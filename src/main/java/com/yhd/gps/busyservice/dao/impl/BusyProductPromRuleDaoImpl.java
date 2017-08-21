package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.BusyProductPromRuleDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.schedule.vo.ProductPromRule4ResetSoldNumVo;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyProductPromRuleDaoImpl extends ScheduleBaseDao implements BusyProductPromRuleDao {

    @Override
    public int batchDeleteProductPromRuleFromDbByIds(final List<Long> ruleIds) {
        int result = 0;
        if(CollectionUtils.isNotEmpty(ruleIds) && ruleIds.size() <= 1000) {
            Map<String, Object> params = new HashMap<String, Object>(1, 1F);
            params.put("ruleIdList", ruleIds);
            try {
                BusyDbContext.switchToMasterDB();
                result = delete("deleteProductPromRuleFromDbByIdList", params);
            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BSProductPromRuleVo> queryOutDatePromRuleList(Date outDate, Date deleteDate, Integer startRow,
                                                              Integer pageSize) {
        List<BSProductPromRuleVo> result = Collections.emptyList();
        if(outDate != null && deleteDate != null && startRow != null && startRow.compareTo(0) >= 0 && pageSize != null
           && pageSize.compareTo(0) > 0) {
            Map<String, Object> params = new HashMap<String, Object>(6, 1F);
            params.put("outDate", outDate);
            params.put("deleteDate", deleteDate);
            params.put("startRow", startRow);
            params.put("endRow", startRow + pageSize);
            params.put("pageSize", pageSize);
            params.put("offset", startRow <= 0 ? 0 : (startRow - 1));
            result = queryForList("queryOutDateBSProductPromRuleList", params);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductPromRule4ResetSoldNumVo> batchGetProductPromRuleListByIds(List<Long> ruleIds) {
        List<ProductPromRule4ResetSoldNumVo> result = Collections.emptyList();
        if(CollectionUtils.isNotEmpty(ruleIds)) {
            Map<String, Object> params = new HashMap<String, Object>(1, 1F);
            params.put("ruleIds", ruleIds);
            result = queryForList("batchGetProductPromRuleListByIds", params);
        }
        return result;
    }

    @Override
    public int batchUpdateProductPromRuleSoldNumByRuleIds(List<Long> ruleIds) {
        int result = 0;
        if(CollectionUtils.isNotEmpty(ruleIds)) {
            Map<String, Object> params = new HashMap<String, Object>(2, 1F);
            params.put("ruleIds", ruleIds);
            params.put("currDay", ScheduleDateUtils.parseDate(new Date()));
            try {
                BusyDbContext.switchToMasterDB();
                result = update("batchUpdateProductPromRuleSoldNumByRuleIds", params);
            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductPromRule4ResetSoldNumVo> batchGetProductPromRuleListByMerchantIds(List<Long> merchantIds,
                                                                                         int offset, int pageSize) {
        List<ProductPromRule4ResetSoldNumVo> result = Collections.emptyList();
        if(CollectionUtils.isNotEmpty(merchantIds)) {
            Map<String, Object> params = new HashMap<String, Object>(3, 1F);
            params.put("merchantIds", merchantIds);
            params.put("offset", offset);
            params.put("pageSize", pageSize);
            result = queryForList("batchGetProductPromRuleListByMerchantIds", params);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BSProductPromRuleVo> batchGetProductPromRuleListByPmIds(List<Long> pmIds) {
        List<BSProductPromRuleVo> result = Collections.emptyList();
        if(CollectionUtils.isNotEmpty(pmIds)) {
            Map<String, Object> params = new HashMap<String, Object>(1, 1F);
            params.put("pmIds", pmIds);
            result = queryForList("batchGetProductPromRuleListByPmIds", params);
        }
        return result;
    }

}