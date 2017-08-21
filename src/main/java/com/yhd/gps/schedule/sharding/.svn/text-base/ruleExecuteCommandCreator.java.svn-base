package com.yhd.gps.schedule.sharding;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.dao.GpsScheduleWarningRuleDao;
import com.yhd.gps.schedule.command.RuleExecuteCommand;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleContext;
import com.yhd.schedule.sharding.ShardingDataExecCommandCreator;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * 
 * 规则执行command
 * @Author lipengcheng
 * @CreateTime 2017-3-2 下午04:46:50
 */
public class ruleExecuteCommandCreator implements ShardingDataExecCommandCreator {

    private String bussinessType;
    private GpsScheduleWarningRuleDao gpsScheduleWarningRuleDao;

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public void setGpsScheduleWarningRuleDao(GpsScheduleWarningRuleDao gpsScheduleWarningRuleDao) {
        this.gpsScheduleWarningRuleDao = gpsScheduleWarningRuleDao;
    }

    @Override
    public String getBussinessType() {
        return bussinessType;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ShardingDataExecCommand create(int shardingIndex, CountDownLatch finishSignal) {
        @SuppressWarnings("unchecked")
        List<Long> ruleIds = (List<Long>) ScheduleContext.getValue(ScheduleConstants.RULE_EXECUTE_ID_LIST);
        return new RuleExecuteCommand(shardingIndex, finishSignal, bussinessType, ruleIds, gpsScheduleWarningRuleDao);
    }

}
