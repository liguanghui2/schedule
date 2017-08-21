package com.yhd.schedule.sharding;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.dao.ShardingIndexDao;
import com.yhd.gps.schedule.vo.ShardingIndexVo;

/**
 * 切片管理类
 * @Author xuyong
 * @CreateTime 2016-3-9 下午04:02:56
 */
public class ShardingIndexManager {

    private static final Log logger = LogFactory.getLog(ShardingIndexManager.class);

    private static final String localIp = PoolUtils.computePoolIp();

    /**
     * 切片DAO
     */
    protected static ShardingIndexDao shardingIndexDao;

    public void setShardingIndexDao(ShardingIndexDao dao) {
        setShardingIndexDaoStatic(dao);
    }
    
    public static void setShardingIndexDaoStatic(ShardingIndexDao dao) {
        shardingIndexDao = dao;
    }

    public static List<ShardingIndexVo> queryAllSharding(String tableName) {
        List<ShardingIndexVo> allShardingIndexVos = shardingIndexDao.queryAllShardingByTableName(tableName);
        // 打乱集合的元素顺序
        Collections.shuffle(allShardingIndexVos);
        return allShardingIndexVos;
    }

    /**
     * 争抢切片
     * @param shardingIndex
     * @return
     */
    public static boolean tryGrab(ShardingIndexVo shardingIndex) {
        // 如果被本机占用
        if(isLocalUsed(shardingIndex)) {
            return true;
        }

        // 使用数据库更新方式来抢夺
        shardingIndex.setIp(localIp);
        int rows = shardingIndexDao.updateShardingUnValid(shardingIndex);
        if(rows > 0) {
            logger.info("sharding<table : " + shardingIndex.getTableName() + ", shardingIndex : "
                        + shardingIndex.getShardingIndex() + "> grab successed by<" + localIp + ">");
            return true;
        } else {
            logger.info("sharding<table : " + shardingIndex.getTableName() + ", index : "
                        + shardingIndex.getShardingIndex() + "> grab failed by<" + localIp + ">");
            return false;
        }
    }
    
    /**
     * 争抢宕机释放的切片
     * @param shardingIndex
     * @return
     */
    public static boolean tryCrashGrab(ShardingIndexVo shardingIndex){
        // 如果被本机占用
        if(isLocalUsed(shardingIndex)) {
            return true;
        }

        // 使用数据库更新方式来抢夺
        shardingIndex.setIp(localIp);
        int rows = shardingIndexDao.updateCrashShardingUnValid(shardingIndex);
        if(rows > 0) {
            logger.info("sharding<table : " + shardingIndex.getTableName() + ", shardingIndex : "
                        + shardingIndex.getShardingIndex() + "> grab successed by<" + localIp + ">");
            return true;
        } else {
            logger.info("sharding<table : " + shardingIndex.getTableName() + ", index : "
                        + shardingIndex.getShardingIndex() + "> grab failed by<" + localIp + ">");
            return false;
        }
    }

    /**
     * 检测该切片是否可用
     * @param shardingIndex
     * @return
     */
    public static boolean isUsable(ShardingIndexVo shardingIndex) {
        // 如果被本机占用
        if(isLocalUsed(shardingIndex)) {
            return true;
        }
        // 如果状态被占用并且IP地址非本机IP,则不可使用，此时如果有异常会有ZK提供的可用IP列表来更新
        if(shardingIndex.getIsValid() == ScheduleConstants.SHARDING_INDEX_UNVALID
           && !shardingIndex.getIp().equals(localIp)) {
            return false;
        }

        return shardingIndex.getIsValid() == ScheduleConstants.SHARDING_INDEX_VALID;
    }

    /**
     * 如果状态被占用，并且IP地址是本机，则有可能是本机故障然后重启的情况，该切片可以再次使用
     * @param shardingIndex
     * @return
     */
    private static boolean isLocalUsed(ShardingIndexVo shardingIndex) {
        if(shardingIndex.getIsValid() == ScheduleConstants.SHARDING_INDEX_UNVALID
           && shardingIndex.getIp().equals(localIp)) {
            return true;
        }
        return false;
    }

    /**
     * 退还切片
     * @param shardingIndex
     * @return
     */
    public static boolean returnBack(ShardingIndexVo shardingIndex) {
        int rows = 0;
        try {
            rows = shardingIndexDao.updateShardingValid(shardingIndex);
        } catch (Exception e) {
            logger.error(
                    "sharding<table : " + shardingIndex.getTableName() + ", shardingIndex : "
                            + shardingIndex.getShardingIndex() + "> give back, error : " + e.getMessage(), e);
        }
        if(rows > 0) {
            logger.info("sharding<table : " + shardingIndex.getTableName() + ", shardingIndex : "
                        + shardingIndex.getShardingIndex() + "> give back successed by<" + localIp + ">");
            return true;
        } else {
            logger.info("sharding<table : " + shardingIndex.getTableName() + ", shardingIndex : "
                        + shardingIndex.getShardingIndex() + "> give back failed by<" + localIp + ">");
            return false;
        }
    }

    /**
     * 节点发生变化之后，利用活动IP列表更新切片状态
     * @param activeIps
     * @return
     */
    public static boolean returnBack(List<String> activeIps) {
        return shardingIndexDao.updateShardingValidByActiveIps(activeIps) > 0;
    }
    
    /**
     * 用于查询京东图书机器宕机的机器数量
     */
    public static Integer queryCrashNumByTableName(String tableName){
        return shardingIndexDao.queryCrashNumByTableName(tableName);
    }
    
    
    public static List<ShardingIndexVo> queryAllCrashSharding(String tableName) {
        return shardingIndexDao.queryAllCrashShardingByTableName(tableName);
    }
}
