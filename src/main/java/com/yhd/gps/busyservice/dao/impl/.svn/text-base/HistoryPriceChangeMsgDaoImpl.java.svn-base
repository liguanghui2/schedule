package com.yhd.gps.busyservice.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.utils.GpsCommonUtils;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.busy.vo.SimpleHistoryPriceChangeMsgVo;

/**
 * 
 * 历史价格DAO
 * 
 * @Author liuxiangrong
 * @CreateTime 2016-1-29 下午01:49:03
 */
public class HistoryPriceChangeMsgDaoImpl extends ScheduleBaseDao implements HistoryPriceChangeMsgDao {

    /**
     * 插入单条历史价格数据
     */
    @Override
    public Long insertHistoryPriceChangeMsg(HistoryPriceChangeMsgVo busyHistoryPriceChangeMsgVo) {
        Long result = null;
        try {
            BusyDbContext.switchToMasterDB();
            result = insert("bs_insertHistoryPriceChangeMsg", busyHistoryPriceChangeMsgVo);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    /**
     * 批量插入历史价格数据
     */
    @Override
    public Integer batchInsertHistoryPriceChangeMsg(final List<HistoryPriceChangeMsgVo> priceChangeMsgList) {
        int result = 0;
        try {
            BusyDbContext.switchToMasterDB();
            result = batchInsert("bs_insertHistoryPriceChangeMsg", priceChangeMsgList);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    /**
     * 通过分片ID获取某个分片的未处理的商品列表信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> queryUnDealPmIdsBySharding(int shardingIndex, Date startDate, Date endDate) {
        if(null == startDate || null == endDate) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>(3, 1F);
        params.put("shardingIndex", shardingIndex);
        params.put("startDate", ScheduleDateUtils.format(startDate));
        params.put("endDate", ScheduleDateUtils.format(endDate));
        return queryForList("queryUnDealPmIdsBySharding", params);
    }

    /**
     * 根据商品ID和时间区间查询最近的历史价格信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SimpleHistoryPriceChangeMsgVo> queryRecentHistoryPriceChangeMsgs(List<Long> pmInfoIds, Date startDate,
                                                                           Date endDate) {
        if(CollectionUtils.isEmpty(pmInfoIds) || null == startDate || null == endDate) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>(3, 1F);
        params.put("pmInfoIds", pmInfoIds);
        params.put("startDate", ScheduleDateUtils.format(startDate));
        params.put("endDate", ScheduleDateUtils.format(endDate));
        return queryForList("queryRecentHistoryPriceChangeMsgs", params);
    }
    
    /**
     * 查询一段时间内商品最低价
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SimpleHistoryPriceChangeMsgVo> queryMinHistoryPriceChangeMsgs(List<Long> pmInfoIds, String startDate,
            String endDate) {
        if(CollectionUtils.isEmpty(pmInfoIds) || null == startDate || null == endDate) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>(3, 1F);
        params.put("pmInfoIds", pmInfoIds);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        return queryForList("queryMinHistoryPriceChangeMsgs", params);
    }

    /**
     * 根据ID更新历史价格的状态为已处理is_deal=1
     */
    @Override
    public int batchUpdateHistoryPriceChangeMsg2Dealed(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        int result = 0;
        List<List<Long>> lists = GpsCommonUtils.splitList(ids, ScheduleConstants.DB_MAX_COUNT);
        for(List<Long> list : lists) {
            result += batchUpdateHistoryPriceChangeMsg2DealedInner(list);
        }
        return result;
    }

    private int batchUpdateHistoryPriceChangeMsg2DealedInner(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        int result = 0;
        try {
            BusyDbContext.switchToMasterDB();
            result = update("batchUpdateHistoryPriceChangeMsg2Dealed", ids);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @Override
    public int deleteHistoryPriceChangeMsgByPmIds(List<Long> pmIds) {
        if(CollectionUtils.isEmpty(pmIds)) {
            return 0;
        }
        int result = 0;
        try {
            BusyDbContext.switchToMasterDB();
            result = delete("deleteHistoryPriceChangeMsgByPmIds", pmIds);
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

}