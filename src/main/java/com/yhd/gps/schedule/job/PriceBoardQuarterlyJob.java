package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/** 
 * @author  sunfei
 * @CreateTime 2017-7-2 下午03:52:13 
 */
public class PriceBoardQuarterlyJob extends GpsBaseJob {
    private static final Logger logger = Logger.getLogger(PriceBoardQuarterlyJob.class);
    private ShardingDataProcessor quarterlyPriceBoardDataProcessor;
    
    public void setQuarterlyPriceBoardDataProcessor(ShardingDataProcessor quarterlyPriceBoardDataProcessor) {
        this.quarterlyPriceBoardDataProcessor = quarterlyPriceBoardDataProcessor;
    }

    @Override
    public void init() {
        jobName = "quarterly--售价看板每月处理上三个月历史数据定时器";
        jobDesc = "每月一号计算上三个月所有城市精选商品的售价看板数据并落地 history_price_monthly表";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        quarterlyPriceBoardDataProcessor.process();
        logger.debug("---####处理三个月售价看板数据的定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

}
