package com.yhd.gps.vo;

import java.util.Date;

import org.junit.Test;

import com.yhd.gps.schedule.vo.DateIntervalVo;

/** 
 * @author  sunfei
 * @CreateTime 2017-7-4 下午01:33:36 
 */
public class DateIntervalVoTest {
    @Test
    public void test(){
        DateIntervalVo dateIntervalVo = new DateIntervalVo();
        dateIntervalVo.setStartDate(new Date());
        dateIntervalVo.setEndDate(new Date());
        dateIntervalVo.getStartDate();
        dateIntervalVo.getEndDate();
        dateIntervalVo.toString();
    }
}
