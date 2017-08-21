package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.pricechange.vo.PromotionPriceChangeMsg;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2015-6-16 下午01:54:58
 */
public interface PromotionPriceChangeMsgDao {

    public Integer insertPromotionPriceChangeMsg(final List<PromotionPriceChangeMsg> msgs);

    /**
     * 分页抓取未发送的促销价格变化消息
     * @param shardingIndex
     * @param pageSize
     * @return
     */
    public List<PromotionPriceChangeMsg> getUnSendPromotionPriceChangeMsgs(int shardingIndex, Integer pageSize);

    /**
     * 分页抓取补偿的促销价格变化消息
     * @param pageSize
     * @return
     */
    public List<PromotionPriceChangeMsg> getCompensatePromotionPriceChangeMsgs(Integer pageSize);

    /**
     * 清理过期的促销价格变化消息
     * @param delayDays
     * @return
     */
    public Integer clearExpiredPromotionPriceChangeMsg(Integer delayDays);

    int batchUpdatePromotionPriceChangeMsgStatus2Send(List<Long> ids);
}
