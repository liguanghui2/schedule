package com.yhd.gps.msg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.msg.PromotionPriceChangeMsgSender;
import com.yhd.gps.pricechange.vo.LPPriceMessageVo;
import com.yhd.gps.pricechange.vo.PromotionPriceChangeMsg;
import com.yhd.gps.schedule.common.ScheduleDateUtils;

/**
 * LP价格变化消息发送器
 * @Author xuyong
 * @CreateTime 2015-6-16 下午08:26:28
 */
public class PromotionPriceChangeMsgSenderTest extends BaseSpringTest {

    @Autowired
    private PromotionPriceChangeMsgSender promotionPriceChangeMsgSender;

    @Test
    public void send() {
        List<PromotionPriceChangeMsg> priceChangeMsgs = new ArrayList<PromotionPriceChangeMsg>();
        for(int i = 0; i < 10; i++) {
            PromotionPriceChangeMsg data = new PromotionPriceChangeMsg();
            data.setActualSendIp("127.0.0.1");
            data.setActualSendTime(new Date());
            data.setCreateIp("127.0.0.1");
            data.setCreateTime(ScheduleDateUtils.addMinutes(new Date(), -60));
            data.setMsgContent("test " + i);
            data.setPromotionId(1000l + i);
            data.setMsgStatus(0);
            data.setMsgTaskIp("192.168.1.12");
            data.setMsgTaskTime(ScheduleDateUtils.addMinutes(new Date(), -30));
            data.setPmInfoId(666l + i);
            data.setShardingIndex((int) (data.getPmInfoId() % 16));
            data.setUpdateIp("127.0.0.1");
            data.setUpdateTime(new Date());
            priceChangeMsgs.add(data);
        }

        Set<Long> sendedMsgIds = new HashSet<Long>();

        promotionPriceChangeMsgSender.send(priceChangeMsgs, sendedMsgIds);
    }

    @Test
    public void createJumperMessageLog() {
        LPPriceMessageVo message = new LPPriceMessageVo();
        message.setPmInfoId(PM_INFO_ID_0);
        message.setPrice(new BigDecimal(1.2));
        message.setProductId(11L);
        String topic = "test";
        String messageType = "history";
        String content = "content";
        Date sendTime = new Date();
        promotionPriceChangeMsgSender.createJumperMessageLog(message, topic, messageType, content, sendTime);
    }
}