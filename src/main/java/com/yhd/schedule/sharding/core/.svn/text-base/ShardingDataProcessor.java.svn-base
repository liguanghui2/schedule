package com.yhd.schedule.sharding.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.schedule.common.ExecutorManager;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.ShardingIndexVo;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.ShardingIndexManager;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;
import com.yihaodian.configcentre.client.utils.YccGlobalPropertyConfigurer;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-7 下午04:54:34
 */
public class ShardingDataProcessor {

    private static final Log logger = LogFactory.getLog(ShardingDataProcessor.class);

    private ShardingDataExecCommandCreator commandCreator;

    /**
     * 每台机器最多可以获取的默认的可用切片数量
     */
    private static final int GRAB_SHARDING_MAX_COUNT_DEFAULT = 4;

    /**
     * 抢夺完一个切片之后的等待时间（单位是毫秒）
     */
    private static final int GRAB_SHARDING_SLEEP_TIME_MILLISECONDS = 50;

    /**
     * 处理完所有切片的最大时间，避免异常导致线程一直不释放锁（单位分钟）
     */
    private static final int SHARDING_PROCESS_MAX_TIME_MINUTES = 5;

    /**
     * 能够获取的最大的切片数量，避免资源分配不均; 初始设置为每台机器最多获取4个空闲切片
     */
    protected int canGrabMaxShrdingCount = GRAB_SHARDING_MAX_COUNT_DEFAULT;

    /**
     * 抢夺完一个切片之后的等待时间（单位是毫秒）
     */
    protected int grapShardingSleepTime = GRAB_SHARDING_SLEEP_TIME_MILLISECONDS;

    /**
     * 处理完所有切片的最大时间，避免异常导致线程一直不释放锁（单位分钟）
     */
    protected int processShardingMaxTime = SHARDING_PROCESS_MAX_TIME_MINUTES;

    public void setGrapShardingSleepTime(int grapShardingSleepTime) {
        this.grapShardingSleepTime = grapShardingSleepTime;
    }

    public void setProcessShardingMaxTime(int processShardingMaxTime) {
        this.processShardingMaxTime = processShardingMaxTime;
    }

    public void setCanGrabMaxShrdingCount(int canGrabMaxShrdingCount) {
        this.canGrabMaxShrdingCount = canGrabMaxShrdingCount;
    }

    public void setCommandCreator(ShardingDataExecCommandCreator commandCreator) {
        this.commandCreator = commandCreator;
    }

    /**
     * <pre>
     *  1. 查询所有的历史价切片
     *  2. 循环抢占切片
     *  3. 根据切片处理数据
     *  4. 将切片返还
     * </pre>
     */
    public void process() {
        String bussinessType = getBussinessType();
        // 1. 查询所有切片
        List<ShardingIndexVo> shardingIndexList = ShardingIndexManager.queryAllSharding(bussinessType);
        logger.info("bussinessType : " + bussinessType + ", all sharding count is <" + shardingIndexList.size()
                    + ">, can get <" + canGrabMaxShrdingCount + "> shardings");
        String env = YccGlobalPropertyConfigurer.getEnv();
        if(env != null && !ScheduleConstants.IS_PRODUCTION.equals(env)) {
            shardingIndexList = getSpecifiedShardingIndexForTest(env, shardingIndexList);
        }
        if(CollectionUtils.isEmpty(shardingIndexList)) {
            return;
        }

        logger.info("bussinessType : " + bussinessType + ", start process sharding data.");

        // 2. 循环抢占切片
        List<ShardingIndexVo> grabSuccessedShardingIndexs = new ArrayList<ShardingIndexVo>();
        for(ShardingIndexVo shardingIndex : shardingIndexList) {
            if(!ShardingIndexManager.isUsable(shardingIndex)) {
                continue;
            }
            boolean grabSuccessed = ShardingIndexManager.tryGrab(shardingIndex);
            if(grabSuccessed) {
                grabSuccessedShardingIndexs.add(shardingIndex);
            }
            // 避免单台机器抢太多，浪费其他空闲机器
            if(grabSuccessedShardingIndexs.size() >= canGrabMaxShrdingCount) {
                break;
            }
            // 等待一段时间再去抢下一个切片
            try {
                Thread.sleep(new Random().nextInt(grapShardingSleepTime));
            } catch (InterruptedException e) {
                logger.error("执行线程睡眠发生异常, error : " + e.getMessage(), e);
            }
        }

        if(CollectionUtils.isEmpty(grabSuccessedShardingIndexs)) {
            return;
        }

        CountDownLatch finishSignal = new CountDownLatch(grabSuccessedShardingIndexs.size());
        for(ShardingIndexVo currentShardingIndex : grabSuccessedShardingIndexs) {
            // 3. 根据切片处理数据
            try {
                processCore(currentShardingIndex.getShardingIndex(), finishSignal);
            } catch (Exception e) {
                logger.error("切片处理数据发生异常, shardingIndex : + " + currentShardingIndex + ",error : " + e.getMessage(), e);
            }
        }
        try {
            // 等待切片处理完毕，如果超时则强制中断
            finishSignal.await(processShardingMaxTime, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.error("线程被中断, error : " + e.getMessage(), e);
        } finally {
            for(ShardingIndexVo currentShardingIndex : grabSuccessedShardingIndexs) {
                // 4. 将切片返还
                ShardingIndexManager.returnBack(currentShardingIndex);
            }
        }
        logger.info("bussinessType : " + bussinessType + ", end process sharding data.");
    }

    protected String getBussinessType() {
        return commandCreator.getBussinessType();
    }

    /**
     * 处理数据的核心方法
     * @param currentShardingIndex
     * @param finishSignal
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    protected void processCore(final int shardingIndex, CountDownLatch finishSignal) throws InterruptedException, ExecutionException {
        ShardingDataExecCommand execCommand = commandCreator.create(shardingIndex, finishSignal);
        ExecutorManager.executeOnly(execCommand);
    }

    private List<ShardingIndexVo> getSpecifiedShardingIndexForTest(String env, List<ShardingIndexVo> shardingIndexList) {
        String specifiedShardingIndex = MiscConfigUtils.getItemValue(ScheduleConstants.SPECIFIED_SHARDING_INDEX);
        if(StringUtils.isNotBlank(specifiedShardingIndex)) {
            List<ShardingIndexVo> shardings = new ArrayList<ShardingIndexVo>();
            if(CollectionUtils.isNotEmpty(shardingIndexList)) {
                for(ShardingIndexVo shardingIndexVo : shardingIndexList) {
                    if(shardingIndexVo.getShardingIndex() == Integer.parseInt(specifiedShardingIndex)) {
                        shardings.add(shardingIndexVo);
                    }
                }

                if(CollectionUtils.isNotEmpty(shardings)) {
                    shardingIndexList = shardings;
                    logger.info("env : " + env + ", specified sharding is <" + specifiedShardingIndex + ">");
                }
            }
        }
        return shardingIndexList;
    }
}
