package com.yhd.gps.busyservice.handler;

import com.yhd.gps.busyservice.strategy.IProcessWarningStrategy;
import com.yhd.gps.busyservice.strategy.impl.ProcessWarningWithExcuteSql;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.GpsScheduleWarningRuleVo;

/**
 * 
 * 报警处理
 * @Author lipengcheng
 * @CreateTime 2017-3-6 上午11:24:59
 */
public class ProcessWarningHandler {
    public static void handle(GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo) {
        if(null == gpsScheduleWarningRuleVo) {
            return;
        }
        IProcessWarningStrategy processWarningStrategy = null;
        if(gpsScheduleWarningRuleVo.getRuleType() == ScheduleConstants.GPS_SCHEDULE_WARNIN_RULE_TYPE_EXECUTE_SQL) {
            processWarningStrategy = new ProcessWarningWithExcuteSql();
        }
        // 其他类型,待扩充

        if(null != processWarningStrategy) {
            processWarningStrategy.execute(gpsScheduleWarningRuleVo);
        }
    }
}
