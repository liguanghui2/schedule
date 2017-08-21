package com.yhd.gps.exception;

import org.junit.Assert;
import org.junit.Test;

import com.yhd.gps.schedule.exception.GpsScheduleBusinessException;

/** 
 * @author  sunfei
 * @CreateTime 2017-7-4 下午02:12:07 
 */
public class GpsScheduleBusinessExceptionTest {
    @Test
    public void test(){
        GpsScheduleBusinessException exception = new GpsScheduleBusinessException();
        GpsScheduleBusinessException exception2 = new GpsScheduleBusinessException("异常");
        GpsScheduleBusinessException exception3 = new GpsScheduleBusinessException("异常", new Throwable());
        Assert.assertNotNull(exception);
        Assert.assertNotNull(exception2);
        Assert.assertNotNull(exception3);
    }

}
