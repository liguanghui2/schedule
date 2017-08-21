package com.yhd.gps.busyservice.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.vo.BusyPmInfoVo;
import com.yhd.gps.schedule.vo.JDBookMessageVo;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-2 下午01:20:46
 */
public class BusyPmInfoDaoImpl extends ScheduleBaseDao implements BusyPmInfoDao {

    /**
     * 批量根据子品求组合商品
     * @param subPmIds
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> batchGetMainCombinePmIdBySubPmIds(List<Long> subPmIds) {
        if(CollectionUtils.isEmpty(subPmIds)) {
            return Collections.emptyList();
        }
        // 去重
        subPmIds = new ArrayList<Long>(new HashSet<Long>(subPmIds));
        Map<String, List<Long>> param = new HashMap<String, List<Long>>(1, 1F);
        param.put("subpminfoIds", subPmIds);

        return (List<Long>) queryForList("batchGetMainCombinePminfoIdByBusSubInfoIds", param);
    }

    @Override
    public BusyPmInfoVo getPmInfoVo(Long pmId) {
        BusyPmInfoVo result = null;
        if(pmId != null && pmId > 0) {
            result = (BusyPmInfoVo) queryForObject("getPmInfoVo", pmId);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<JDBookMessageVo> getJDBookSyncInfoByShardingIndex(Integer shardingIndex, Long offset,
                                                                  List<Long> merchantIds) {
        if(null == shardingIndex || null == offset || CollectionUtils.isEmpty(merchantIds)) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<String, Object>(3, 1F);
        params.put("shardingIndex", shardingIndex);
        params.put("pmId", offset);
        params.put("merchantIds", merchantIds);
        return queryForList("getPmInfoIdByShardingIndex", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<JDBookMessageVo> getJDBookSyncInfoByPmIds(List<Long> pmIds) {
        if(CollectionUtils.isEmpty(pmIds)) {
            return Collections.emptyList();
        }
        return queryForList("getJDBookSyncInfoByPmIds", pmIds);
    }
}
