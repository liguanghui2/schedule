package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.schedule.sharding.processor.GpsProductSyncShardingDataProcessor;

/**
 * ---- 请加注释 ------
 * 
 * @Author chenmao
 * @CreateTime 2015-11-11 下午03:11:26
 */
public class GpsProductSyncJob extends GpsBaseJob {
    private static final Log LOG = LogFactory.getLog(GpsProductSyncJob.class);

    private GpsProductSyncShardingDataProcessor gpsProductSyncShardingDataProcessor;

    public void setGpsProductSyncShardingDataProcessor(GpsProductSyncShardingDataProcessor gpsProductSyncShardingDataProcessor) {
        this.gpsProductSyncShardingDataProcessor = gpsProductSyncShardingDataProcessor;
    }

    @Override
    public void init() {
        jobName = "GPS_PRODUCT同步";
        jobDesc = "GPS_PRODUCT同步补偿程序";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        gpsProductSyncShardingDataProcessor.process();
        LOG.debug("---###---[GPS]GPS_PRODUCT同步补偿程序定时任务执行结束:---总共耗费时间:" + (System.currentTimeMillis() - start) / 1000
                  + " 秒");
    }

}