package com.yhd.schedule.sharding;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.SpringBeanUtil;
import com.yhd.schedule.sharding.core.ShardingDataProcessorForCrash;
import com.yihaodian.architecture.hedwig.common.exception.HedwigException;
import com.yihaodian.architecture.hedwig.common.util.ZkUtil;
import com.yihaodian.architecture.zkclient.IZkChildListener;
import com.yihaodian.architecture.zkclient.ZkClient;

/**
 * 切片容错处理类
 * 
 * @Author liuxiangrong
 * @CreateTime 2016-1-30 上午12:16:43
 */
public class ShardingFaultHandler implements InitializingBean {
    private static final Log logger = LogFactory.getLog(ShardingFaultHandler.class);

    private static ShardingDataProcessorForCrash jdBookSyncShardingDataProcessorForCrash = (ShardingDataProcessorForCrash) SpringBeanUtil
            .getBean("jdBookSyncShardingDataProcessorForCrash");

    /**
     * 当前IP地址
     */
    protected String localIp = PoolUtils.computePoolIp();

    /**
     * ZK客户端
     */
    protected static ZkClient zkClient = null;

    // 切片容错处理的ZK基础路径
    protected String shardingActiveIpListZKPath;

    public void setShardingActiveIpListZKPath(String shardingActiveIpListZKPath) {
        this.shardingActiveIpListZKPath = shardingActiveIpListZKPath;
    }

    private static void initZkClient(String path) throws HedwigException {
        try {
            zkClient = getZkClientInstance(path);
        } catch (HedwigException e) {
            throw e;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     * 容错初始化ZK节点，配置在SPRING，启动执行，只一次
     */
    public void init() {
        if(StringUtils.isEmpty(shardingActiveIpListZKPath)) {
            throw new IllegalArgumentException("shardingActiveIpListZKPath is empty.");
        }
        logger.info("切片容错服务初始化开始 ");
        try {
            if(zkClient == null) {
                // 初始化ZK节点，建立监听
                initZkNode(localIp);

                // 注册监听器
                registerListener();
            }
        } catch (Exception e) {
            logger.error("切片容错服务初始化异常 :" + e.getMessage(), e);
        }
        logger.info("切片容错服务初始化结束 ");
    }

    /**
     * 初始化ZK节点
     * 
     * @param localIp
     * @throws HedwigException
     */
    protected void initZkNode(String localIp) throws HedwigException {
        initZkClient(shardingActiveIpListZKPath);

        // 初始化数据切片的ZK基础路径
        if(!zkClient.exists(shardingActiveIpListZKPath)) {
            zkClient.createPersistent(shardingActiveIpListZKPath);
        }

        // 在处理节点的IP列表路径下面创建临时节点，节点名为本地IP
        zkClient.createEphemeral(shardingActiveIpListZKPath + "/" + localIp);
    }

    /**
     * 获取zkclient
     * @param basePath
     * @return
     * @throws HedwigException
     */
    private static ZkClient getZkClientInstance(String basePath) throws HedwigException {
        ZkClient _zkClient = ZkUtil.getZkClientInstance();
        if(_zkClient != null && StringUtils.isNotBlank(basePath)) {
            _zkClient.createPersistent(basePath, true);
        }
        return _zkClient;
    }

    /**
     * 注册监听器 建立对处理IP节点和同步对比节点的监听
     */
    protected void registerListener() {
        // 注册对处理节点的IP路径的监听
        zkClient.subscribeChildChanges(shardingActiveIpListZKPath, new IZkChildListener() {
            @Override
            public void handleChildChange(String basePath, List<String> childList) throws Exception {
                if(basePath != null && CollectionUtils.isNotEmpty(childList)) {
                    logger.info("切片容错处理的ZK基础路径 : " + basePath + ", 最新的IP活动列表：" + childList);

                    // 节点发生变化之后，利用活动IP列表更新切片状态
                    Boolean returnBackSuccess = ShardingIndexManager.returnBack(childList);
                    if(returnBackSuccess) {
                        // 此处用于判断京东图书同步是否发生机器宕机,是,则京东图书同步Job重新触发
                        int CrashNum = ShardingIndexManager
                                .queryCrashNumByTableName(ScheduleConstants.JD_BOOK_SYNC_BUSSINESSTYPE);
                        if(CrashNum > 0) {
                            // 所有机器继续抢占宕机机器释放的切片并处理
                            jdBookSyncShardingDataProcessorForCrash.process();
                        }
                    }
                }
            }
        });
    }

}
