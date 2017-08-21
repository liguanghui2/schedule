package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * @author:shengxu1
 * @date ：2017-4-28 下午03:33:32
 */
public class HandleAdvicedPriceJob extends GpsBaseJob {
    private static final Logger LOG = Logger.getLogger(HandleAdvicedPriceJob.class);
    private ShardingDataProcessor handleAdvicedPriceProcessor;

    public void setHandleAdvicedPriceProcessor(ShardingDataProcessor handleAdvicedPriceProcessor) {
        this.handleAdvicedPriceProcessor = handleAdvicedPriceProcessor;
    }

    @Override
    public void init() {
        jobName = "城市精选处理建议价定时任务";
        jobDesc = "城市精选处理建议价定时任务";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        handleAdvicedPriceProcessor.process();
        LOG.debug("---####处理建议价定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");

    }

}
