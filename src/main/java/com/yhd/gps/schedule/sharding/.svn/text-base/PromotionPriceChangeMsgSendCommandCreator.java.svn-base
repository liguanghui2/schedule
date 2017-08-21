package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.dao.PromotionPriceChangeMsgDao;
import com.yhd.gps.busyservice.msg.PromotionPriceChangeMsgSender;
import com.yhd.gps.schedule.command.PromotionPriceChangeMsgSendCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-12 下午04:52:27
 */
public class PromotionPriceChangeMsgSendCommandCreator implements ShardingDataExecCommandCreator {
    private String bussinessType;

    private Integer pageSize;

    private PromotionPriceChangeMsgDao promotionPriceChangeMsgDao;

    private PromotionPriceChangeMsgSender sender;

    public void setPromotionPriceChangeMsgDao(PromotionPriceChangeMsgDao promotionPriceChangeMsgDao) {
        this.promotionPriceChangeMsgDao = promotionPriceChangeMsgDao;
    }

    public void setSender(PromotionPriceChangeMsgSender sender) {
        this.sender = sender;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new PromotionPriceChangeMsgSendCommand(shardingIndex, finishSignal, bussinessType, pageSize,
            promotionPriceChangeMsgDao, sender);
    }
}
