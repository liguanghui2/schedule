package com.yhd.gps.vo;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.vo.DistintcPriceChangCache;

/**
 * ---- 请加注释 ------
 * @Author zuozhen
 * @CreateTime 2015-6-29 下午04:46:03
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class DistintcPriceChangCacheTest   extends AbstractDependencyInjectionSpringContextTests {
    
    @Test
    public void testVo(){
        DistintcPriceChangCache vo = new DistintcPriceChangCache();
        vo.setPrice(new BigDecimal(1));
        vo.setPromRuleId(1L);
        System.out.println(vo.getPrice() + "\t" + vo.getPromRuleId());
    }

}