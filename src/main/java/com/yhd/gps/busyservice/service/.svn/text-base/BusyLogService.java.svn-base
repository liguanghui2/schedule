package com.yhd.gps.busyservice.service;

import java.util.List;

import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.vo.JumperMessageLog;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public interface BusyLogService {

    /**
     * 插入消息日志
     * @param messageLogs
     */
    public void batchInsertMessageLog(final List<JumperMessageLog> messageLogs);

    public int batchInsertProductPromRuleLogs(List<BSProductPromRuleVo> ruleList, String opType);

    /**
     * 批量插入GPSPromotion的日志
     * @param gpsPromotionVos
     * @param opType
     * @param memo
     * @return
     */
    public int batchInsertGpsPromotionLogs(final List<GPSPromotionVo> gpsPromotionVos, String opType, String memo);

}
