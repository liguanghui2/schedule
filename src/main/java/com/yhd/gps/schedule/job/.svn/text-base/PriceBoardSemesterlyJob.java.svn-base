package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/** 
 * @author  sunfei
 * @CreateTime 2017-7-2 下午04:03:39 
 */
public class PriceBoardSemesterlyJob extends GpsBaseJob {
    private static final Logger logger = Logger.getLogger(PriceBoardSemesterlyJob.class);
    private ShardingDataProcessor semesterlyPriceBoardDataProcessor;
    
    public void setSemesterlyPriceBoardDataProcessor(ShardingDataProcessor semesterlyPriceBoardDataProcessor) {
        this.semesterlyPriceBoardDataProcessor = semesterlyPriceBoardDataProcessor;
    }

    @Override
    public void init() {
        jobName = "semesterly--售价看板每月处理上六个月历史数据定时器";
        jobDesc = "每月一号计算上六个月所有城市精选商品的售价看板数据并落地 history_price_monthly表";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        semesterlyPriceBoardDataProcessor.process();
        logger.debug("---####处理六个月售价看板数据的定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

}
