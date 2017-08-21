package com.yhd.gps.busyservice.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.yhd.gps.schedule.job.GpsPriceChangeMsgSendJob;
import com.yhd.gps.schedule.service.GoldCoinPriceChangeHistoryService;
import com.yhd.gps.schedule.vo.GoldCoinPriceChangeMsg;
import com.yhd.gps.schedule.vo.HistoryGoldCoinPriceChangeMsgVo;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.client.facade.BusyPriceFacadeService;
import com.yihaodian.front.busystock.vo.GoldCoinPromRuleVo;
import com.yihaodian.front.databus.client.FrontDatabusClient;

public class GoldCoinPriceChangeMsgSender {
    private static final Log logger = LogFactory.getLog(GpsPriceChangeMsgSendJob.class);
    private static BusyPriceFacadeService busyPriceFacadeService = BusyStockClientUtil.getBusyPriceFacadeService();
    private GoldCoinPriceChangeHistoryService goldCoinPriceChangeHistoryService;

    public void setGoldCoinPriceChangeHistoryService(GoldCoinPriceChangeHistoryService goldCoinPriceChangeHistoryService) {
        this.goldCoinPriceChangeHistoryService = goldCoinPriceChangeHistoryService;
    }

    // 1.写入金币促销历史价格表并发消息
    public void saveToGoldCoinPriceChangeHistoryAndSendMsg(List<GoldCoinPriceChangeMsg> goldCoinPriceChangeMsgs,
                                                           final Set<Long> sendedMsgIds) {
        List<Long> ruleIds = new ArrayList<Long>(goldCoinPriceChangeMsgs.size());
        List<Long> pmIds = new ArrayList<Long>(goldCoinPriceChangeMsgs.size());
        Set<Long> Ids = new HashSet<Long>(goldCoinPriceChangeMsgs.size(), 1F);
        for(GoldCoinPriceChangeMsg goldCoinPriceChangeMsg : goldCoinPriceChangeMsgs) {
            Long ruleId = goldCoinPriceChangeMsg.getRuleId();
            Long pmInfoId = goldCoinPriceChangeMsg.getPmInfoId();
            ruleIds.add(ruleId);
            pmIds.add(pmInfoId);
            Ids.add(goldCoinPriceChangeMsg.getId());
        }
        List<GoldCoinPromRuleVo> resultList = null;
        try {
            resultList = busyPriceFacadeService.getGoldCoinPromRuleVoListByIds(ruleIds);
        } catch (Exception e) {
            logger.error("####远程查询金币促销失败！#####" + e.getMessage());
        }
        if(CollectionUtils.isEmpty(resultList)) {
            logger.info("####根据ruleId没有查到金币促销#####" + ruleIds);
            return;
        }
        List<HistoryGoldCoinPriceChangeMsgVo> historyGoldCoinPriceChangeMsgVos = buildListHistoryGoldCoinPriceChangeMsgVo(resultList);
         goldCoinPriceChangeHistoryService.batchInsertGoldCoinHistoryPriceChangeMsg(historyGoldCoinPriceChangeMsgVos);
        logger.info("####成功插入数据到金币促销历史价格表。#####");
        sendPriceChangeMsg(pmIds);
        sendedMsgIds.addAll(Ids);
    }

    private List<HistoryGoldCoinPriceChangeMsgVo> buildListHistoryGoldCoinPriceChangeMsgVo(List<GoldCoinPromRuleVo> goldCoinPromRuleVos) {
        List<HistoryGoldCoinPriceChangeMsgVo> result = new ArrayList<HistoryGoldCoinPriceChangeMsgVo>(
            goldCoinPromRuleVos.size());
        for(GoldCoinPromRuleVo goldCoinPromRuleVo : goldCoinPromRuleVos) {
            HistoryGoldCoinPriceChangeMsgVo historyGoldCoinPriceChangeMsgVo = new HistoryGoldCoinPriceChangeMsgVo();
            historyGoldCoinPriceChangeMsgVo.setPmInfoId(goldCoinPromRuleVo.getPmInfoId());
            historyGoldCoinPriceChangeMsgVo.setProductId(goldCoinPromRuleVo.getProductId());
            historyGoldCoinPriceChangeMsgVo.setMerchantId(goldCoinPromRuleVo.getMerchantId());
            historyGoldCoinPriceChangeMsgVo.setChannelId(goldCoinPromRuleVo.getChannelId());
            historyGoldCoinPriceChangeMsgVo.setRuleId(goldCoinPromRuleVo.getId());
            historyGoldCoinPriceChangeMsgVo.setPromTotalPrice(goldCoinPromRuleVo.getPromTotalPrice());
            historyGoldCoinPriceChangeMsgVo.setPromCashPrice(goldCoinPromRuleVo.getPromCashPrice());
            historyGoldCoinPriceChangeMsgVo.setGoldCoinPrice(goldCoinPromRuleVo.getGoldCoinPrice());
            historyGoldCoinPriceChangeMsgVo.setGoldCoinNum(goldCoinPromRuleVo.getGoldCoinNum());
            historyGoldCoinPriceChangeMsgVo.setIsPromotion(goldCoinPromRuleVo.getIsPromotion());
            historyGoldCoinPriceChangeMsgVo.setPromStartDate(goldCoinPromRuleVo.getPromStartDate());
            historyGoldCoinPriceChangeMsgVo.setPromEndDate(goldCoinPromRuleVo.getPromEndDate());
            historyGoldCoinPriceChangeMsgVo.setPromUpdateTime(goldCoinPromRuleVo.getUpdateTime());
            historyGoldCoinPriceChangeMsgVo.setCreateTime(new Date());
            historyGoldCoinPriceChangeMsgVo.setBackOperatorId(goldCoinPromRuleVo.getBackOperatorId());
            historyGoldCoinPriceChangeMsgVo.setShardingIndex((int) (historyGoldCoinPriceChangeMsgVo.getPmInfoId()%16));
            historyGoldCoinPriceChangeMsgVo.setIsDeal(0);
            result.add(historyGoldCoinPriceChangeMsgVo);
        }
        return result;
    }

    private void sendPriceChangeMsg(List<Long> pmIds) {
        try {
            FrontDatabusClient.publishGoldCoinPriceChange(pmIds);
        } catch (Exception e) {
            logger.error("---##---发送价格变化的pmId发生异常--##---" + e.getMessage(), e);
        }
    }
}
