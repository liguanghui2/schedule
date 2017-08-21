package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * 发送Promotion价格变化消息和胖消息定时任务
 * @Author xuyong
 * @CreateTime 2016-6-17 下午05:07:46
 */
public class PromotionPriceChangeMsgSendJob extends GpsBaseJob {

    private static final Log logger = LogFactory.getLog(PromotionPriceChangeMsgSendJob.class);

    private ShardingDataProcessor promotionPriceChangeMsgSendProcessor;

    public void setPromotionPriceChangeMsgSendProcessor(ShardingDataProcessor promotionPriceChangeMsgSendProcessor) {
        this.promotionPriceChangeMsgSendProcessor = promotionPriceChangeMsgSendProcessor;
    }

    @Override
    public void init() {
        jobName = "Promotion价格变化消息发送定时器";
        jobDesc = "发送Promotion价格变化消息和胖消息";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        try {
            // 发送LP价格变动消息
            promotionPriceChangeMsgSendProcessor.process();
            logger.debug("---LP导致的价格变化消息发送任务结束执行 --总共耗费时间:" + (System.currentTimeMillis() - start) / 1000 + " 秒---");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}