package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * 
 * 京东日百价格同步job
 * @Author lipengcheng
 * @CreateTime 2017-2-27 下午02:54:44
 */
public class JDDailyGoodsSyncJob extends GpsBaseJob {
    private static final Log LOG = LogFactory.getLog(JDDailyGoodsSyncJob.class);

    private ShardingDataProcessor jdDailyGoodsSyncShardingDataProcessor;

    public void setJdDailyGoodsSyncShardingDataProcessor(ShardingDataProcessor jdDailyGoodsSyncShardingDataProcessor) {
        this.jdDailyGoodsSyncShardingDataProcessor = jdDailyGoodsSyncShardingDataProcessor;
    }

    @Override
    public void init() {
        jobName = "京东日百价格同步";
        jobDesc = "同步京东日百价格到yhd";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        jdDailyGoodsSyncShardingDataProcessor.process();
        LOG.debug("---###---[GPS]京东日百价格同步定时任务执行结束:---总共耗费时间:" + (System.currentTimeMillis() - start) / 1000 + " 秒");
    }

}