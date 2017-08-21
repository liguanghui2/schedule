package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.service.JDBookSyncService;
import com.yhd.gps.schedule.command.JDDailyGoodsSyncMsgSendCommand;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * 
 * 京东日百价格同步
 * @Author lipengcheng
 * @CreateTime 2017-2-27 下午02:59:06
 */
public class JDDailyGoodsSyncCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;
    private Integer msgSize;
    private JDBookSyncService jdBookSyncService;

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    public void setMsgSize(Integer msgSize) {
        this.msgSize = msgSize;
    }

    public void setJdBookSyncService(JDBookSyncService jdBookSyncService) {
        this.jdBookSyncService = jdBookSyncService;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new JDDailyGoodsSyncMsgSendCommand(shardingIndex, finishSignal, bussinessType, msgSize,
            jdBookSyncService);
    }

}
