package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.schedule.sharding.processor.CompareLPPromotionShardingDataProcessor;

/**
 * 
 * 比较促销landingPage和gps landingPage的定时任务
 * @Author lipengcheng
 * @CreateTime 2016-7-4 上午11:39:49
 */
public class PromotionCompareJob extends GpsBaseJob {
    private static final Log LOG = LogFactory.getLog(PromotionCompareJob.class);

    private CompareLPPromotionShardingDataProcessor compareLPPromotionShardingDataProcessor;

    public void setCompareLPPromotionShardingDataProcessor(CompareLPPromotionShardingDataProcessor compareLPPromotionShardingDataProcessor) {
        this.compareLPPromotionShardingDataProcessor = compareLPPromotionShardingDataProcessor;
    }

    @Override
    public void init() {
        jobName = "landingPage促销比对";
        jobDesc = "比较促销landingPage和gps landingPage数据差异";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        compareLPPromotionShardingDataProcessor.process();
        LOG.debug("---###---[GPS]landingPage促销比对定时任务执行结束:---总共耗费时间:" + (System.currentTimeMillis() - start) / 1000
                  + " 秒");
    }
}