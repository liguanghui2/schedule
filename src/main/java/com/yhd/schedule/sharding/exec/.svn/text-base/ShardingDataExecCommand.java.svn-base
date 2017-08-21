package com.yhd.schedule.sharding.exec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 切片数据处理的异步任务基类
 * @Author xuyong
 * @CreateTime 2016-3-9 下午08:04:18
 */
public abstract class ShardingDataExecCommand<R, D> {

    private static final Log logger = LogFactory.getLog(ShardingDataExecCommand.class);

    // 数据切片号
    protected int shardingIndex;
    // 任务处理完成信号量
    protected CountDownLatch finishSignal;

    protected String bussinessType;

    public ShardingDataExecCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType) {
        this.shardingIndex = shardingIndex;
        this.finishSignal = finishSignal;
        this.bussinessType = bussinessType;
    }

    public R action() {
        logger.debug("bussinessType : " + bussinessType + ",shardingIndex = " + shardingIndex + " 切片数据开始处理");
        R result = null;
        try {
            D bussinessDataes = fetchBussinessDataes(shardingIndex);
            if(isDataesEmpty(bussinessDataes)) {
                // 避免该分片对应的没有数据导致处理过快后续其他机器有重复强制该分片
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return result;
            }

            ExecResult<R> execResult = doWork(bussinessDataes);

            if(null != execResult && CollectionUtils.isNotEmpty(execResult.getDataIds())) {
                List<Long> dataIds = new ArrayList<Long>(execResult.getDataIds());
                Collections.sort(dataIds);

                int rows = updateData2Processed(dataIds);
                logger.debug("bussinessType : " + bussinessType + ",shardingIndex = " + shardingIndex + " 切片数据处理完成"
                             + rows + "条");
            }
            if(null != execResult) {
                result = execResult.getResult();
            }
        } finally {
            finishSignal.countDown();
        }

        logger.debug("bussinessType : " + bussinessType + ",shardingIndex = " + shardingIndex + " 切片数据处理完成");
        return result;
    }

    /**
     * 抓取业务数据,子类实现改方法
     * @return
     */
    public abstract D fetchBussinessDataes(final int shardingIndex);

    /**
     * 处理业务数据,子类实现改方法
     * @return
     */
    public abstract ExecResult<R> doWork(final D bussinessDataes);

    /**
     * 更新业务数据状态为已处理,子类实现改方法
     * @param dataIds
     * @return
     */
    public abstract int updateData2Processed(final List<Long> dataIds);

    @SuppressWarnings("rawtypes")
    protected boolean isDataesEmpty(D bussinessDataes) {
        if(null == bussinessDataes) {
            return true;
        }

        if(bussinessDataes instanceof Collection) {
            return CollectionUtils.isEmpty((Collection) bussinessDataes);
        }

        if(bussinessDataes instanceof Map) {
            return MapUtils.isEmpty((Map) bussinessDataes);
        }
        return false;
    }

}