package com.yhd.gps.busyservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.BusyGpsPromotionDao;
import com.yhd.gps.busyservice.dao.BusyProductPromRuleDao;
import com.yhd.gps.busyservice.service.BusyLogService;
import com.yhd.gps.busyservice.service.BusyPromRuleCleanService;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yihaodian.busy.common.BusyConstants;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

public class BusyPromRuleCleanServiceImpl implements BusyPromRuleCleanService {

    private static final Log log = LogFactory.getLog(BusyPromRuleCleanServiceImpl.class);

    private BusyProductPromRuleDao busyProductPromRuleDao;

    private BusyGpsPromotionDao busyGpsPromotionDao;

    private BusyLogService busyLogService;

    public void setBusyProductPromRuleDao(BusyProductPromRuleDao busyProductPromRuleDao) {
        this.busyProductPromRuleDao = busyProductPromRuleDao;
    }

    public void setBusyGpsPromotionDao(BusyGpsPromotionDao busyGpsPromotionDao) {
        this.busyGpsPromotionDao = busyGpsPromotionDao;
    }

    public void setBusyLogService(BusyLogService busyLogService) {
        this.busyLogService = busyLogService;
    }

    @Override
    public Long cleanOutDatePromRule(Date outDate, Date deleteDate, Integer pageSize) {
        Long deleteCount = 0L;

        Integer startRow = 1;
        try {
            int loopTimes = 1;
            // 每次查询pageSize条记录进行清除
            List<BSProductPromRuleVo> list = busyProductPromRuleDao.queryOutDatePromRuleList(outDate, deleteDate,
                    startRow, pageSize);
            while(CollectionUtils.isNotEmpty(list)) {
                Long start = System.currentTimeMillis();
                // 批量删除本次查出来的过期的促销规则
                List<Long> historyRuleIds = new ArrayList<Long>(list.size());
                for(BSProductPromRuleVo vo : list) {
                    historyRuleIds.add(vo.getId());
                }
                busyProductPromRuleDao.batchDeleteProductPromRuleFromDbByIds(historyRuleIds);
                busyLogService.batchInsertProductPromRuleLogs(list, BusyConstants.OP_TYPE_DELETE);
                Long end = System.currentTimeMillis();
                log.debug("---###---[GPS]cleanOutDatePromRule: loop=[" + ++loopTimes + "], cleanDate=[" + list.size()
                          + "], useTime=[" + (end - start) + "]ms");

                deleteCount += list.size();

                // 删除一页后,继续查询下一页需要删除的数据
                list = busyProductPromRuleDao.queryOutDatePromRuleList(outDate, deleteDate, startRow, pageSize);
            }
        } catch (Exception e) {
            log.error("BusyPromRuleCleanServiceImpl.cleanOutDatePromRule error: " + e.getMessage(), e);
        }
        return deleteCount;
    }

    @Override
    public Long cleanOutDateGpsPromotion(Date outDate, Date deleteDate, Integer pageSize) {
        Long deleteCount = 0L;
        Integer startRow = 1;
        try {
            int loopTimes = 1;
            // 每次查询pageSize条记录进行清除
            List<GPSPromotionVo> list = busyGpsPromotionDao.queryOutDateGpsPromotionList(outDate, deleteDate, startRow,
                    pageSize);
            while(CollectionUtils.isNotEmpty(list)) {
                Long start = System.currentTimeMillis();
                // 批量删除本次查出来的过期的数据
                List<Long> historyRuleIds = new ArrayList<Long>(list.size());
                for(GPSPromotionVo vo : list) {
                    historyRuleIds.add(vo.getId());
                }
                busyGpsPromotionDao.batchDeleteGpsPromotionFromDbByIds(historyRuleIds);
                busyLogService.batchInsertGpsPromotionLogs(list, BusyConstants.OP_TYPE_DELETE_PHYSICAL, "过期数据清除");
                Long end = System.currentTimeMillis();
                log.error("---###---[GPS]cleanOutDatePromRule: loop=[" + ++loopTimes + "], cleanDate=[" + list.size()
                          + "], useTime=[" + (end - start) + "]ms");
                deleteCount += list.size();

                // 删除一页后,继续查询下一页需要删除的数据
                list = busyGpsPromotionDao.queryOutDateGpsPromotionList(outDate, deleteDate, startRow, pageSize);
            }
        } catch (Exception e) {
            log.error("BusyPromRuleCleanServiceImpl.cleanOutDatePromRule error: ", e);
        }
        return deleteCount;
    }

}
