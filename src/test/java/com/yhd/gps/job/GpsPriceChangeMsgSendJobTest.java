package com.yhd.gps.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.dao.BusyPriceChangeMsgDao;
import com.yhd.gps.busyservice.dao.PromotionPriceChangeMsgDao;
import com.yhd.gps.pricechange.vo.PromotionPriceChangeMsg;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.job.GpsPriceChangeMsgSendJob;
import com.yihaodian.busy.vo.BusyPriceChangeMsgVo;

/**
 * 发送价格变化消息和胖消息定时任务
 * @Author liuxiangrong
 * @CreateTime 2016-4-13 下午07:07:11
 */
public class GpsPriceChangeMsgSendJobTest extends BaseSpringTest {

    @Autowired
    private GpsPriceChangeMsgSendJob gpsPriceChangeMsgSendJob;

    @Autowired
    private BusyPriceChangeMsgDao busyPriceChangeMsgDao;

    @Autowired
    private PromotionPriceChangeMsgDao promotionPriceChangeMsgDao;

    @Before
    public void onSetUp() throws Exception {
        List<BusyPriceChangeMsgVo> list = new ArrayList<BusyPriceChangeMsgVo>();
        for(int i = 0; i < 20; i++) {
            if(i == 2) {
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
                list.add(data);
            }
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
            list.add(data);
        }

        busyPriceChangeMsgDao.insertPriceChangeMsgVos(list);

        List<PromotionPriceChangeMsg> pList = new ArrayList<PromotionPriceChangeMsg>();
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
            pList.add(data);
        }

        promotionPriceChangeMsgDao.insertPromotionPriceChangeMsg(pList);
    }

    @Test
    public void test() {
        gpsPriceChangeMsgSendJob.execute();
    }
}