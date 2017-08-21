package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.PmAreaPriceDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yihaodian.busy.vo.BusyPmAreaPriceVo;

/**
 * 
 * 区域价
 * @Author lipengcheng
 * @CreateTime 2017-1-6 下午06:14:38
 */
public class PmAreaPriceDaoImpl extends ScheduleBaseDao implements PmAreaPriceDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<BusyPmAreaPriceVo> getPmAreaPriceVoListByRefPriceIds(List<Long> refPriceIds) {
        if(CollectionUtils.isEmpty(refPriceIds)) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refPriceIds", refPriceIds);
        return queryForList("getPmAreaPriceVoListByRefPriceIds", params);
    }
}