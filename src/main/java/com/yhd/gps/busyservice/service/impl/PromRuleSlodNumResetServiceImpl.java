package com.yhd.gps.busyservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.BusyProductPromRuleDao;
import com.yhd.gps.busyservice.service.PromRuleSoldNumResetService;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.dao.DataProcessScannerDao;
import com.yhd.gps.schedule.vo.DataProcessScannerVo;
import com.yhd.gps.schedule.vo.ProductPromRule4ResetSoldNumVo;
import com.yihaodian.busy.common.BusyConstants;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.client.facade.BusyCacheAdminFacadeService;

public class PromRuleSlodNumResetServiceImpl implements PromRuleSoldNumResetService {

    private static final Log log = LogFactory.getLog(PromRuleSlodNumResetServiceImpl.class);

    private static final BusyCacheAdminFacadeService busyCacheAdminFacadeService = BusyStockClientUtil
            .getBusyCacheAdminFacadeService();

    private BusyProductPromRuleDao busyProductPromRuleDao;
    private DataProcessScannerDao dataProcessScannerDao;

    public void setBusyProductPromRuleDao(BusyProductPromRuleDao busyProductPromRuleDao) {
        this.busyProductPromRuleDao = busyProductPromRuleDao;
    }

    public void setDataProcessScannerDao(DataProcessScannerDao dataProcessScannerDao) {
        this.dataProcessScannerDao = dataProcessScannerDao;
    }

    @Override
    public Integer resetPromRuleSoldNum(List<DataProcessScannerVo> dataProcessScannerVos, Set<Long> scannerIds) {
        Integer result = 0;
        Long start = System.currentTimeMillis();
        final int size = dataProcessScannerVos.size();
        // 构造ruleId和scannerId关系
        Map<Long, Long> ruleId2ScannerIdMap = new HashMap<Long, Long>(size, 1F);
        for(DataProcessScannerVo dataProcessScannerVo : dataProcessScannerVos) {
            ruleId2ScannerIdMap.put(dataProcessScannerVo.getRefId(), dataProcessScannerVo.getId());
            scannerIds.add(dataProcessScannerVo.getId());
        }
        // 所有的ruleId，用于查促销
        List<Long> allRuleIds = new ArrayList<Long>(ruleId2ScannerIdMap.keySet());
        // 根据ruleIds查询促销
        List<ProductPromRule4ResetSoldNumVo> productPromRuleVos = busyProductPromRuleDao
                .batchGetProductPromRuleListByIds(allRuleIds);

        // 需要删除扫描表的记录
        List<Long> needDelScannerIds = new ArrayList<Long>(size);
        // 需要重置soldNum的促销
        Map<Long, Long> needResetSoldNumMap = new HashMap<Long, Long>(size, 1F);
        for(ProductPromRule4ResetSoldNumVo productPromRuleVo : productPromRuleVos) {
            // 是否需要重置soldNum
            if(isNeedResetSoldNum(productPromRuleVo)) {
                needResetSoldNumMap.put(productPromRuleVo.getId(), productPromRuleVo.getPmId());
            }
            // 过期和无效的记录需要从扫描表删除
            if(isNeedDelFromScanner(productPromRuleVo)) {
                needDelScannerIds.add(ruleId2ScannerIdMap.get(productPromRuleVo.getId()));
            }
        }

        // 先更新soldNum
        if(!needResetSoldNumMap.isEmpty()) {
            List<Long> ruleIds = new ArrayList<Long>(needResetSoldNumMap.keySet());
            int resetCount = busyProductPromRuleDao.batchUpdateProductPromRuleSoldNumByRuleIds(ruleIds);
            // 清理价格促销缓存
            busyCacheAdminFacadeService.batchFlushRuleVoCache(ruleIds);
            // 清理价格缓存
            List<Long> pmIds = new ArrayList<Long>(needResetSoldNumMap.values());
            busyCacheAdminFacadeService.batchFlushPmPriceCache(pmIds);
            result = resetCount;
        }

        // 再从扫描表删除查不到的促销
        if(CollectionUtils.isNotEmpty(needDelScannerIds)) {
            dataProcessScannerDao.batchDeleteDataProcessScannerByIds(needDelScannerIds);
        }

        Long end = System.currentTimeMillis();
        log.debug("---###---[GPS]resetPromRuleSoldNum: processDate=[" + dataProcessScannerVos.size() + "], useTime=["
                  + (end - start) + "]ms");
        return result;
    }

    private static Boolean isNeedResetSoldNum(ProductPromRule4ResetSoldNumVo productPromRuleVo) {
        // 当日00:00:00
        Date currDay = ScheduleDateUtils.parseDate(new Date());
        // 需要重置soldNum的情况：促销类型是6,活动有效，促销开始时间小于或等于当前时间，促销截止时间大于当前时间,sold_date<当日00:00:00，sold_num>0
        if(productPromRuleVo != null
           && productPromRuleVo.getPromoteType() == BusyConstants.RULE_LIMIT_ALL_USER_ORDER_NOTALLOWBASEPRICE
           && productPromRuleVo.getRuleStatus() == ScheduleConstants.PRODUCT_PROM_RULE_STATUS_IS_VALID
           && (productPromRuleVo.getPromStartTime().before(new Date()) || productPromRuleVo.getPromStartTime()
                   .compareTo(new Date()) == 0) && productPromRuleVo.getPromEndTime().after(new Date())
           && productPromRuleVo.getSoldDate() != null && productPromRuleVo.getSoldDate().before(currDay)
           && productPromRuleVo.getSoldNum() > 0) {
            return true;
        }
        return false;
    }

    // 已逻辑删除或已过期价格促销需要从扫描表删除
    private static Boolean isNeedDelFromScanner(ProductPromRule4ResetSoldNumVo productPromRuleVo) {
        Boolean result = false;
        if(productPromRuleVo == null) {
            return result;
        }
        if(productPromRuleVo.getRuleStatus() != ScheduleConstants.PRODUCT_PROM_RULE_STATUS_IS_VALID
           || productPromRuleVo.getPromEndTime().before(new Date())) {
            result = true;
        }
        return result;
    }
}
