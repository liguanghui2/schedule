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
import com.yhd.gps.busyservice.msg.GPSPriceChangeMsgSender;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yihaodian.busy.vo.BusyPriceChangeMsgVo;
import com.yihaodian.busy.vo.PriceFatMessageVo;

/**
 * GPS价格变化消息发送器
 * @Author xuyong
 * @CreateTime 2015-7-21 上午10:55:52
 */
public class GPSPriceChangeMsgSenderTest extends BaseSpringTest {

    @Autowired
    private GPSPriceChangeMsgSender gpsPriceChangeMsgSender;

    @Test
    public void send() {
        List<BusyPriceChangeMsgVo> priceChangeMsgs = new ArrayList<BusyPriceChangeMsgVo>();
        for(int i = 0; i < 20; i++) {
            BusyPriceChangeMsgVo data = new BusyPriceChangeMsgVo();
            data.setActualSendIp("127.0.0.1");
            data.setActualSendTime(new Date());
            data.setCreateIp("127.0.0.1");
            data.setCreateTime(ScheduleDateUtils.addMinutes(new Date(), -60));
            data.setMsgContent("test " + i);
            data.setMsgPriceId(1000l + i);
            data.setMsgStatus(0);
            data.setMsgTaskIp("192.168.1.12");
            data.setMsgTaskTime(ScheduleDateUtils.addMinutes(new Date(), -30));
            data.setMsgType(1);
            data.setPmInfoId(888l + i);
            data.setShardingIndex((int) (Math.random() * 4));
            data.setUpdateIp("127.0.0.1");
            data.setUpdateTime(new Date());
            priceChangeMsgs.add(data);
        }
        Set<Long> sendedMsgIds = new HashSet<Long>();

        gpsPriceChangeMsgSender.send(priceChangeMsgs, sendedMsgIds);

        // 支持区域价
        priceChangeMsgs = new ArrayList<BusyPriceChangeMsgVo>();
        BusyPriceChangeMsgVo data = new BusyPriceChangeMsgVo();
        data.setActualSendIp("127.0.0.1");
        data.setActualSendTime(new Date());
        data.setCreateIp("127.0.0.1");
        data.setCreateTime(ScheduleDateUtils.addMinutes(new Date(), -60));
        data.setMsgContent("test");
        data.setMsgPriceId(123321226735L);
        data.setMsgStatus(0);
        data.setMsgTaskIp("192.168.1.12");
        data.setMsgTaskTime(ScheduleDateUtils.addMinutes(new Date(), -30));
        data.setMsgType(2);
        data.setPmInfoId(68000L);
        data.setShardingIndex((int) (Math.random() * 4));
        data.setUpdateIp("127.0.0.1");
        data.setUpdateTime(new Date());
        priceChangeMsgs.add(data);
        gpsPriceChangeMsgSender.send(priceChangeMsgs, sendedMsgIds);
    }

    @Test
    public void createJumperMessageLog() {
        PriceFatMessageVo message = new PriceFatMessageVo();
        message.setPmInfoId(PM_INFO_ID_0);
        message.setChannelId(1L);
        message.setDate(new Date());
        message.setPrice(new BigDecimal(1.2));
        message.setProductId(11L);
        message.setPriceId(2L);
        String topic = "test";
        String messageType = "history";
        String content = "content";
        Date sendTime = new Date();
        gpsPriceChangeMsgSender.createJumperMessageLog(message, topic, messageType, content, sendTime);
    }

    @Test
    public void buildJumperMessageLog() {
        List<PriceFatMessageVo> messages = new ArrayList<PriceFatMessageVo>();
        PriceFatMessageVo message = new PriceFatMessageVo();
        message.setPmInfoId(PM_INFO_ID_0);
        message.setChannelId(1L);
        message.setDate(new Date());
        message.setPrice(new BigDecimal(1.2));
        message.setProductId(11L);
        message.setPriceId(2L);
        messages.add(message);
        String topic = "test";
        String messageType = "history";
        Date sendTime = new Date();
        gpsPriceChangeMsgSender.buildJumperMessageLog(messages, topic, messageType, sendTime);
    }

    @Test
    public void saveJumperMessageLog() {
        List<PriceFatMessageVo> messages = new ArrayList<PriceFatMessageVo>();
        PriceFatMessageVo message = new PriceFatMessageVo();
        message.setPmInfoId(PM_INFO_ID_0);
        message.setChannelId(1L);
        message.setDate(new Date());
        message.setPrice(new BigDecimal(1.2));
        message.setProductId(11L);
        message.setPriceId(2L);
        messages.add(message);
        String topic = "test";
        String messageType = "history";
        gpsPriceChangeMsgSender.saveJumperMessageLog(topic, messages, messageType);
    }

}