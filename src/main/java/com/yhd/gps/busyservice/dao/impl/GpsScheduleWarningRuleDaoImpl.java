package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yhd.gps.busyservice.dao.GpsScheduleWarningRuleDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.schedule.vo.GpsScheduleWarningRuleVo;

/**
 * 
 * ---- 请加注释 ------
 * @Author lipengcheng
 * @CreateTime 2017-3-3 上午10:21:18
 */
public class GpsScheduleWarningRuleDaoImpl extends ScheduleBaseDao implements GpsScheduleWarningRuleDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<GpsScheduleWarningRuleVo> getGpsScheduleWarningRuleVo(int shardingIndex, List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        Map<String, Object> map = new HashMap<String, Object>(2, 1F);
        map.put("shardingIndex", shardingIndex);
        map.put("ids", ids);
        return queryForList("getGpsScheduleWarningRuleVo", map);
    }

    @Override
    public List<GpsScheduleWarningRuleVo> getAllGpsScheduleWarningRuleVoList() {
        return queryForList("getAllGpsScheduleWarningRuleVoList");
    }

    @Override
    public GpsScheduleWarningRuleVo getGpsScheduleWarningRuleVoById(Long id) {
        if(id == null || id <= 0) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>(1, 1F);
        map.put("id", id);
        return (GpsScheduleWarningRuleVo) queryForObject("getGpsScheduleWarningRuleVoById", map);
    }

    @Override
    public Long insertGpsScheduleWarningRuleVo(GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo) {
        Long result = 0L;
        if(gpsScheduleWarningRuleVo == null || StringUtils.isBlank(gpsScheduleWarningRuleVo.getSchema1())
           || StringUtils.isBlank(gpsScheduleWarningRuleVo.getSql1())
           || StringUtils.isBlank(gpsScheduleWarningRuleVo.getSchema2())
           || StringUtils.isBlank(gpsScheduleWarningRuleVo.getSql2())) {
            return result;
        }
        try {
            BusyDbContext.switchToMasterDB();
            result = insert("insertGpsScheduleWarningRuleVo", gpsScheduleWarningRuleVo);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @Override
    public int updateGpsScheduleWarningRuleVo(GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo) {
        int result = 0;
        if(gpsScheduleWarningRuleVo == null || null == gpsScheduleWarningRuleVo.getId()) {
            return result;
        }
        try {
            BusyDbContext.switchToMasterDB();
            result = update("updateGpsScheduleWarningRuleVo", gpsScheduleWarningRuleVo);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @Override
    public int deleteGpsScheduleWarningRuleVoById(Long id) {
        int result = 0;
        if(null == id) {
            return result;
        }
        try {
            BusyDbContext.switchToMasterDB();
            Map<String, Object> map = new HashMap<String, Object>(1, 1F);
            map.put("id", id);
            result = delete("deleteGpsScheduleWarningRuleVoById", map);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

}
