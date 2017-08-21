package com.yhd.gps.busyservice.strategy.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yhd.gps.busy.mail.BusyMailUtil;
import com.yhd.gps.busyservice.dao.RuleExecuteDao;
import com.yhd.gps.busyservice.strategy.IProcessWarningStrategy;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.SpringBeanUtil;
import com.yhd.gps.schedule.vo.GpsScheduleWarningRuleVo;

/**
 * 
 * 执行sql处理报警
 * @Author lipengcheng
 * @CreateTime 2017-3-6 上午11:06:49
 */
public class ProcessWarningWithExcuteSql implements IProcessWarningStrategy {

    @Override
    public void execute(GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo) {
        if(null == gpsScheduleWarningRuleVo) {
            return;
        }
        String schema = gpsScheduleWarningRuleVo.getSchema2();
        String sql = gpsScheduleWarningRuleVo.getSql2();
        // 未配置schema和sql则直接返回
        if(StringUtils.isBlank(schema) || StringUtils.isBlank(sql)) {
            return;
        }

        // 获得dao
        RuleExecuteDao ruleExcuteDao = (RuleExecuteDao) SpringBeanUtil
                .getBean(schema + ScheduleConstants.RULE_EXECUTE_DAO_SUFFIX);
        // 执行sql2
        List<HashMap<String, Object>> result = ruleExcuteDao.getRuleExecuteResult4Warning(sql);
        if(CollectionUtils.isEmpty(result)) {
            return;
        }
        // 设置邮件接收人
        String emailStr = gpsScheduleWarningRuleVo.getEmail();
        String[] toUserMail = null;
        if(emailStr != null) {
            toUserMail = emailStr.split(",");
        }
        BusyMailUtil
                .sendHtmlMail(
                        "定时器报警规则执行结果：id="
                                + (gpsScheduleWarningRuleVo.getId() != null ? gpsScheduleWarningRuleVo.getId() : ""),
                        gpsScheduleWarningRuleVo.getWarningContent(), ScheduleConstants.SEND_MAIL_THRESHOLD, result,
                        toUserMail);

    }
}
