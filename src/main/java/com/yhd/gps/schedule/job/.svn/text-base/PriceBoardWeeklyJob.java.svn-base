package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/** 
 * @author  sunfei
 * @CreateTime 2017-6-30 下午04:31:20 
 */
public class PriceBoardWeeklyJob extends GpsBaseJob {
    private static final Logger logger = Logger.getLogger(PriceBoardWeeklyJob.class);
    private ShardingDataProcessor weeklyPriceBoardDataProcessor;
    
    public void setWeeklyPriceBoardDataProcessor(ShardingDataProcessor weeklyPriceBoardDataProcessor) {
        this.weeklyPriceBoardDataProcessor = weeklyPriceBoardDataProcessor;
    }

    @Override
    public void init() {
        jobName = "weekly--售价看板每周处理历史数据定时器";
        jobDesc = "每周一计算上一周所有城市精选商品的售价看板数据并落地 history_price_weekly表";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        weeklyPriceBoardDataProcessor.process();
        logger.debug("---####处理每周售价看板数据的定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

}
