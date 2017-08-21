package com.yhd.gps.schedule.vo.builder;

import java.util.Date;

import com.yihaodian.busy.vo.BusyPmPriceVo;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.busy.vo.builder.HistoryPriceChangeMsgVoBuilder;

/**
 * 
 * ---- 定时器扩展历史价格Vo构建类 ------
 * @Author  lipengfei
 * @CreateTime  2016-8-25 下午04:56:44
 */
public class HistoryPriceChangeMsgVoBuilder4Schedule extends HistoryPriceChangeMsgVoBuilder{
    
    /**
     * 促销价格变化消息转历史价格(记录用)
     * @param priceFatMessages
     * @return
     */
    public static final HistoryPriceChangeMsgVo buildHistoryPriceChangeMsg(BusyPmPriceVo vo) {
        HistoryPriceChangeMsgVo historyPriceChangeMsg = new HistoryPriceChangeMsgVo();

        historyPriceChangeMsg.setProductId(vo.getProductId());
        historyPriceChangeMsg.setMerchantId(vo.getMerchantId());
        historyPriceChangeMsg.setPmInfoId(vo.getPmId());
        historyPriceChangeMsg.setChannelId(vo.getChannelId());
        historyPriceChangeMsg.setPriceChangeMsgType(1);
        historyPriceChangeMsg.setPrice(vo.getCurrentPrice());
        historyPriceChangeMsg.setPriceId(vo.getId());
        if(vo.getCurrentRuleVo() != null) {
            historyPriceChangeMsg.setPromotionId(vo.getCurrentRuleVo().getId());
        }
        historyPriceChangeMsg.setStartTime(vo.getPromStartTime());
        historyPriceChangeMsg.setEndTime(vo.getPromEndTime());
        historyPriceChangeMsg.setRuleType(vo.getRuleType());
        historyPriceChangeMsg.setPromName("补偿数据");
        historyPriceChangeMsg.setPromType(vo.getPromoteType());
        historyPriceChangeMsg.setIsDeal(1);
        historyPriceChangeMsg.setCreateTime(new Date());

        return historyPriceChangeMsg;
    }
}