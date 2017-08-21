package com.yhd.gps.busyservice.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.GoldCoinPriceChangeMsgDao;
import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.schedule.vo.GoldCoinPriceChangeMsg;

public class GoldCoinPriceChangeMsgDaoImpl extends ScheduleBaseDao implements GoldCoinPriceChangeMsgDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<GoldCoinPriceChangeMsg> getUnSendGoldCoinPriceChangeMsg(int shardingIndex, Integer pageSize) {
        Map<String, Object> parameters = new HashMap<String, Object>(2, 1F);
        parameters.put("shardingIndex", shardingIndex);
        parameters.put("pageSize", pageSize);
        return (List<GoldCoinPriceChangeMsg>) queryForList("getUnSendGoldCoinPriceChangeMsg", parameters);
    }

    @Override
    public int batchUpdateGoldCoinPriceChangeMsgStatus2Send(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>(2, 1F);
        params.put("ids", ids);
        params.put("currentIp", PoolUtils.getPoolIP());
        try {
            BusyDbContext.switchToMasterDB();
            return update("batchUpdateGoldCoinPriceChangeMsgStatus2Send", params);

        } finally {
            BusyDbContext.reset();
        }

    }

}
