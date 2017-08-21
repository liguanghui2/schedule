package com.yhd.gps.schedule.service;

import java.util.List;

import com.yhd.gps.schedule.vo.HistoryGoldCoinPriceChangeMsgVo;

public interface GoldCoinPriceChangeHistoryService {
    /**
     * 批量插入金币促销历史价格数据
     * @param priceChangeMsgList
     */
    public Integer batchInsertGoldCoinHistoryPriceChangeMsg(List<HistoryGoldCoinPriceChangeMsgVo> goldCoinPriceChangeMsgList);
}
