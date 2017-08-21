package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * 发送金币促销变价消息
 * 
 * @Author Kevin Lee
 * @CreateTime 2017-06-14 下午17:58:32
 */

public class GoldCoinPriceChangeMsgSendJob extends GpsBaseJob {

    private static final Log logger = LogFactory.getLog(GpsPriceChangeMsgSendJob.class);

    private ShardingDataProcessor goldCoinPriceChangeMsgSendProcessor;

    public void setGoldCoinPriceChangeMsgSendProcessor(ShardingDataProcessor goldCoinPriceChangeMsgSendProcessor) {
        this.goldCoinPriceChangeMsgSendProcessor = goldCoinPriceChangeMsgSendProcessor;
    }

    @Override
    public void init() {
        jobName = "处理金币促销变化定时器";
        jobDesc = "发送金币促销变化消息和胖消息";

    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        try {
            goldCoinPriceChangeMsgSendProcessor.process();
            logger.debug("---处理金币促销变化消息发送任务结束执行 --总共耗费时间:" + (System.currentTimeMillis() - start) / 1000 + " 秒---");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
