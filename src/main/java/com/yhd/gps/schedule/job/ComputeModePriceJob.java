package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * @author:liguanghui1
 * @date ：2017-4-13 下午05:33:32
 */
public class ComputeModePriceJob extends GpsBaseJob {
    private static final Logger LOG = Logger.getLogger(ComputeModePriceJob.class);
    private ShardingDataProcessor computeModePriceProcessor;

    public void setComputeModePriceProcessor(ShardingDataProcessor computeModePriceProcessor) {
        this.computeModePriceProcessor = computeModePriceProcessor;
    }

    @Override
    public void init() {
        jobName = "根据切片处理众数价定时任务";
        jobDesc = "城市精选根据切片处理众数价加权移动平均价计算任务";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        computeModePriceProcessor.process();
        LOG.debug("---####处理众数价定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");

    }

}
