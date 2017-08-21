package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.dao.GoldCoinPriceChangeMsgDao;
import com.yhd.gps.busyservice.msg.GoldCoinPriceChangeMsgSender;
import com.yhd.gps.schedule.command.GoldCoinPriceChangeMsgSendCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

public class GoldCoinPriceChangeMsgSendCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;
    private Integer pageSize;
    private GoldCoinPriceChangeMsgDao goldCoinPriceChangeMsgDao;
    private GoldCoinPriceChangeMsgSender sender;

    public void setGoldCoinPriceChangeMsgDao(GoldCoinPriceChangeMsgDao goldCoinPriceChangeMsgDao) {
        this.goldCoinPriceChangeMsgDao = goldCoinPriceChangeMsgDao;
    }

    public void setSender(GoldCoinPriceChangeMsgSender sender) {
        this.sender = sender;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new GoldCoinPriceChangeMsgSendCommand(shardingIndex, finishSignal, bussinessType, pageSize,
            goldCoinPriceChangeMsgDao, sender);
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getBussinessType() {
        return bussinessType;
    }
}
