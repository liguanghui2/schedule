package com.yhd.gps.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.common.ScheduleContext;

/**
 * 计算上下文
 * 
 * @author Hikin Yao
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ScheduleContextTest extends AbstractDependencyInjectionSpringContextTests {

    @Test
    public void test() {
        ScheduleContext.reset();
        ScheduleContext.setValue("test", 1L);
        Long result = (Long) ScheduleContext.getValue("test");
        assertTrue(result == 1L);
        ScheduleContext.removeValue("test");
        result = (Long) ScheduleContext.getValue("test");
        assertNull(result);
        ScheduleContext.reset();
    }
}
