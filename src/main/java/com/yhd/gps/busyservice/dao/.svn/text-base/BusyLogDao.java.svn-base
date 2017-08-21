package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.promotion.vo.GPSPromotionLogVo;
import com.yhd.gps.schedule.vo.BusyProductPromRuleLogVo;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public interface BusyLogDao {

    /**
     * 批量插入ProductPromRule变更日志
     * @param logs
     * @return
     */
    public int batchInsertProductPromRuleLogs(final List<BusyProductPromRuleLogVo> logs);

    /**
     * 批量插入GpsPromotionLog日志对象
     * @param logs
     * @return
     */
    public List<Long> batchInsertGPSPromotionLogs(final List<GPSPromotionLogVo> logs);
    
}
