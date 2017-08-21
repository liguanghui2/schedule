package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.schedule.command.ComputeModePriceCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * @author:liguanghui1
 * @date ：2017-4-13 下午05:58:33
 */
public class ComputeModePriceCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;

    @SuppressWarnings("rawtypes")
    @Override
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {

        return new ComputeModePriceCommand(shardingIndex, finishSignal, bussinessType);
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

}
