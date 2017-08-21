package com.yhd.schedule.sharding.exec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.schedule.sharding.exception.InvokeInvalidMethodException;

/**
 * 切片数据处理的异步任务基类， 支持分页
 * @Author xuyong
 * @CreateTime 2016-3-9 下午08:04:18
 */
public abstract class ShardingDataPageExecCommand<R, D> extends ShardingDataExecCommand<List<R>, D> {

    private static final Log logger = LogFactory.getLog(ShardingDataPageExecCommand.class);

    private Integer pageSize;

    public ShardingDataPageExecCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,
                                       Integer pageSize) {
        super(shardingIndex, finishSignal, bussinessType);
        if(null == pageSize) {
            throw new IllegalArgumentException("pageSize is null.");
        }
        this.pageSize = pageSize;
    }

    public List<R> action() {
        logger.debug("bussinessType : " + bussinessType + ",shardingIndex = " + shardingIndex + " 切片数据开始处理");
        List<R> result = new ArrayList<R>();
        try {
            int i = 1;

            D bussinessDataes = fetchBussinessDataes(shardingIndex, pageSize, i);
            if(isDataesEmpty(bussinessDataes)) {
                // 避免该分片对应的没有数据导致处理过快后续其他机器有重复强制该分片
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Collections.emptyList();
            }

            while(true) {
                logger.debug("bussinessType : " + bussinessType + ",shardingIndex = " + shardingIndex + " 切片数据抓取第" + i
                             + "页");
                ExecResult<List<R>> execResult = doWork(bussinessDataes);

                if(null != execResult && CollectionUtils.isNotEmpty(execResult.getDataIds())) {
                    List<Long> dataIds = new ArrayList<Long>(execResult.getDataIds());
                    Collections.sort(dataIds);

                    int rows = updateData2Processed(dataIds);
                    logger.debug("bussinessType : " + bussinessType + ",shardingIndex = " + shardingIndex + " 切片数据处理完成"
                                 + rows + "条");
                }

                if(null != execResult && CollectionUtils.isNotEmpty(execResult.getResult())) {
                    result.addAll(execResult.getResult());
                }

                i++;
                bussinessDataes = fetchBussinessDataes(shardingIndex, pageSize, i);

                if(isDataesEmpty(bussinessDataes) || isInterruptTask()) {
                    break;
                }
            }
        } finally {
            finishSignal.countDown();
        }
        logger.debug("bussinessType : " + bussinessType + ",shardingIndex = " + shardingIndex + " 切片数据处理完成");
        return result;
    }

    /**
     * while循环执行任务中是否可以中断任务,默认是false
     * @return
     */
    public boolean isInterruptTask() {
        return false;
    }

    /**
     * 抓取业务数据,子类实现改方法
     * @return
     */
    public D fetchBussinessDataes(final int shardingIndex) {
        throw new InvokeInvalidMethodException("分页抓取切片数据时，该方法不能调用!");
    }

    public abstract D fetchBussinessDataes(final int shardingIndex, final Integer pageSize,final Integer currentPage);

}