package com.yhd.gps.schedule.command;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.dao.GpsScheduleWarningRuleDao;
import com.yhd.gps.busyservice.handler.RuleExecuteHandler;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * 
 * 规则执行
 * @Author lipengcheng
 * @CreateTime 2017-3-2 下午05:53:09
 */
public class RuleExecuteCommand extends ShardingDataExecCommand<List<Object>, List<Object>> {

    private List<Long> ruleIds;
    private GpsScheduleWarningRuleDao gpsScheduleWarningRuleDao;

    public RuleExecuteCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType, List<Long> ruleIds,
                              GpsScheduleWarningRuleDao gpsScheduleWarningRuleDao) {
        super(shardingIndex, finishSignal, bussinessType);
        this.ruleIds = ruleIds;
        this.gpsScheduleWarningRuleDao = gpsScheduleWarningRuleDao;
    }

    @Override
    public List<Object> fetchBussinessDataes(int shardingIndex) {
        // 交给规则处理器执行
        new RuleExecuteHandler(shardingIndex, ruleIds, gpsScheduleWarningRuleDao).exexute();
        return Collections.emptyList();
    }

    @Override
    public ExecResult<List<Object>> doWork(List<Object> objects) {
        return new ExecResult<List<Object>>(null, null);
    }

    @Override
    public int updateData2Processed(final List<Long> ids) {
        return 0;
    }

}