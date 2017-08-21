package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * 发送价格变化消息和胖消息定时任务
 * @Author chenmao
 * @CreateTime 2015-12-8 下午03:32:32
 */
public class GpsPriceChangeMsgSendJob extends GpsBaseJob {

    private static final Log logger = LogFactory.getLog(GpsPriceChangeMsgSendJob.class);

    private ShardingDataProcessor gpsPriceChangeMsgSendProcessor;

    public void setGpsPriceChangeMsgSendProcessor(ShardingDataProcessor gpsPriceChangeMsgSendProcessor) {
        this.gpsPriceChangeMsgSendProcessor = gpsPriceChangeMsgSendProcessor;
    }

    @Override
    public void init() {
        jobName = "GPS价格变化消息发送定时器";
        jobDesc = "发送价格变化消息和胖消息";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        try {
            // 发送GPS价格变动消息
            gpsPriceChangeMsgSendProcessor.process();
            logger.debug("---基准价、促销价导致的价格变化消息发送任务结束执行 --总共耗费时间:" + (System.currentTimeMillis() - start) / 1000
                         + " 秒---");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}