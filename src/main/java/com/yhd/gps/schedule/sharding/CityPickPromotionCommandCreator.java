package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.busyservice.service.CityPickPriceService;
import com.yhd.gps.schedule.command.CityPickPromotionCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * Created by zhangshuqiang on 2017/5/2.
 */
public class CityPickPromotionCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;

    private BusyMiscConfigDao busyMiscConfigDao;

    private CityPickPriceService cityPickPriceService;

    @Override
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new CityPickPromotionCommand(shardingIndex, finishSignal, bussinessType, busyMiscConfigDao, cityPickPriceService);
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public BusyMiscConfigDao getBusyMiscConfigDao() {
        return busyMiscConfigDao;
    }

    public void setBusyMiscConfigDao(BusyMiscConfigDao busyMiscConfigDao) {
        this.busyMiscConfigDao = busyMiscConfigDao;
    }

    public CityPickPriceService getCityPickPriceService() {
        return cityPickPriceService;
    }

    public void setCityPickPriceService(CityPickPriceService cityPickPriceService) {
        this.cityPickPriceService = cityPickPriceService;
    }
}
