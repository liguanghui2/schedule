package com.yhd.gps.busyservice.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.yhd.gps.busyservice.dao.SyncDataLogDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.schedule.vo.SyncDataLogVo;

public class SyncDataLogDaoImpl extends ScheduleBaseDao implements SyncDataLogDao {

    @Override
    public int insertSyncDataLog(SyncDataLogVo syncDataLogVo) {
        try {
            if(syncDataLogVo != null) {
                BusyDbContext.switchToMasterDB();
                Long id= insert("gps_insertSyncDataLog", syncDataLogVo);
                if(null != id) {
                    return 1;
                }
            }
            return 0;
        } finally {
            BusyDbContext.reset();
        }
    }

    @Override
    public int batchInsertSyncDataLog(final List<SyncDataLogVo> syncDataLogVos) {
        if(CollectionUtils.isEmpty(syncDataLogVos)) {
            return 0;
        }
        int result = 1;
        try {
            BusyDbContext.switchToMasterDB();
            return batchInsert("gps_insertSyncDataLog", syncDataLogVos);
        } catch (Exception e) {
            result = 0;
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SyncDataLogVo> getSyncDataLogVoList() {
        return queryForList("gps_getSyncDataLogVoList");
    }

    @Override
    public int batchDeleteSyncDataLogVo(List<Long> keyIds) {
        int result = 0;
        if(CollectionUtils.isEmpty(keyIds)) {
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("ids", keyIds);
        try {
            BusyDbContext.switchToMasterDB();
            result = delete("gps_batchDeleteSyncDataLogVo", params);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

}