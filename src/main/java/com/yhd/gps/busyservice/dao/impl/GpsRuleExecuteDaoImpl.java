package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yhd.gps.busyservice.dao.RuleExecuteDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.common.ScheduleSqlUtils;

/**
 * 
 * ---- 请加注释 ------
 * @Author lipengcheng
 * @CreateTime 2017-3-3 上午10:21:18
 */
public class GpsRuleExecuteDaoImpl extends ScheduleBaseDao implements RuleExecuteDao {

    @Override
    public int getRuleExecuteResult(String sql) {
        sql = ScheduleSqlUtils.validateSql(sql);
        if(StringUtils.isBlank(sql)) {
            return 0;
        }
        return (Integer) queryForObject("getRuleExecuteResult", sql);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HashMap<String, Object>> getRuleExecuteResult4Warning(String sql) {
        sql = ScheduleSqlUtils.validateSql(sql);
        if(StringUtils.isBlank(sql)) {
            return Collections.emptyList();
        }
        return queryForList("getRuleExecuteResult4Warning", sql);
    }
}
