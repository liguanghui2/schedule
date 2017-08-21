package com.yhd.gps.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.service.PromRuleSoldNumResetService;
import com.yhd.gps.schedule.command.PromRuleSoldNumResetCommand;
import com.yhd.gps.schedule.dao.DataProcessScannerDao;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * 
 * 重置价格促销表（product_prom_rule）soldNum
 * @Author lipengcheng
 * @CreateTime 2016-7-10 下午10:15:01
 */
public class ResetPromRuleSoldNumCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;

    private Integer pageSize;
    private DataProcessScannerDao dataProcessScannerDao;
    private PromRuleSoldNumResetService promRuleSoldNumResetService;

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setDataProcessScannerDao(DataProcessScannerDao dataProcessScannerDao) {
        this.dataProcessScannerDao = dataProcessScannerDao;
    }

    public void setPromRuleSoldNumResetService(PromRuleSoldNumResetService promRuleSoldNumResetService) {
        this.promRuleSoldNumResetService = promRuleSoldNumResetService;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        return new PromRuleSoldNumResetCommand(shardingIndex, finishSignal, bussinessType, pageSize,
            dataProcessScannerDao, promRuleSoldNumResetService);
    }

}
