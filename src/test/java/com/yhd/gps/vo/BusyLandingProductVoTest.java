package com.yhd.gps.vo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.vo.BusyLandingProductVo;

/**
 * 
 * 促销的lp信息
 * @Author lipengcheng
 * @CreateTime 2016-7-1 下午04:59:34
 */
@SuppressWarnings("deprecation")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BusyLandingProductVoTest extends AbstractDependencyInjectionSpringContextTests {

    @Test
    public void testVo() {
        BusyLandingProductVo landingProductVo = new BusyLandingProductVo();
        landingProductVo.setPromotionId(1L);
        System.out.println(landingProductVo.toString());
    }
}
