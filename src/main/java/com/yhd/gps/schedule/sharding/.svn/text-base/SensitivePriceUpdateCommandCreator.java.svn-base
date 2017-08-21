package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.service.AdvicePriceService;
import com.yhd.gps.schedule.command.HandleAdvicedPriceCommand;
import com.yhd.gps.schedule.command.SensitivePriceUpdateCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * Created by zhangshuqiang on 2017/5/2.
 *
 */
public class SensitivePriceUpdateCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;

    private int pageSize;

    private AdvicePriceService advicePriceService;

    public void setAdvicePriceService(AdvicePriceService advicePriceService) {
        this.advicePriceService = advicePriceService;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new SensitivePriceUpdateCommand(shardingIndex, finishSignal, bussinessType, pageSize, advicePriceService);
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

}