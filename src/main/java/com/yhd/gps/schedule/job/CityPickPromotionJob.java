package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;
import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * Created by zhangshuqiang on 2017/5/2.
 */
public class CityPickPromotionJob extends GpsBaseJob {
    private static final Logger LOG = Logger.getLogger(CityPickPromotionJob.class);
    private ShardingDataProcessor cityPickPromotionProcessor;

    public ShardingDataProcessor getCityPickPromotionProcessor() {
        return cityPickPromotionProcessor;
    }

    public void setCityPickPromotionProcessor(ShardingDataProcessor cityPickPromotionProcessor) {
        this.cityPickPromotionProcessor = cityPickPromotionProcessor;
    }

    @Override
    public void init() {
        jobName = "根据切片处理创建普通促销定时任务";
        jobDesc = "城市精选根据切片处理处理创建普通促销任务";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        cityPickPromotionProcessor.process();
        LOG.debug("---####创建普通促销定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");

    }

}

