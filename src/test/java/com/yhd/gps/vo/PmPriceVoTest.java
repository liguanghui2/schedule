package com.yhd.gps.vo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.vo.PmPriceVo;

/**
 * @author:liguanghui1
 * @date ：2017-4-10 下午04:49:15
 */

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class PmPriceVoTest extends AbstractDependencyInjectionSpringContextTests {

    @Test
    public void testVo() {
        PmPriceVo vo = new PmPriceVo();
        vo.setId(1234L);
        vo.setPmId(1234L);
        assertTrue(vo.getId().compareTo(1234L) == 0);
        assertTrue(vo.getPmId().compareTo(1234L) == 0);

    }
}
