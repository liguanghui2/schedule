package com.yhd.gps.schedule.common;

import com.yihaodian.busy.vo.BusyAreaVo;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-2 下午04:27:39
 */
public class ScheduleCacheKeyUtil {

    /**
     * GPS_BUSY_PRICE_CHANGE_MSG 根据pm_info_id和channelId缓存
     */
    public static final String GPS_BUSY_PRICE_CHANGE_MSG_BY_PMINFOID_AND_CHANNELID_AND_AREA = "GPS_BUSY_PRICE_CHANGE_MSG_BY_PMINFOID_AND_CHANNELID_AND_AREA";

    public static String BUSY_GPS_PRODUCT_KEY_PREFIX = "BUSY_GPS_PRODUCT_KEY_PREFIX";

    public static String getBusyPriceChangeMsgCacheKey(Long pmInfoId, Long channelId, BusyAreaVo areaVo) {
        StringBuilder key = new StringBuilder(100);
        key.append(GPS_BUSY_PRICE_CHANGE_MSG_BY_PMINFOID_AND_CHANNELID_AND_AREA).append("_").append(pmInfoId)
                .append("_").append(channelId).append("_")
                .append((areaVo == null || areaVo.getProvinceId() == null) ? 0 : areaVo.getProvinceId()).append("_")
                .append((areaVo == null || areaVo.getCityId() == null) ? 0 : areaVo.getCityId()).append("_")
                .append((areaVo == null || areaVo.getCountyId() == null) ? 0 : areaVo.getCountyId());
        return key.toString();
    }

    public static String getGpsProductCacheKey(Long productId) {
        StringBuilder key = new StringBuilder(100);
        key.append(BUSY_GPS_PRODUCT_KEY_PREFIX).append("_").append(productId);
        return key.toString();
    }

}
