package com.yhd.gps.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.service.BusyPromRuleCleanService;

public class BusyPromRuleCleanServiceTest extends BaseSpringTest {

    @Autowired
    private BusyPromRuleCleanService busyPromRuleCleanService;

    @Test
    public void cleanOutDatePromRule() {
        Date outDate = new Date();
        Date deleteDate = new Date();
        Integer pageSize = 1;
        busyPromRuleCleanService.cleanOutDatePromRule(outDate, deleteDate, pageSize);
    }

    @Test
    public void cleanOutDateGpsPromotion() {
        Date outDate = new Date();
        Date deleteDate = new Date();
        Integer pageSize = 1;
        busyPromRuleCleanService.cleanOutDateGpsPromotion(outDate, deleteDate, pageSize);
    }

}
