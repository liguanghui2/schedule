package com.yhd.gps.schedule.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.utils.DateUtil;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-5-3 下午06:31:42
 */
public class MiscConfigUtils {

    private static final Log logger = LogFactory.getLog(MiscConfigUtils.class);

    private static BusyMiscConfigDao busyMiscConfigDao;

    static {
        busyMiscConfigDao = (BusyMiscConfigDao) SpringBeanUtil.getBean("busyMiscConfigDao");
    }

    /**
     * 根据itemKey查找配置的itemValue
     * @param itemKey
     * @return
     */
    public static String getItemValue(String itemKey) {
        String result = null;
        try {
            BusyMiscConfigVo configVo = busyMiscConfigDao.getBusyMiscConfigVoByKey(itemKey);
            if(configVo != null && configVo.getItemEnabled()) {
                String itemValue = configVo.getItemValue();
                if(itemValue != null && StringUtils.isNotBlank(itemValue)) {
                    result = itemValue;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = null;
        }
        return result;
    }

    /**
     * 获取要清除pm_price_change_msg表的延期时间
     * @return
     */
    public static Integer getPriceChangeMsgCleanDelayDays() {
        Integer cleanDelayDays = ScheduleConstants.PRICE_CHANGE_MSG_CLEAN_DELAY_DAYS_DEFAULT;

        String customCleanDelayDays = getItemValue(ScheduleConstants.PRICE_CHANGE_MSG_CLEAN_DELAY_DAYS);
        if(StringUtils.isNotEmpty(customCleanDelayDays)) {
            cleanDelayDays = Integer.valueOf(customCleanDelayDays);
        }
        return cleanDelayDays;
    }

    public static BusyMiscConfigVo getLastSyncDate() {
        return busyMiscConfigDao.getBusyMiscConfigVoByKey(ScheduleConstants.GPS_PRODUCT_SYNC_TIME);
    }

    /**
     * 自定义打标参照的商品价格变化的开始时间
     * @return
     */
    public static Date getCustomStartDate4RegionMonthLevel() {
        Date date = null;
        String itemValue = getItemValue(ScheduleConstants.CUSTOM_START_DATE_FOR_REGION_MONTH_LEVEL);
        if(StringUtils.isNotBlank(itemValue)) {
            date = DateUtil.parseDate(itemValue);
        }
        return date;
    }

    /**
     * 自定义时间段查询最低价
     * @return
     */
    public static String[] getCustomDate4MinPrice() {
        String itemValue = getItemValue(ScheduleConstants.CUSTOM_DATE_FOR_MIN_PRICE);
        if(StringUtils.isNotBlank(itemValue)) {
            return itemValue.split(",");
        }
        return null;
    }

    /**
     * 自定义补偿数据发送日期
     * @return
     */
    public static String[] getCustomCompensateLoseDataTime() {
        String itemValue = getItemValue(ScheduleConstants.COMPENSATE_LOSE_DATA_TIME);
        if(StringUtils.isNotBlank(itemValue)) {
            return itemValue.split(",");
        }
        return null;
    }

    /**
     * 自定义补偿数据的商家
     * @return
     */
    public static String[] getCustomCompensateMerchantIds() {
        String itemValue = getItemValue(ScheduleConstants.COMPENSATE_LOSE_DATA_BY_MERCHANT4SCHEDULE);
        if(StringUtils.isNotBlank(itemValue)) {
            return itemValue.split(",");
        }
        return null;
    }

    public static Integer getShardingCount(String tableName) {
        Integer result = null;
        if(StringUtils.isBlank(tableName)) {
            return result;
        }
        String itemValue = getItemValue(tableName);
        if(StringUtils.isNotEmpty(itemValue) && StringUtils.isNumeric(itemValue)) {
            result = Integer.valueOf(itemValue);
        }
        return result;
    }

    public static List<Long> getConfigListByItemKey(String itemKey) {
        List<Long> result = new ArrayList<Long>();
        if(StringUtils.isBlank(itemKey)) {
            return result;
        }
        try {
            String itemValue = getItemValue(itemKey);
            if(null == itemValue) {
                return result;
            }
            String[] items = itemValue.split(",");
            for(String item : items) {
                if(StringUtils.isNotBlank(item)) {
                    result.add(Long.valueOf(item));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}