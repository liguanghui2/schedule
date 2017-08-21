package com.yhd.gps.vo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.vo.LPCompareResultVo;

/**
 * landingPage比对结果
 * @Author lipengcheng
 * @CreateTime 2016-7-1 上午10:02:40
 */
@SuppressWarnings("deprecation")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class LPCompareResultVoTest extends AbstractDependencyInjectionSpringContextTests {

    @Test
    public void testVo() {
        LPCompareResultVo resultVo = new LPCompareResultVo();
        resultVo.setPromotionId(1L);
        System.out.println(resultVo.toString());
    }
}
