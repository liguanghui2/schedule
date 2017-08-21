package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * @author sunfei
 * @CreateTime 2016-11-28 下午05:23:21
 */
public class JDBookSyncJob extends GpsBaseJob {
    private static final Logger logger = Logger.getLogger(JDBookSyncJob.class);
    private ShardingDataProcessor jdBookSyncShardingDataProcessor;

    public void setJdBookSyncShardingDataProcessor(ShardingDataProcessor jdBookSyncShardingDataProcessor) {
        this.jdBookSyncShardingDataProcessor = jdBookSyncShardingDataProcessor;
    }

    @Override
    public void init() {
        jobName = "京东图书入驻，同步京东商品定时器";
        jobDesc = "拉取pss库京东图书pmId";
    }

    @Override
    public void doJobTask() {
        try {
            jdBookSyncShardingDataProcessor.process();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
