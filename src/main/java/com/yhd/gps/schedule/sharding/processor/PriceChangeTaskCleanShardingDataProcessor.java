package com.yhd.gps.schedule.sharding.processor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.yhd.gps.busyservice.dao.BusyPriceChangeMsgDao;
import com.yhd.gps.busyservice.dao.PromotionPriceChangeMsgDao;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-5-19 下午05:19:18
 */
public class PriceChangeTaskCleanShardingDataProcessor extends ShardingDataProcessor {

    private static final Logger LOG = Logger.getLogger(PriceChangeTaskCleanShardingDataProcessor.class);

    private BusyPriceChangeMsgDao busyPriceChangeMsgDao;
    private PromotionPriceChangeMsgDao promotionPriceChangeMsgDao;
    private String bussinessType;

    public void setBusyPriceChangeMsgDao(BusyPriceChangeMsgDao busyPriceChangeMsgDao) {
        this.busyPriceChangeMsgDao = busyPriceChangeMsgDao;
    }

    public void setPromotionPriceChangeMsgDao(PromotionPriceChangeMsgDao promotionPriceChangeMsgDao) {
        this.promotionPriceChangeMsgDao = promotionPriceChangeMsgDao;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    @Override
    protected void processCore(int shardingIndex, CountDownLatch finishSignal) throws InterruptedException, ExecutionException {
        Integer delayDays = MiscConfigUtils.getPriceChangeMsgCleanDelayDays();
        try {
            // 清理过期的价格变化消息
            busyPriceChangeMsgDao.clearExpiredPriceChangeTaskData(delayDays);
        } catch (Exception e) {
            LOG.error("清理过期的价格变化消息发生异常, error : " + e.getMessage(), e);
        }
        try {
            // 清理过期的promotion价格变化消息
            promotionPriceChangeMsgDao.clearExpiredPromotionPriceChangeMsg(delayDays);
        } catch (Exception e) {
            LOG.error("清理过期的promotion价格变化消息, error : " + e.getMessage(), e);
        }
        finishSignal.countDown();
    }

    @Override
    protected String getBussinessType() {
        return bussinessType;
    }

}