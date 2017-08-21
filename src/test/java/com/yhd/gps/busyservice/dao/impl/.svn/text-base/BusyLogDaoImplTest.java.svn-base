package com.yhd.gps.busyservice.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.spring.annotation.SpringBeanByName;

import com.yhd.gps.BaseSpringWithUnitilsTest;
import com.yhd.gps.schedule.vo.BusyProductPromRuleLogVo;

/**
 * @author:liguanghui1
 * @date ：2016-8-30 下午03:44:56
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BusyLogDaoImplTest extends BaseSpringWithUnitilsTest {

    @SpringBeanByName
    private BusyLogDaoImpl busyLogDao;

    @Test
    public void batchInsertProductPromRuleLogsTest() {
        BusyProductPromRuleLogVo vo1 = new BusyProductPromRuleLogVo();
        vo1.setActivityId(12L);
        vo1.setClientIP("172.0.0.1");
        BusyProductPromRuleLogVo vo2 = new BusyProductPromRuleLogVo();
        vo2.setActivityId(13L);
        vo2.setClientIP("172.0.0.2");
        List<BusyProductPromRuleLogVo> logs = new ArrayList<BusyProductPromRuleLogVo>();
        logs.add(vo1);
        logs.add(vo2);
        int result = busyLogDao.batchInsertProductPromRuleLogs(logs);
        assertTrue(result == 2);
    }
}
