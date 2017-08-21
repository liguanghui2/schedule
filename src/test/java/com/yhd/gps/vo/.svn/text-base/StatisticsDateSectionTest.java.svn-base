package com.yhd.gps.vo;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.vo.StatisticsDateSection;

/**
 * 区间价格对象
 * @Author liuxiangrong
 * @CreateTime 2016-2-2 下午02:54:37
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class StatisticsDateSectionTest  extends AbstractDependencyInjectionSpringContextTests {
    
    @Test
    public void testVo(){
        StatisticsDateSection vo = new StatisticsDateSection(new Date(),new Date());
        vo.setEndDate(new Date());
        vo.setStartDate(new Date());
        
        System.out.println(vo.getEndDate() + "\t" + vo.getStartDate());
    }

}