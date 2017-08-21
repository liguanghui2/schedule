package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.service.OfflineDataService;
import com.yhd.gps.schedule.command.InitOfflineDataMsgSendCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * @author:liguanghui1
 * @date ：2017-3-15 下午06:06:57
 */
public class InitOfflineDataMsgSendCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;
    private Integer msgSize;
    private OfflineDataService offlineDataService;

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    public void setMsgSize(Integer msgSize) {
        this.msgSize = msgSize;
    }

    public void setOfflineDataService(OfflineDataService offlineDataService) {
        this.offlineDataService = offlineDataService;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new InitOfflineDataMsgSendCommand(shardingIndex, finishSignal, bussinessType, msgSize,
            offlineDataService);
    }
}
