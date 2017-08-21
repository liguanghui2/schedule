package com.yhd.gps.schedule.common;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class ScheduleCacheConstants {
    /**
     * busy工程cache bean name
     */
    public static String BUSY_DATA_CACHE_BEAN_NAME = "busyDataCache";

    /**
     * 缓存的空对象
     */
    public static String BUSY_NULL = "BUSY_NULL";
    /**
     * 空对象缓存时间
     */
    public static int BUSY_NULL_EXPIERY_MINUTES = 1 * 10;

    /**
     * 价格缓存时间
     */
    public static int BUSY_PRICE_EXPIERY_MINUTES = 1 * 60;

    /**
     * gps_product 缓存时间
     */
    public static int BUSY_GPS_PRODUCT_EXPIRY_MINUTES = 30 * 24 * 60;

    /**
     * 价格变更消息内容去重缓存时间
     */
    public static int BUSY_DISTINTC_PRICE_CHANGE_EXPIRY_MINUTES = 24 * 60;

    /**
     * 价格风控规则缓存时间
     */
    public static int BUSY_PRICE_RISK_RULE_EXPIRY_MINUTES = 2 * 60;

    /**
     * add by pangzhiwang
     * 
     * 历史价格缓存缓存时间
     */
    public static int GPS_BUSY_PRICE_CHANGE_EXPIRY_MINUTES = 2 * 60;

}
