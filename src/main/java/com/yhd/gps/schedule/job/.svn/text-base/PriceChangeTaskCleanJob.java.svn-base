package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.schedule.sharding.processor.PriceChangeTaskCleanShardingDataProcessor;

/**
 * 清除价格变化消息表的过期数据定时任务
 * @Author chenmao
 * @CreateTime 2015-12-9 下午04:40:06
 */
public class PriceChangeTaskCleanJob extends GpsBaseJob {

    private static final Log LOG = LogFactory.getLog(PriceChangeTaskCleanJob.class);

    private PriceChangeTaskCleanShardingDataProcessor priceChangeTaskCleanShardingDataProcessor;

    public void setPriceChangeTaskCleanShardingDataProcessor(PriceChangeTaskCleanShardingDataProcessor priceChangeTaskCleanShardingDataProcessor) {
        this.priceChangeTaskCleanShardingDataProcessor = priceChangeTaskCleanShardingDataProcessor;
    }

    @Override
    public void init() {
        jobName = "清除促销变价、LP变价扫描表的过期数据";
        jobDesc = "清除促销变价、LP变价扫描表的过期数据";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();

        priceChangeTaskCleanShardingDataProcessor.process();

        LOG.debug("---###---价格消息清理任务结束执行 --总共耗费时间:" + (System.currentTimeMillis() - start) / 1000 + " 秒---###---");
    }
}