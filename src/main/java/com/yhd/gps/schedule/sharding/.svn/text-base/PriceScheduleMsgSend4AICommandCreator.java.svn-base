package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.schedule.command.PriceScheduleMsgSend4AICommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * @author:liguanghui1
 * @date ：2017-4-25 下午01:52:17
 */
public class PriceScheduleMsgSend4AICommandCreator implements ShardingDataExecCommandCreator {
    private String bussinessType;

    @SuppressWarnings("rawtypes")
    @Override
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new PriceScheduleMsgSend4AICommand(shardingIndex, finishSignal, bussinessType);
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }
}
