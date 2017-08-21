package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.schedule.vo.PmPriceVo;

/**
 * @author:liguanghui1
 * @date ：2017-3-16 上午10:02:30
 */
public interface PmPriceDao {

    List<PmPriceVo> getPmInfoIdsFromPmPriceForOfflineData(Integer shardingIndex, Long offset, String beginTime);
    
    /**
     * 根据productId列表查询pm_price数据
     * @param productIds
     * @return
     */
    List<PmPriceVo> getPmPricesByProductIds(List<Long> productIds);
}
