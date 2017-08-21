package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.schedule.sharding.processor.PromotionDataesCleanShardingDataProcessor;

/**
 * 清除product_prom_rule、gps_promotion表的过期数据的定时任务
 * @Author xuyong
 * @CreateTime 2015-11-10 下午03:53:14
 */
public class GPSPromotionDataesCleanJob extends GpsBaseJob {
    private static final Log LOG = LogFactory.getLog(GPSPromotionDataesCleanJob.class);

    private PromotionDataesCleanShardingDataProcessor promotionDataesCleanShardingDataProcessor;

    public void setPromotionDataesCleanShardingDataProcessor(PromotionDataesCleanShardingDataProcessor promotionDataesCleanShardingDataProcessor) {
        this.promotionDataesCleanShardingDataProcessor = promotionDataesCleanShardingDataProcessor;
    }

    @Override
    public void init() {
        jobName = "GPS过期数据清除";
        jobDesc = "清除product_prom_rule、gps_promotion表的过期数据";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        promotionDataesCleanShardingDataProcessor.process();
        LOG.debug("---###---[GPS]清除product_prom_rule、gps_promotion表的过期数据定时任务执行结束:---总共耗费时间:"
                  + (System.currentTimeMillis() - start) / 1000 + " 秒");

    }

}