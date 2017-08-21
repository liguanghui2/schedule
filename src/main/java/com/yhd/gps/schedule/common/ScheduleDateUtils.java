package com.yhd.gps.schedule.common;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.Assert;

import com.yhd.gps.schedule.vo.DateIntervalVo;
import com.yihaodian.busy.common.BusyConstants;

/**
 * 日期工具类
 * @Author liuxiangrong
 * @CreateTime 2016-1-29 下午03:07:56
 */
public class ScheduleDateUtils {

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DAY_FORMAT_DATE_PATTERN = "yyyy-MM-dd 00:00:00";

    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static String format(Date date) {
        if(null == date) {
            return null;
        }
        return DateFormatUtils.format(date, DEFAULT_DATE_PATTERN);
    }

    public static String formatDay(Date date) {
        if(null == date) {
            return null;
        }
        return DateFormatUtils.format(date, DAY_FORMAT_DATE_PATTERN);
    }

    /**
     * 将当前日期格式化成不包含 时 分 秒的日期
     * 
     * @param date
     * @return
     */
    public static Date parseDate(Date date) {
        Assert.notNull(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTime().getTime());
    }

    /**
     * 将一个字符串转换成默认格式的日期
     * 
     * @param strDateTime 需要转换的日期字符串
     * @return 当转换成功返回转换成功后的日期，否则返回 null
     */
    public static Date parseDate(String strDateTime) {
        try {
            return DateUtils.parseDate(strDateTime, new String[]{DEFAULT_DATE_PATTERN });
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将一个字符串转换成指定格式的日期
     * 
     * @param strDateTime 需要转换的日期字符串
     * @param pattern 转换的格式
     * @return 当转换成功返回转换成功后的日期，否则返回 null
     */
    public static Date parseDate(String strDateTime, String pattern) {
        try {
            return DateUtils.parseDate(strDateTime, new String[]{pattern });
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 给指定日期加几天
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Assert.notNull(date);
        return DateUtils.addDays(date, days);
    }

    /**
     * 给指定日期加几分钟
     * 
     * @param date 指定的日期
     * @param numMins 需要往后加的分钟数
     * @return 加好后的日期
     */
    public static Date addMinutes(Date date, int numMins) {
        Assert.notNull(date);
        return DateUtils.addMinutes(date, numMins);
    }

    /**
     * 给指定日期加几毫秒
     * 
     * @param date 指定的日期
     * @param ms 需要往后加的毫秒数
     * @return 加好后的日期
     */
    public static Date addMillisecond(Date date, int ms) {
        Assert.notNull(date);
        return DateUtils.addMilliseconds(date, ms);
    }

    public static String getDateAddMinutesStr(Date date, int numMins) {
        Assert.notNull(date);
        return format(DateUtils.addMinutes(date, numMins));
    }

    /**
     * 获取一个月前的日期
     * @param date
     * @return
     */
    public static Date getBefore1MonthDate(Date date) {
        return getBeforeXMonthDate(date, BusyConstants.DATE_REGION_MONTH_LEVEL_1);
    }

    /**
     * 获取X月前的日期
     * @param date
     * @param x
     * @return
     */
    public static Date getBeforeXMonthDate(Date date, int x) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1 * x);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static String getDateAddDaysStr(Date date, int numDays) {
        Assert.notNull(date);
        return format(DateUtils.addDays(date, numDays));
    }

    /**
     * 获取昨天一天的开始时间与结束时间
     * @param date
     * @return
     */
    public static DateIntervalVo getYesterdayDateRange(Date date) {
        date = DateUtils.addDays(date, -1);
        DateIntervalVo dateIntervalVo = new DateIntervalVo();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 59);
        dateIntervalVo.setStartDate(startCalendar.getTime());
        dateIntervalVo.setEndDate(endCalendar.getTime());
        return dateIntervalVo;
    }

    /**
     * 获取上周的开始时间与结束时间(自然周，周一至周日)
     * @param date
     * @return
     */
    public static DateIntervalVo getLastWeekDateRange(Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        DateIntervalVo dateIntervalVo = new DateIntervalVo();
        int dayOfWeek = startCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        startCalendar.add(Calendar.DATE, offset1 - 7);
        endCalendar.add(Calendar.DATE, offset2 - 7);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 59);
        dateIntervalVo.setStartDate(startCalendar.getTime());
        dateIntervalVo.setEndDate(endCalendar.getTime());
        return dateIntervalVo;
    }

    /**
     * 按非自然周计算，获取上周开始时间与结束时间（周日至周六为一周）
     * @param date
     * @return
     */
    public static DateIntervalVo getLastWeekDateRange2(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        // 默认当前时间为周天,上周天则往前推7天，上周六往前推1天
        int sundayDelayNum = 7;
        int saturdayDelayNum = 1;
        // 当前日期为周一
        if(dayOfWeek == 1) {
            sundayDelayNum = 8;
            saturdayDelayNum = 2;
        }
        if(dayOfWeek == 2) {
            sundayDelayNum = 9;
            saturdayDelayNum = 3;
        }
        if(dayOfWeek == 3) {
            sundayDelayNum = 10;
            saturdayDelayNum = 4;
        }
        if(dayOfWeek == 4) {
            sundayDelayNum = 11;
            saturdayDelayNum = 5;
        }
        if(dayOfWeek == 5) {
            sundayDelayNum = 12;
            saturdayDelayNum = 6;
        }
        if(dayOfWeek == 6) {
            sundayDelayNum = 13;
            saturdayDelayNum = 7;
        }
        Date lastSunday = DateUtils.addDays(date, -sundayDelayNum);
        Date lastSaturday = DateUtils.addDays(date, -saturdayDelayNum);
        Calendar sundayCalendar = Calendar.getInstance();
        Calendar saturdayCalendar = Calendar.getInstance();
        sundayCalendar.setTime(lastSunday);
        saturdayCalendar.setTime(lastSaturday);
        sundayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        sundayCalendar.set(Calendar.MINUTE, 0);
        sundayCalendar.set(Calendar.SECOND, 0);
        sundayCalendar.set(Calendar.MILLISECOND, 0);
        saturdayCalendar.set(Calendar.HOUR_OF_DAY, 23);
        saturdayCalendar.set(Calendar.MINUTE, 59);
        saturdayCalendar.set(Calendar.SECOND, 59);
        saturdayCalendar.set(Calendar.MILLISECOND, 59);
        DateIntervalVo dateIntervalVo = new DateIntervalVo();
        dateIntervalVo.setStartDate(sundayCalendar.getTime());
        dateIntervalVo.setEndDate(saturdayCalendar.getTime());
        return dateIntervalVo;
    }

    /**
     * 获取上月的开始时间与结束时间
     * @param date
     * @return
     */
    public static DateIntervalVo getLastMonthDateRange(Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        DateIntervalVo dateIntervalVo = new DateIntervalVo();
        startCalendar.add(Calendar.MONTH, -1);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        int month = endCalendar.get(Calendar.MONTH);
        endCalendar.set(Calendar.MONTH, month - 1);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 59);
        dateIntervalVo.setStartDate(startCalendar.getTime());
        dateIntervalVo.setEndDate(endCalendar.getTime());
        return dateIntervalVo;
    }

    /**
     * 获取X天之前的日期作为开始时间，当前时间作为结束时间的时间区间
     * @param date
     * @return
     */
    public static DateIntervalVo getXDateRange(Date date, int x) {
        Date startDate = DateUtils.addDays(date, -x);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        DateIntervalVo dateIntervalVo = new DateIntervalVo();
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);
        dateIntervalVo.setStartDate(startCalendar.getTime());
        dateIntervalVo.setEndDate(endCalendar.getTime());
        return dateIntervalVo;
    }

    /**
     * 获取年份
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.YEAR);
        return String.valueOf(i);
    }

    /**
     * 获取月份
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.MONTH) + 1;
        return String.valueOf(i);
    }

    /**
     * 获取当前日期在一年中的周次(按周日为周第一天计算)
     * @param date
     * @return
     */
    public static int getWeekNum(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
