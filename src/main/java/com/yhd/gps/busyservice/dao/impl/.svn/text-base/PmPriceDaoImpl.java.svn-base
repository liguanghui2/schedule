package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhd.gps.busyservice.dao.PmPriceDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.vo.PmPriceVo;

/**
 * @author:liguanghui1
 * @date ：2017-3-16 上午10:04:02
 */
public class PmPriceDaoImpl extends ScheduleBaseDao implements PmPriceDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<PmPriceVo> getPmInfoIdsFromPmPriceForOfflineData(Integer shardingIndex, Long offset, String beginTime) {
        if(null == shardingIndex || null == offset) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shardingIndex", shardingIndex);
        params.put("offset", offset);
        params.put("beginTime", beginTime);
        List<PmPriceVo> result = queryForList("getPmInfoIdsFromPmPriceForOfflineData", params);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PmPriceVo> getPmPricesByProductIds(List<Long> productIds){
        if(null == productIds) {
            return Collections.emptyList();
        }
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productIds", productIds);
        List<PmPriceVo> result = queryForList("getPmPricesByProductIds", params);
        return result;
    }
}
