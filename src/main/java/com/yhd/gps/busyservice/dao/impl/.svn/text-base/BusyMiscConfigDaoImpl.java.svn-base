package com.yhd.gps.busyservice.dao.impl;

import org.apache.commons.lang.StringUtils;

import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.database.BusyDbContext;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyMiscConfigDaoImpl extends ScheduleBaseDao implements BusyMiscConfigDao {

    @Override
    public BusyMiscConfigVo getBusyMiscConfigVoByKey(String key) {
        BusyMiscConfigVo result = null;
        if(StringUtils.isNotBlank(key)) {
            result = (BusyMiscConfigVo) queryForObject("getBusyMiscConfigVoByKey", key);
        }
        return result;
    }

    @Override
    public int updateBusyMiscConfigVo(BusyMiscConfigVo miscConfigVo) {
        int result = 0;
        try {
            if(miscConfigVo != null) {
                BusyDbContext.switchToMasterDB();
                result = update("updateBusyMiscConfigVo", miscConfigVo);
            }
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }
}