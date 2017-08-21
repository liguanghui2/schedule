package com.yhd.gps.busyservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.BusyLogDao;
import com.yhd.gps.busyservice.dao.BusyPriceJumperLogDao;
import com.yhd.gps.busyservice.service.BusyLogService;
import com.yhd.gps.promotion.vo.GPSPromotionLogVo;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.BusyLogUtils;
import com.yhd.gps.schedule.vo.BusyProductPromRuleLogVo;
import com.yhd.gps.schedule.vo.JumperMessageLog;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyLogServiceImpl implements BusyLogService {
    private static final Log log = LogFactory.getLog(BusyPromRuleCleanServiceImpl.class);

    private BusyPriceJumperLogDao busyPriceJumperLogDao;
    private BusyLogDao busyLogDao;

    public void setBusyPriceJumperLogDao(BusyPriceJumperLogDao busyPriceJumperLogDao) {
        this.busyPriceJumperLogDao = busyPriceJumperLogDao;
    }

    public void setBusyLogDao(BusyLogDao busyLogDao) {
        this.busyLogDao = busyLogDao;
    }

    @Override
    public void batchInsertMessageLog(final List<JumperMessageLog> messageLogs) {
        busyPriceJumperLogDao.batchInsertMessageLog(messageLogs);
    }

    /**
     * 新增价格促销清理日志,此处无需发价格变化消息
     */
    @Override
    public int batchInsertProductPromRuleLogs(List<BSProductPromRuleVo> ruleList, String opType) {
        int result = 0;
        try {
            if(CollectionUtils.isNotEmpty(ruleList)) {
                List<BusyProductPromRuleLogVo> logs = new ArrayList<BusyProductPromRuleLogVo>(ruleList.size());
                for(BSProductPromRuleVo ruleVo : ruleList) {
                    logs.add(BusyLogUtils.buildProductPromRuleLogVo(ruleVo, opType));
                }
                busyLogDao.batchInsertProductPromRuleLogs(logs);
                result = ruleList.size();
            }
        } catch (Exception e) {
            log.error("batch insert ProductPromRuleLog error: ", e);
        }
        return result;
    }

    @Override
    public int batchInsertGpsPromotionLogs(List<GPSPromotionVo> gpsPromotionVos, String opType, String memo) {
        int result = 0;
        try {
            final List<GPSPromotionLogVo> logs = new ArrayList<GPSPromotionLogVo>();
            if(CollectionUtils.isNotEmpty(gpsPromotionVos)) {
                for(GPSPromotionVo gpsPromotionVo : gpsPromotionVos) {
                    if(gpsPromotionVo != null) {
                        GPSPromotionLogVo logVo = BusyLogUtils.buildGPSPromotionLogVo(gpsPromotionVo, opType, memo);
                        logs.add(logVo);
                    }
                }
                busyLogDao.batchInsertGPSPromotionLogs(logs);
                result = logs.size();
            }
        } catch (Exception e) {
            log.error(" insert GPSPromotionVoLog error: ", e);
        }
        return result;
    }

}