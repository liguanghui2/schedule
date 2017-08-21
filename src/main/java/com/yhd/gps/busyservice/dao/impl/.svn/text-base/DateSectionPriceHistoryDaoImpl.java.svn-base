package com.yhd.gps.busyservice.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhd.gps.busyservice.dao.DateSectionPriceHistoryDao;
import com.yhd.gps.pricechange.vo.DateSectionPriceInfoVo;
import com.yhd.gps.pricechange.vo.input.QueryDateSectionPriceInfoByPmIdAndDateSectionRequest;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.database.BusyDbContext;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2015-6-11 下午01:56:01
 */
public class DateSectionPriceHistoryDaoImpl extends ScheduleBaseDao implements DateSectionPriceHistoryDao {

    @Override
    public Integer insertDateSectionPriceInfo(final List<DateSectionPriceInfoVo> dateSectionPriceInfos) {
        int result = 0;
        try {
            BusyDbContext.switchToMasterDB();
            result = batchInsert("insertDateSectionPriceInfoVo", dateSectionPriceInfos);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @Override
    public Integer updateDateSectionPriceInfo(final List<DateSectionPriceInfoVo> dateSectionPriceInfos) {
        int result = 0;
        try {
            BusyDbContext.switchToMasterDB();
            result = batchUpdate("updateDateSectionPriceInfoVo", dateSectionPriceInfos);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DateSectionPriceInfoVo> queryDateSectionPriceInfoByPmIdAndDateSection(QueryDateSectionPriceInfoByPmIdAndDateSectionRequest request) {
        return queryForList("queryDateSectionPriceInfoByPmIdAndDateSection", request);
    }
    
    @Override
    public Integer deleteDateSectionPriceInfo(QueryDateSectionPriceInfoByPmIdAndDateSectionRequest request) {
        int result = 0;
        if(null == request || !request.checkParameter()) {
            return 0;
        }
        try {
            BusyDbContext.switchToMasterDB();
            result = delete("deleteDateSectionPriceInfo", request);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @Override
    public List<Long> queryDateSectionPriceInfoByChannelId() {
        return queryForList("queryDateSectionPriceInfoByChannelId");
    }

    @Override
    public void deleteDateSectionPriceHistoryByIds(List<Long> ids) {
        try {
            BusyDbContext.switchToMasterDB();
            Map<Object, Object> param = new HashMap<Object, Object>(1, 1F);
            param.put("ids", ids);
            delete("deleteDateSectionPriceHistoryByIds", param);
        } finally {
            BusyDbContext.reset();
        }
    }

}