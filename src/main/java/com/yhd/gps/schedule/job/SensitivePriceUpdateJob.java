package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * Created by zhangshuqiang on 2017/5/2.
 *
 */
public class SensitivePriceUpdateJob extends GpsBaseJob {
    private static final Logger LOG = Logger.getLogger(SensitivePriceUpdateJob.class);
    private ShardingDataProcessor sensitivePriceUpdateProcessor;

    public void setSensitivePriceUpdateProcessor(ShardingDataProcessor sensitivePriceUpdateProcessor) {
        this.sensitivePriceUpdateProcessor = sensitivePriceUpdateProcessor;
    }

    @Override
    public void init() {
        jobName = "城市精选敏感品弹性价格区间更新定时任务";
        jobDesc = "城市精选敏感品弹性价格区间更新定时任务";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        sensitivePriceUpdateProcessor.process();
        LOG.debug("---####处理敏感品弹性价格区间更新定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");

    }

}
