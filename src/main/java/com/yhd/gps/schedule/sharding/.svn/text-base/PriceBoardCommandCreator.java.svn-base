package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.schedule.command.PriceBoardCommand;
import com.yhd.gps.schedule.remoteService.ProductInfoRemoteService;
import com.yhd.gps.schedule.service.PriceBoardDataRecordService;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * @author sunfei
 * @CreateTime 2017-6-29 上午10:43:57
 */
public class PriceBoardCommandCreator implements ShardingDataExecCommandCreator {
    private String bussinessType;
    private Integer pageSize;
    private ProductInfoRemoteService productRemoteService;
    private PriceBoardDataRecordService priceBoardDataRecordService;

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setProductRemoteService(ProductInfoRemoteService productRemoteService) {
        this.productRemoteService = productRemoteService;
    }

    public void setPriceBoardDataRecordService(PriceBoardDataRecordService priceBoardDataRecordService) {
        this.priceBoardDataRecordService = priceBoardDataRecordService;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new PriceBoardCommand(shardingIndex, finishSignal, bussinessType, pageSize, productRemoteService,
            priceBoardDataRecordService);
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getBussinessType() {
        return bussinessType;
    }

}
