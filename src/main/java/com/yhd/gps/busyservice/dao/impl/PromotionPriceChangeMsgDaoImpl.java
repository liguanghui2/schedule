package com.yhd.gps.busyservice.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.PromotionPriceChangeMsgDao;
import com.yhd.gps.pricechange.vo.PromotionPriceChangeMsg;
import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.utils.GpsCommonUtils;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2015-6-16 下午01:55:33
 */
public class PromotionPriceChangeMsgDaoImpl extends ScheduleBaseDao implements PromotionPriceChangeMsgDao {

    @Override
    public Integer insertPromotionPriceChangeMsg(final List<PromotionPriceChangeMsg> msgs) {
        int result = 0;
        if(CollectionUtils.isEmpty(msgs)) {
            return 0;
        }
        try {
            BusyDbContext.switchToMasterDB();
            result = batchInsert("insertPromotionPriceChangeMsg", msgs);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PromotionPriceChangeMsg> getUnSendPromotionPriceChangeMsgs(int shardingIndex, Integer pageSize) {
        Map<String, Object> parameters = new HashMap<String, Object>(2, 1F);
        parameters.put("shardingIndex", shardingIndex);
        parameters.put("pageSize", pageSize);
        return (List<PromotionPriceChangeMsg>) queryForList("getUnSendPromotionPriceChangeMsgs", parameters);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PromotionPriceChangeMsg> getCompensatePromotionPriceChangeMsgs(Integer pageSize) {
        Map<String, Object> parameters = new HashMap<String, Object>(1, 1F);
        parameters.put("pageSize", pageSize);

        return (List<PromotionPriceChangeMsg>) queryForList("getCompensatePromotionPriceChangeMsgs", parameters);
    }

    @Override
    public Integer clearExpiredPromotionPriceChangeMsg(Integer delayDays) {
        int result = 0;
        if(delayDays == null || delayDays == 0) {
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("delayTime", ScheduleDateUtils.getDateAddDaysStr(new Date(), delayDays));
        try {
            BusyDbContext.switchToMasterDB();
            result = delete("clearExpiredPromotionPriceChangeMsg", params);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @Override
    public int batchUpdatePromotionPriceChangeMsgStatus2Send(final List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return 0;
        }

        int result = 0;
        List<List<Long>> lists = GpsCommonUtils.splitList(ids, ScheduleConstants.DB_MAX_COUNT);
        for(List<Long> list : lists) {
            result += batchUpdatePromotionPriceChangeMsgStatus2SendInner(list);
        }
        return result;
    }

    private int batchUpdatePromotionPriceChangeMsgStatus2SendInner(final List<Long> ids) {
        int result = 0;
        if(CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>(2, 1F);
        params.put("ids", ids);
        params.put("currentIp", PoolUtils.getPoolIP());
        try {
            BusyDbContext.switchToMasterDB();
            result = update("batchUpdatePromotionPriceChangeMsgStatus2Send", params);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

}