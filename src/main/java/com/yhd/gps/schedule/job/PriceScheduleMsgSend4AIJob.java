package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * @author:liguanghui1
 * @date ：2017-4-25 下午01:29:10
 */
public class PriceScheduleMsgSend4AIJob extends GpsBaseJob {

    private static final Logger LOG = Logger.getLogger(PriceScheduleMsgSend4AIJob.class);
    private ShardingDataProcessor priceScheduleMsgSend4AIProcessor;

    public void setPriceScheduleMsgSend4AIProcessor(ShardingDataProcessor priceScheduleMsgSend4AIProcessor) {
        this.priceScheduleMsgSend4AIProcessor = priceScheduleMsgSend4AIProcessor;
    }

    @Override
    public void init() {
        jobName = "定时器2拉取商品到商品定价表";
        jobDesc = "根据商家ID拉取在售商品清单，保存到商品定价表";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        priceScheduleMsgSend4AIProcessor.process();
        LOG.debug("---####定时器2拉取商品到商品定价表定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

}
