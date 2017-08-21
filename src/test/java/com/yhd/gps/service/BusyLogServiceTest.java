package com.yhd.gps.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.service.BusyLogService;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.vo.JumperMessageLog;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

public class BusyLogServiceTest extends BaseSpringTest {

    @Autowired
    private BusyLogService busyLogService;

    @Test
    public void batchInsertMessageLog() {
        List<JumperMessageLog> messageLogs = new ArrayList<JumperMessageLog>();
        for(int i = 0; i < 2; i++) {
            JumperMessageLog vo = new JumperMessageLog();
            vo.setContent("test" + i);
            vo.setCreateTime(new Date());
            vo.setMessageType("price-change");
            vo.setPmInfoId(PM_INFO_ID_0);
            vo.setSendTime(new Date());
            vo.setServerIp("127.0.0.1");
            vo.setTopic("test topic");
            messageLogs.add(vo);
        }
        busyLogService.batchInsertMessageLog(messageLogs);
    }

    @Test
    public void batchInsertProductPromRuleLogs() {
        List<BSProductPromRuleVo> ruleList = null;
        String opType = "1";

        busyLogService.batchInsertProductPromRuleLogs(ruleList, opType);
    }

    @Test
    public void batchInsertGpsPromotionLogs() {
        List<GPSPromotionVo> gpsPromotionVos = null;
        String opType = "2";
        String memo = "memo";
        busyLogService.batchInsertGpsPromotionLogs(gpsPromotionVos, opType, memo);
    }

}
