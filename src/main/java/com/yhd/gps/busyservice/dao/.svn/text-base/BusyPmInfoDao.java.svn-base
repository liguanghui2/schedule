package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.schedule.vo.BusyPmInfoVo;
import com.yhd.gps.schedule.vo.JDBookMessageVo;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-2 下午01:20:05
 */
public interface BusyPmInfoDao {

    /**
     * 根据子品的pmInfoId求出主品的pm_info_id
     * @param subPmIds
     * @return
     */
    List<Long> batchGetMainCombinePmIdBySubPmIds(List<Long> subPmIds);

    /**
     * 根据pmId获取pminfo信息
     * 
     * @param pmId
     * @return
     */
    public BusyPmInfoVo getPmInfoVo(Long pmId);

    /**
     * 根据切片id获取pmInfoId
     * 
     * @param shardingIndex
     * @param offset
     * @param merchantIds
     * @return
     */
    List<JDBookMessageVo> getJDBookSyncInfoByShardingIndex(Integer shardingIndex, Long offset, List<Long> merchantIds);

    /**
     * 根据pmId获得京东id
     * 
     * @param pmIds
     * @return
     */
    List<JDBookMessageVo> getJDBookSyncInfoByPmIds(List<Long> pmIds);
}
