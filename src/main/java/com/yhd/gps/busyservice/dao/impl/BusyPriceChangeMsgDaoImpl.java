package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.BusyPriceChangeMsgDao;
import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.utils.GpsCommonUtils;
import com.yihaodian.busy.vo.BusyPriceChangeMsgVo;


public class BusyPriceChangeMsgDaoImpl extends ScheduleBaseDao implements BusyPriceChangeMsgDao {

    @Override
    public int insertPriceChangeMsgVos(List<BusyPriceChangeMsgVo> msgs) {
        if(CollectionUtils.isEmpty(msgs)) {
            return 0;
        }
        try {
            BusyDbContext.switchToMasterDB();
            return batchInsert("insertPriceChangeMsgVo", msgs);
        } finally {
            BusyDbContext.reset();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusyPriceChangeMsgVo> getUnSendPriceChangeMsgList(int shardingIndex, Integer pageSize) {
        if(pageSize == null) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>(2, 1F);
        params.put("shardingIndex", shardingIndex);
        params.put("pageSize", pageSize);
        return (List<BusyPriceChangeMsgVo>) queryForList("getUnSendPriceChangeMsgList", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusyPriceChangeMsgVo> getCompensatePriceChangeMsgList(Integer pageSize) {
        if(pageSize == null) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("pageSize", pageSize);
        return (List<BusyPriceChangeMsgVo>) queryForList("getCompensatePriceChangeMsgList", params);
    }

    @Override
    public int clearExpiredPriceChangeTaskData(Integer delayDays) {
        if(delayDays == null || delayDays == 0) {
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("delayTime", ScheduleDateUtils.getDateAddDaysStr(new Date(), delayDays));
        try {
            BusyDbContext.switchToMasterDB();
            return delete("clearExpiredPriceChangeMsg", params);
        } finally {
            BusyDbContext.reset();
        }
    }

    @Override
    public int batchUpdatePriceChangeMsgStatus2Send(final List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        int result = 0;
        List<List<Long>> lists = GpsCommonUtils.splitList(ids, ScheduleConstants.DB_MAX_COUNT);
        for(List<Long> list : lists) {
            result += batchUpdatePriceChangeMsgStatus2SendInner(list);
        }
        return result;
    }

    private int batchUpdatePriceChangeMsgStatus2SendInner(final List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>(2, 1F);
        params.put("ids", ids);
        params.put("currentIp", PoolUtils.getPoolIP());
        try {
            BusyDbContext.switchToMasterDB();
            return update("batchUpdatePriceChangeMsgStatus2Send", params);

        } finally {
            BusyDbContext.reset();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusyPriceChangeMsgVo> compensatePriceChangeMsgs(int shardingIndex, Integer pageSize, String startTime,
                                                                String endTime) {
        if(pageSize == null) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>(2, 1F);
        params.put("shardingIndex", shardingIndex);
        params.put("pageSize", pageSize);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return (List<BusyPriceChangeMsgVo>) queryForList("compensatePriceChangeMsgs", params);
    }

    @Override
    public int batchUpdateCompensatePriceChangeMsgStatus(List<Long> ids) {
        int result = 0;
        if(CollectionUtils.isEmpty(ids)) {
            return result;
        }
        List<List<Long>> lists = GpsCommonUtils.splitList(ids, ScheduleConstants.DB_MAX_COUNT);
        for(List<Long> list : lists) {
            if(CollectionUtils.isEmpty(list)) {
                return result;
            }
            Map<String, Object> params = new HashMap<String, Object>(1, 1F);
            params.put("ids", list);
            try {
                BusyDbContext.switchToMasterDB();
                result += update("batchUpdateCompensatePriceChangeMsgStatus", params);

            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

}