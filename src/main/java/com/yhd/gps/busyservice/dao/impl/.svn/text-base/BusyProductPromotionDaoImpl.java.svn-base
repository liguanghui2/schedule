package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.BusyProductPromotionDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.PromotionPeriodTimeVo;

/**
 * 
 * 查询促销LP信息
 * @Author lipengcheng
 * @CreateTime 2016-7-1 上午10:51:27
 */
public class BusyProductPromotionDaoImpl extends ScheduleBaseDao implements BusyProductPromotionDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> batchGetLandingProductPromotionIdListByDate(Date startDate, Date endDate) {
        List<Long> promotionIds = Collections.emptyList();
        if(startDate != null && endDate != null && startDate.before(endDate)) {
            Map<String, Object> param = new HashMap<String, Object>(2, 1F);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            promotionIds = queryForList("batchGetLandingProductPromotionIdListByDate", param);
        }
        return promotionIds;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusyLandingProductVo> batchGetLandingProductVoListByDate(Date startDate, Date endDate,
                                                                         List<Long> promotionIds, Integer startRow,
                                                                         Integer endRow) {
        List<BusyLandingProductVo> result = null;
        if(startDate != null && endDate != null && startDate.before(endDate)
           && CollectionUtils.isNotEmpty(promotionIds) && startRow != null && startRow.compareTo(0) > 0
           && endRow != null && endRow.compareTo(0) > 0) {
            Map<String, Object> param = new HashMap<String, Object>(4, 1F);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("promotionIds", promotionIds);
            param.put("startRow", startRow);
            param.put("endRow", endRow);
            result = queryForList("batchGetLandingProductVoListByDate", param);
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusyLandingProductVo> batchGetLandingProductVoListByDate41Mall(Date startDate, Date endDate,
                                                                               List<Long> promotionIds,
                                                                               List<Long> productIds) {
        List<BusyLandingProductVo> result = null;
        if(startDate != null && endDate != null && startDate.before(endDate)
           && CollectionUtils.isNotEmpty(promotionIds) && CollectionUtils.isNotEmpty(productIds)) {
            Map<String, Object> param = new HashMap<String, Object>(3, 1F);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("promotionIds", promotionIds);
            param.put("productIds", productIds);
            result = queryForList("batchGetLandingProductVoListByDate41Mall", param);
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PromotionPeriodTimeVo> batchGetPromotionPeriodTimeVoList(List<Long> promotionIds) {
        List<PromotionPeriodTimeVo> result = null;
        if(CollectionUtils.isNotEmpty(promotionIds)) {
            Map<String, Object> param = new HashMap<String, Object>(1, 1F);
            param.put("promotionIds", promotionIds);
            result = queryForList("batchGetPromotionPeriodTimeVoList", param);
        }
        return result;
    }

}
