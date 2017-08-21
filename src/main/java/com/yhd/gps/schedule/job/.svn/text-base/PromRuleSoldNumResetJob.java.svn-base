package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * 
 * 重置product_prom_rule表的soldNum定时任务
 * @Author lipengcheng
 * @CreateTime 2016-7-10 下午10:45:09
 */
public class PromRuleSoldNumResetJob extends GpsBaseJob {
    private static final Log LOG = LogFactory.getLog(PromRuleSoldNumResetJob.class);

    private ShardingDataProcessor resetPromRuleSoldNumShardingDataProcessor;

    public void setResetPromRuleSoldNumShardingDataProcessor(ShardingDataProcessor resetPromRuleSoldNumShardingDataProcessor) {
        this.resetPromRuleSoldNumShardingDataProcessor = resetPromRuleSoldNumShardingDataProcessor;
    }

    @Override
    public void init() {
        jobName = "GPS价格促销已售数量重置";
        jobDesc = "重置product_prom_rule表的soldNum";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        resetPromRuleSoldNumShardingDataProcessor.process();
        LOG.debug("---###---[GPS]重置product_prom_rule表的soldNum定时任务执行结束:---总共耗费时间:"
                  + (System.currentTimeMillis() - start) / 1000 + " 秒");

    }

}