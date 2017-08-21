package com.yhd.gps.busyservice.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.BusyGpsPromotionDao;
import com.yhd.gps.pricechange.vo.LPPriceMessageVo;
import com.yhd.gps.pricechange.vo.PromotionPriceChangeMsg;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.vo.JumperMessageLog;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.busy.vo.builder.HistoryPriceChangeMsgVoBuilder;
import com.yihaodian.front.busystock.client.BusyPoolUtil;
import com.yihaodian.front.databus.DatabusConstants;
import com.yihaodian.front.databus.client.FrontDatabusClient;

/**
 * LP价格变化消息发送器
 * @Author xuyong
 * @CreateTime 2015-6-16 下午08:26:28
 */
public class PromotionPriceChangeMsgSender extends MsgSender<LPPriceMessageVo> {

    private BusyGpsPromotionDao busyGpsPromotionDao;

    private static final Integer MSG_SIZE = 50;

    public void setBusyGpsPromotionDao(BusyGpsPromotionDao busyGpsPromotionDao) {
        this.busyGpsPromotionDao = busyGpsPromotionDao;
    }

    /**
     * 发送消息
     * @param promotionPriceChangeMsgs
     * @param sendedMsgIds
     * @return
     */
    public Integer send(List<PromotionPriceChangeMsg> promotionPriceChangeMsgs, final Set<Long> sendedMsgIds) {
        if(CollectionUtils.isEmpty(promotionPriceChangeMsgs)) {
            return 0;
        }
        List<Long> gpsPromotionIds = new ArrayList<Long>(promotionPriceChangeMsgs.size());
        for(PromotionPriceChangeMsg promotionPriceChangeMsg : promotionPriceChangeMsgs) {
            gpsPromotionIds.add(promotionPriceChangeMsg.getPromotionId());
            sendedMsgIds.add(promotionPriceChangeMsg.getId());
        }

        List<GPSPromotionVo> promotionVos = busyGpsPromotionDao.batchGetGpsPromotionByIdsFromDb(gpsPromotionIds);
        if(CollectionUtils.isEmpty(promotionVos)) {
            return 0;
        }

        sendPromotionPriceMessage(promotionVos);

        return promotionPriceChangeMsgs.size();
    }

    private void sendPromotionPriceMessage(List<GPSPromotionVo> promotionVos) {
        List<LPPriceMessageVo> promotionPriceMessageVos = new ArrayList<LPPriceMessageVo>(promotionVos.size());
        Set<String> exsits = new HashSet<String>();
        // 构造LP价格变化消息
        for(GPSPromotionVo promotionVo : promotionVos) {
            Long pmInfoId = promotionVo.getPmId();
            Long promotionId = promotionVo.getPromotionId();
            Date endTime = promotionVo.getEndTime();
            if(null == pmInfoId || null == promotionId || null == endTime) {
                continue;
            }
            String identity = pmInfoId + "-" + promotionId + "-" + endTime.getTime();
            if(exsits.contains(identity)) {
                continue;
            }
            exsits.add(identity);
            LPPriceMessageVo lpPriceMessageVo = new LPPriceMessageVo(promotionVo);
            promotionPriceMessageVos.add(lpPriceMessageVo);
        }

        List<List<LPPriceMessageVo>> lists = GPSUtils.split(promotionPriceMessageVos, MSG_SIZE);
        final String messageType = "";
        for(final List<LPPriceMessageVo> list : lists) {
            FrontDatabusClient.publishLPPriceChangeJson(list, messageType);

            // 记录历史价格
            recordHistoryPriceChangeMsgs(list);

            saveJumperMessageLog(DatabusConstants.GPS_LANDING_HISTORY_PRICE, list, messageType);
        }
    }

    @Override
    public JumperMessageLog createJumperMessageLog(LPPriceMessageVo message, String topic, String messageType,
                                                   String content, Date sendTime) {
        return new JumperMessageLog(message.getPmInfoId(), BusyPoolUtil.getPoolIP(), topic, messageType, content,
            sendTime);
    }

    @Override
    public HistoryPriceChangeMsgVo buildHistoryPriceChangeMsg(LPPriceMessageVo message) {
        return HistoryPriceChangeMsgVoBuilder.buildHistoryPriceChangeMsg(message);
    }

}