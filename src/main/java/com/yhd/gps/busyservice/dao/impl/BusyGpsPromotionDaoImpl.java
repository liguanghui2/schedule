package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.BusyGpsPromotionDao;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.database.BusyDbContext;

public class BusyGpsPromotionDaoImpl extends ScheduleBaseDao implements BusyGpsPromotionDao {

    @Override
    public int batchDeleteGpsPromotionFromDbByIds(List<Long> ids) {
        int result = 0;
        if(CollectionUtils.isNotEmpty(ids)) {
            StringBuilder builder = new StringBuilder(ids.size() * 20);
            for(Long id : ids) {
                builder.append(id).append(",");
            }
            try {
                BusyDbContext.switchToMasterDB();
                result = delete("bs_deleteGpsPromotionByIds", ids);
            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GPSPromotionVo> batchGetGpsPromotionByIdsFromDb(List<Long> ids) {
        return queryForList("bs_batchGetGpsPromotionByIds", ids);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GPSPromotionVo> queryOutDateGpsPromotionList(Date outDate, Date deleteDate, Integer startRow,
                                                             Integer pageSize) {
        List<GPSPromotionVo> result = Collections.emptyList();
        if(outDate != null && deleteDate != null && startRow != null && startRow.compareTo(0) >= 0 && pageSize != null
           && pageSize.compareTo(0) > 0) {
            Map<String, Object> params = new HashMap<String, Object>(4, 1F);
            params.put("outDate", outDate);
            params.put("deleteDate", deleteDate);
            params.put("pageSize", pageSize);
            params.put("offset", startRow <= 0 ? 0 : (startRow - 1));
            result = queryForList("bs_queryOutDateGpsPromotionList", params);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GPSPromotionVo> batchGetGpsPromotionVoByPromotionIdsAndPmIds(List<Long> promotionIds, List<Long> pmIds,
                                                                             Date startDate, Date endDate) {
        if(CollectionUtils.isEmpty(promotionIds) && CollectionUtils.isNotEmpty(pmIds) && startDate != null
           && endDate != null && startDate.before(endDate)) {
            return Collections.emptyList();
        }

        Map<String, Object> params = new HashMap<String, Object>(3, 1F);
        params.put("promotionIds", promotionIds);
        params.put("pmIds", pmIds);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        return queryForList("batchGetGpsPromotionVoByPromotionIdsAndPmIds", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> batchGetGpsPromotionIdListByDate(Date startDate, Date endDate) {
        List<Long> promotionIds = Collections.emptyList();
        if(startDate != null && endDate != null && startDate.before(endDate)) {
            Map<String, Object> param = new HashMap<String, Object>(2, 1F);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            promotionIds = queryForList("batchGetGpsPromotionIdListByDate", param);
        }
        return promotionIds;
    }
}