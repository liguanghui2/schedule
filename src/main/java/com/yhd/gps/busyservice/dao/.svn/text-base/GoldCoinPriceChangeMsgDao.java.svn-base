package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.schedule.vo.GoldCoinPriceChangeMsg;

public interface GoldCoinPriceChangeMsgDao {

    /**
     * 分页抓取未发送的金币促销变化消息
     */
    public List<GoldCoinPriceChangeMsg> getUnSendGoldCoinPriceChangeMsg(int shardingIndex, Integer pageSize);

    /**
     * 更新金币促销变化扫描表
     */
    public int batchUpdateGoldCoinPriceChangeMsgStatus2Send(final List<Long> ids);
}
