package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.schedule.command.SendPriceScheduleCompletenessCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * @author:shengxu1
 * @date ：2017-5-16 下午01:52:17
 */
public class SendPriceScheduleCompletenessCommandCreator implements ShardingDataExecCommandCreator {
    private String bussinessType;

    @SuppressWarnings("rawtypes")
    @Override
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new SendPriceScheduleCompletenessCommand(shardingIndex, finishSignal, bussinessType);
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

}
