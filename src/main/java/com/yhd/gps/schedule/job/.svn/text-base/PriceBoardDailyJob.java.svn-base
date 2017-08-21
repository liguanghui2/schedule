package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * @author sunfei
 * @CreateTime 2017-6-28 下午04:57:23
 */
public class PriceBoardDailyJob extends GpsBaseJob {
    private static final Logger logger = Logger.getLogger(PriceBoardDailyJob.class);
    private ShardingDataProcessor dailyPriceBoardDataProcessor;

    public void setDailyPriceBoardDataProcessor(ShardingDataProcessor dailyPriceBoardDataProcessor) {
        this.dailyPriceBoardDataProcessor = dailyPriceBoardDataProcessor;
    }

    @Override
    public void init() {
        jobName = "daily--售价看板日处理历史数据定时器";
        jobDesc = "每天计算前一天所有城市精选商品的售价看板数据并落地 history_price_daily表";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        dailyPriceBoardDataProcessor.process();
        logger.debug("---####处理日售价看板数据的定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

}
