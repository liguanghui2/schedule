package com.yhd.gps.common;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.vo.DateIntervalVo;
import com.yhd.gps.utils.DateUtil;

/**
 * @author sunfei
 * @CreateTime 2017-7-4 下午02:22:44
 */
public class ScheduleDateUtilsTest {
    @Test
    public void testFormat() {
        ScheduleDateUtils.format(new Date(), "yyyy-MM-dd");
        ScheduleDateUtils.format(null);
        ScheduleDateUtils.format(new Date());
    }

    @Test
    public void testFormatDay() {
        ScheduleDateUtils.formatDay(null);
        ScheduleDateUtils.formatDay(new Date());
    }

    @Test
    public void testParseDate() {
        ScheduleDateUtils.parseDate(new Date());
        ScheduleDateUtils.parseDate("2017-09-30 12:00:00");
        ScheduleDateUtils.parseDate("2017-09-30 12:00:00", "yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testAddDays() {
        ScheduleDateUtils.addDays(new Date(), 1);
    }

    @Test
    public void testAddMinutes() {
        ScheduleDateUtils.addMinutes(new Date(), 1);
    }

    @Test
    public void testAddMillisecond() {
        ScheduleDateUtils.addMillisecond(new Date(), 1);
    }

    @Test
    public void testGetDateAddMinutesStr() {
        ScheduleDateUtils.getDateAddMinutesStr(new Date(), 1);
    }

    @Test
    public void testGetDateAddDaysStr() {
        ScheduleDateUtils.getDateAddDaysStr(new Date(), 1);
    }

    @Test
    public void testGetBeforeXMonthDate() {
        ScheduleDateUtils.getBefore1MonthDate(new Date());
        ScheduleDateUtils.getBeforeXMonthDate(new Date(), 2);
    }

    @Test
    public void testGetDateRange() {
        ScheduleDateUtils.getYesterdayDateRange(new Date());
        ScheduleDateUtils.getLastWeekDateRange(new Date());
        ScheduleDateUtils.getLastMonthDateRange(new Date());
        ScheduleDateUtils.getLastMonthDateRange(new Date());
        ScheduleDateUtils.getXDateRange(new Date(), 1);
        ScheduleDateUtils.getYear(new Date());
        ScheduleDateUtils.getMonth(new Date());
        ScheduleDateUtils.getWeekNum(new Date());
    }

    @Test
    public void testGetLastWeekDateRange2() {
        // case1：当前日期为周天
        Date date = DateUtil.parseDate("2017-07-09 10:57:00");
        DateIntervalVo vo = ScheduleDateUtils.getLastWeekDateRange2(date);
        // case2:当前日期为周一
        date = DateUtils.addDays(date, 1);
        vo = ScheduleDateUtils.getLastWeekDateRange2(date);
        // 当前日期为周二
        date = DateUtils.addDays(date, 1);
        vo = ScheduleDateUtils.getLastWeekDateRange2(date);
        // 当前日期为周三
        date = DateUtils.addDays(date, 1);
        vo = ScheduleDateUtils.getLastWeekDateRange2(date);
        // 当前日期为周四
        date = DateUtils.addDays(date, 1);
        vo = ScheduleDateUtils.getLastWeekDateRange2(date);
        // 当前日期为周五
        date = DateUtils.addDays(date, 1);
        vo = ScheduleDateUtils.getLastWeekDateRange2(date);
        // 当前日期为周六
        date = DateUtils.addDays(date, 1);
        vo = ScheduleDateUtils.getLastWeekDateRange2(date);
    }

}
