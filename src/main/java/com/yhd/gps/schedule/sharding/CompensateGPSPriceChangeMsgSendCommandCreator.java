package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.dao.BusyPriceChangeMsgDao;
import com.yhd.gps.busyservice.msg.CompensateGPSPriceChangeMsgSender;
import com.yhd.gps.schedule.command.CompensateGPSPriceChangeMsgSendCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * @Author  lipengfei
 * @CreateTime  2016-8-11 下午05:05:34
 */
public class CompensateGPSPriceChangeMsgSendCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;

    private Integer pageSize;

    private BusyPriceChangeMsgDao busyPriceChangeMsgDao;

    private CompensateGPSPriceChangeMsgSender sender;

    public void setBusyPriceChangeMsgDao(BusyPriceChangeMsgDao busyPriceChangeMsgDao) {
        this.busyPriceChangeMsgDao = busyPriceChangeMsgDao;
    }

    public void setSender(CompensateGPSPriceChangeMsgSender sender) {
        this.sender = sender;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new CompensateGPSPriceChangeMsgSendCommand(shardingIndex, finishSignal, bussinessType, pageSize,
            busyPriceChangeMsgDao, sender);
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

}