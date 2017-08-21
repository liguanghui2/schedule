package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/** 
 * @author  sunfei
 * @CreateTime 2017-7-2 下午03:37:16 
 */
public class PriceBoardMonthlyJob extends GpsBaseJob {
    private static final Logger logger = Logger.getLogger(PriceBoardMonthlyJob.class);
    private ShardingDataProcessor monthlyPriceBoardDataProcessor;
    
    public void setMonthlyPriceBoardDataProcessor(ShardingDataProcessor monthlyPriceBoardDataProcessor) {
        this.monthlyPriceBoardDataProcessor = monthlyPriceBoardDataProcessor;
    }

    @Override
    public void init() {
        jobName = "monthly--售价看板每月处理历史数据定时器";
        jobDesc = "每月1号计算上一月所有城市精选商品的售价看板数据并落地 history_price_monthly表";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        monthlyPriceBoardDataProcessor.process();
        logger.debug("---####处理每月售价看板数据的定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
    }
}
