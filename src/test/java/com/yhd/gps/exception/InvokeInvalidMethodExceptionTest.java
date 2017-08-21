package com.yhd.gps.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.schedule.sharding.exception.InvokeInvalidMethodException;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-6 下午06:42:33
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class InvokeInvalidMethodExceptionTest extends AbstractDependencyInjectionSpringContextTests {

    @Test
    public void test() {
        try {
            throw new InvokeInvalidMethodException("test");
        } catch (Exception e) {

        }
    }

}