package com.yhd.gps.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.BusyLogUtils;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BusyLogUtilsTest  extends AbstractDependencyInjectionSpringContextTests {
    
    @Test
    public void testVo(){
        BusyLogUtils.getBaseLog("1");
        
        GPSPromotionVo gpsPromtionVo = new GPSPromotionVo();
        BusyLogUtils.buildGPSPromotionLogVo(gpsPromtionVo, "1", "memo");
        
        BSProductPromRuleVo productPromRuleVo = new BSProductPromRuleVo();
        BusyLogUtils.buildProductPromRuleLogVo(productPromRuleVo, "1");
        
    }
}
