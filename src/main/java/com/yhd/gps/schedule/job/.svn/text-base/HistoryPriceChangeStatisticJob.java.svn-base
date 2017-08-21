package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * 根据历史价格变化消息HISTORY_PRICE_CHANGE_MESSAGE统计计算历史价格
 * 
 * @Author liuxiangrong
 * @CreateTime 2016-1-29 下午02:10:23
 */
public class HistoryPriceChangeStatisticJob extends GpsBaseJob {

    private static final Log logger = LogFactory.getLog(HistoryPriceChangeStatisticJob.class);

    private ShardingDataProcessor shardingDataProcessor;

    public void setShardingDataProcessor(ShardingDataProcessor shardingDataProcessor) {
        this.shardingDataProcessor = shardingDataProcessor;
    }

    /**
     * 初始化JOB
     */
    @Override
    public void init() {
        jobName = "GPS历史价格变化数据处理";
        jobDesc = "处理history_price_change_message表计算历史价格";
    }

    /**
     * 执行JOB
     */
    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        shardingDataProcessor.process();
        logger.debug("---###---[GPS]处理history_price_change_message数据定时任务执行结束:---总共耗费时间:"
                     + (System.currentTimeMillis() - start) / 1000 + " 秒");
    }
}
