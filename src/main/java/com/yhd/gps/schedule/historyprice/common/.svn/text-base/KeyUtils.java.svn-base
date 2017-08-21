package com.yhd.gps.schedule.historyprice.common;

/**
 * Map的key
 * @Author liuxiangrong
 * @CreateTime 2016-2-2 下午02:51:30
 */
public final class KeyUtils {

    /**
     * pmInfoId + "-" + channelId
     * @param pmInfoId
     * @param channelId
     * @return
     */
    public static final String buildPriceMessagesMapKey(Long pmInfoId, Long channelId) {
        return pmInfoId + "-" + channelId;
    }

    /**
     * pmInfoId + "-" + channelId + "-" + dateRegionLevel
     * @param pmInfoId
     * @param channelId
     * @param dateSectionLevel
     * @return
     */
    public static final String buildDateSectionPriceInfoKey(Long pmInfoId, Long channelId, Integer dateSectionLevel) {
        return pmInfoId + "-" + channelId + "-" + dateSectionLevel;
    }

    /**
     * 拼装messageType
     * @param channelId 渠道id
     * @param dateRegionLevel 3月最低价，1月最低价，自定义3月之内的最低价
     * @return
     */
    public static final String buildMessageType(Long channelId, Integer dateRegionLevel) {
        return channelId + "-" + dateRegionLevel;
    }
}
