package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.schedule.command.HistoryPriceMsgExecCommand;
import com.yhd.gps.schedule.historyprice.DateSectionPriceInfoHandler;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-7 下午05:19:05
 */
public class HistoryPriceMsgShardingDataExecCommandCreator implements ShardingDataExecCommandCreator {

    private HistoryPriceChangeMsgDao historyPriceChangeMsgDao;
    private DateSectionPriceInfoHandler dateSectionPriceInfoHandler;

    private String bussinessType;

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    public void setHistoryPriceChangeMsgDao(HistoryPriceChangeMsgDao historyPriceChangeMsgDao) {
        this.historyPriceChangeMsgDao = historyPriceChangeMsgDao;
    }

    public void setDateSectionPriceInfoHandler(DateSectionPriceInfoHandler dateSectionPriceInfoHandler) {
        this.dateSectionPriceInfoHandler = dateSectionPriceInfoHandler;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        HistoryPriceMsgExecCommand execCommand = new HistoryPriceMsgExecCommand(shardingIndex, finishSignal,
            bussinessType, historyPriceChangeMsgDao, dateSectionPriceInfoHandler);
        return execCommand;
    }

}
