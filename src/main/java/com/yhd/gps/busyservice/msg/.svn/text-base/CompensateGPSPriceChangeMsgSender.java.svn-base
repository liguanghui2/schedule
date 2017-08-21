package com.yhd.gps.busyservice.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.vo.JumperMessageLog;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.busy.vo.BusyPmPriceVo;
import com.yihaodian.busy.vo.BusyPriceChangeMsgVo;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.busy.vo.PriceFatMessageVo;
import com.yihaodian.busy.vo.builder.HistoryPriceChangeMsgVoBuilder;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.client.facade.BusyPriceFacadeService;

/**
 * 
 * ---- 补偿价格变动表数据已处理、历史价格表无数据记录 ------
 * @Author  lipengfei
 * @CreateTime  2016-8-11 下午05:36:28
 */
public class CompensateGPSPriceChangeMsgSender extends MsgSender<PriceFatMessageVo> {
    private static final Log LOGGER = LogFactory.getLog(CompensateGPSPriceChangeMsgSender.class);
    public static final Integer MSG_SIZE = 50;
    private static BusyPriceFacadeService busyPriceFacadeService = BusyStockClientUtil.getBusyPriceFacadeService();
    private BusyPmInfoDao busyPmInfoDao;

    public void setBusyPmInfoDao(BusyPmInfoDao busyPmInfoDao) {
        this.busyPmInfoDao = busyPmInfoDao;
    }

    public Integer send(List<BusyPriceChangeMsgVo> priceChangeMsgs, final Set<Long> priceChangeMsgIds) {
        if(CollectionUtils.isEmpty(priceChangeMsgs)) {
            return 0;
        }
        try {
            Set<Long> pmInfoIdSet = new HashSet<Long>(priceChangeMsgs.size(), 1F);
            Map<Long, BusyPriceChangeMsgVo> priceChangeMsgMap = new HashMap<Long, BusyPriceChangeMsgVo>(
                priceChangeMsgs.size(), 1F);
            for(BusyPriceChangeMsgVo priceChangeMsg : priceChangeMsgs) {
                priceChangeMsgIds.add(priceChangeMsg.getId());

                pmInfoIdSet.add(priceChangeMsg.getPmInfoId());
                priceChangeMsgMap.put(priceChangeMsg.getPmInfoId(), priceChangeMsg);
            }
            // 求出组合商品主品pmInfoId
            List<Long> mainPmInfoIds = busyPmInfoDao.batchGetMainCombinePmIdBySubPmIds(new ArrayList<Long>(pmInfoIdSet));
            if(CollectionUtils.isNotEmpty(mainPmInfoIds)) {
                // 过滤pmId为null的情况
                for(Long mainPmInfoId : mainPmInfoIds) {
                    if(mainPmInfoId != null) {
                        pmInfoIdSet.add(mainPmInfoId);
                    }
                }
            }
            recordHistoryPriceChange(new ArrayList<Long>(pmInfoIdSet), priceChangeMsgMap);
            return priceChangeMsgs.size();
        } catch (Exception e) {
            priceChangeMsgIds.clear();
            LOGGER.error("发送价格变化消息发生异常, error : " + e.getMessage(), e);
        }
        return 0;
    }

    private void recordHistoryPriceChange(List<Long> pmIds, Map<Long, BusyPriceChangeMsgVo> priceChangeMsgMap) {
        try {
            // 查询GPS获取价格
            List<BusyPmPriceVo> pmPriceVos = busyPriceFacadeService.batchGetPmPriceVoListWithCache4Schedule(pmIds);
            if(CollectionUtils.isEmpty(pmPriceVos)) {
                return;
            }
            List<PriceFatMessageVo> priceFatMessages = new ArrayList<PriceFatMessageVo>(pmPriceVos.size());
            for(BusyPmPriceVo pmPriceVo : pmPriceVos) {
                BusyPriceChangeMsgVo priceChangeVo = priceChangeMsgMap.get(pmPriceVo.getPmId());
                PriceFatMessageVo priceFatMessage = buildPriceFatMessageVo(pmPriceVo, priceChangeVo);
                if(null != priceFatMessage) {
                    priceFatMessages.add(priceFatMessage);
                }
            }
            if(CollectionUtils.isNotEmpty(priceFatMessages)) {
                List<List<PriceFatMessageVo>> lists = GPSUtils.split(priceFatMessages, MSG_SIZE);
                for(List<PriceFatMessageVo> list : lists) {
                    // 记录历史价格
                    recordHistoryPriceChangeMsgs(list);
                }
            }
        } catch (Exception e) {
            LOGGER.error("###记录历史价格表出错###" + e.getMessage(), e);
        }
    }


    private static final PriceFatMessageVo buildPriceFatMessageVo(BusyPmPriceVo pmPriceVo, BusyPriceChangeMsgVo priceChangeVo) {
        Long priceOrRuleId = priceChangeVo == null ? pmPriceVo.getId() : priceChangeVo.getMsgPriceId();
        PriceFatMessageVo result = new PriceFatMessageVo();
        result.setPmInfoId(pmPriceVo.getPmId());
        result.setPrice(pmPriceVo.getCurrentPrice());
        result.setPriceId(priceOrRuleId);
        result.setMerchantId(pmPriceVo.getMerchantId());
        result.setProductId(pmPriceVo.getProductId());
        if(null != pmPriceVo.getCurrentRuleVo()) {
            result.setChannelId(pmPriceVo.getChannelId());
            result.setPromRuleId(pmPriceVo.getCurrentRuleVo().getId());
            result.setPromoteType(pmPriceVo.getCurrentRuleVo().getPromoteType());
            result.setPromStartTime(pmPriceVo.getCurrentRuleVo().getPromStartTime());
            result.setPromEndTime(pmPriceVo.getCurrentRuleVo().getPromEndTime());
            result.setPromName(pmPriceVo.getCurrentRuleVo().getPromName());
            result.setBackendOperatorId(pmPriceVo.getCurrentRuleVo().getBackendOperatorId());
            result.setUpdateTime(pmPriceVo.getCurrentRuleVo().getUpdateTime());
            result.setRuleType(pmPriceVo.getCurrentRuleVo().getRuleType());
            result.setActivityId(pmPriceVo.getCurrentRuleVo().getActivityId());
        }
        return result;
    }

    @Override
    public JumperMessageLog createJumperMessageLog(PriceFatMessageVo message, String topic, String messageType,
                                                   String content, Date sendTime) {
        return new JumperMessageLog(message.getPmInfoId(), PoolUtils.getPoolIP(), topic, messageType, content, sendTime);
    }

    @Override
    public HistoryPriceChangeMsgVo buildHistoryPriceChangeMsg(PriceFatMessageVo message) {
        return HistoryPriceChangeMsgVoBuilder.buildHistoryPriceChangeMsg(message);
    }

}