package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.backendprice.vo.GpsProductVo;
import com.yhd.gps.busyservice.dao.BusyProductDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.common.ScheduleDateUtils;

/**
 * @author Hikin Yao
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class BusyProductDaoImpl extends ScheduleBaseDao implements BusyProductDao {

    @Override
    public List<GpsProductVo> batchGetGpsProduct(List<Long> productIds) {
        if(CollectionUtils.isEmpty(productIds)) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("productIdList", productIds);
        return queryForList("gps_batchGetGpsProduct", params);
    }

    @Override
    public List<GpsProductVo> getPageGpsProductForSync(Date startTime, Date endTime, Long lastId, Long startRow,
                                                       Long endRow) {
        if(null == startTime || null == endTime) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>(4, 1F);
        params.put("startTime", ScheduleDateUtils.format(startTime));
        params.put("endTime", ScheduleDateUtils.format(endTime));
        params.put("lastId", lastId);
        params.put("startRow", startRow);
        params.put("endRow", endRow);
        return queryForList("gps_getPageGpsProductForSync", params);
    }

    @Override
    public Long getCountGpsProductForSync(Date startTime, Date endTime) {
        if(null == startTime || null == endTime) {
            return 0L;
        }
        Map<String, Object> params = new HashMap<String, Object>(2, 1F);
        params.put("startTime", ScheduleDateUtils.format(startTime));
        params.put("endTime", ScheduleDateUtils.format(endTime));

        return (Long) queryForObject("gps_getCountGpsProductForSync", params);
    }

    @Override
    public List<GpsProductVo> getPageOffSaleProduct(Date startTime,Date endTime, Long offset, Integer pageSize, int shardingIndex) {
        if(null == pageSize) {
            return Collections.emptyList();
        }

        Map<String, Object> params = new HashMap<String, Object>(4, 1F);
        params.put("startTime", ScheduleDateUtils.format(startTime));
        params.put("endTime", ScheduleDateUtils.format(endTime));
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        if(shardingIndex >= 0) {
            params.put("shardingIndex", shardingIndex);
        }
        return queryForList("getPageOffSaleProduct", params);
    }
}
