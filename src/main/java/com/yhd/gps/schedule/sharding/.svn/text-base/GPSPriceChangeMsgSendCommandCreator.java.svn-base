package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.dao.BusyPriceChangeMsgDao;
import com.yhd.gps.busyservice.msg.GPSPriceChangeMsgSender;
import com.yhd.gps.schedule.command.GPSPriceChangeMsgSendCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-11 下午04:20:44
 */
public class GPSPriceChangeMsgSendCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;

    private Integer pageSize;

    private BusyPriceChangeMsgDao busyPriceChangeMsgDao;

    private GPSPriceChangeMsgSender sender;

    public void setBusyPriceChangeMsgDao(BusyPriceChangeMsgDao busyPriceChangeMsgDao) {
        this.busyPriceChangeMsgDao = busyPriceChangeMsgDao;
    }

    public void setSender(GPSPriceChangeMsgSender sender) {
        this.sender = sender;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new GPSPriceChangeMsgSendCommand(shardingIndex, finishSignal, bussinessType, pageSize,
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