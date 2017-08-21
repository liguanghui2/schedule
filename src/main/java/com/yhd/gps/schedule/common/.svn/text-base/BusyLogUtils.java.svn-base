package com.yhd.gps.schedule.common;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yhd.gps.promotion.vo.GPSPromotionLogVo;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.vo.BaseLogVo;
import com.yhd.gps.schedule.vo.BusyProductPromRuleLogVo;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

public class BusyLogUtils {

    /**
     * 获取日志基本信息
     * @param opType
     * @return
     */
    public static BaseLogVo getBaseLog(String opType) {
        BaseLogVo baseLogVo = new BaseLogVo();

        String poolName = PoolUtils.getPoolName();
        String clientVersion = PoolUtils.getClientVersion();
        String clientIP = PoolUtils.getPoolIP();
        String serverIP = PoolUtils.getPoolIP();

        baseLogVo.setClientIP(StringUtils.substring(clientIP, 0, 50));
        baseLogVo.setClientPoolName(StringUtils.substring(poolName, 0, 200));
        baseLogVo.setClientVersion(StringUtils.substring(clientVersion, 0, 20));
        baseLogVo.setServerIP(StringUtils.substring(serverIP, 0, 30));
        baseLogVo.setOpType(opType);
        baseLogVo.setCreateTime(new Date());

        return baseLogVo;
    }

    /**
     * 将BusyPmPriceVo转换为日志对象
     * @param productPromRuleVo
     * @param opType
     * @return
     */
    public static BusyProductPromRuleLogVo buildProductPromRuleLogVo(BSProductPromRuleVo productPromRuleVo,
                                                                     String opType) {
        BusyProductPromRuleLogVo logVo = new BusyProductPromRuleLogVo();
        logVo.setPmId(productPromRuleVo.getPmId());
        logVo.setProductId(productPromRuleVo.getProductId());
        logVo.setMerchantId(productPromRuleVo.getMerchantId());
        logVo.setPromoteType(productPromRuleVo.getPromoteType());
        logVo.setPromNonMemberPrice(productPromRuleVo.getPromNonMemberPrice());
        logVo.setSpecialPriceLimitNumber(productPromRuleVo.getSpecialPriceLimitNumber());
        logVo.setUserPriceLimitNumber(productPromRuleVo.getUserPriceLimitNumber());
        logVo.setSoldNum(productPromRuleVo.getSoldNum());
        logVo.setPromStartTime(productPromRuleVo.getPromStartTime());
        logVo.setPromEndTime(productPromRuleVo.getPromEndTime());
        logVo.setVisualSerialType(productPromRuleVo.getIsVisualSerial());
        logVo.setBackendOperatorId(productPromRuleVo.getBackendOperatorId());
        logVo.setRuleType(productPromRuleVo.getRuleType());
        logVo.setMutexPromotion(productPromRuleVo.getMutexPromotion());
        logVo.setDiscount(productPromRuleVo.getDiscount());
        logVo.setParentRuleId(productPromRuleVo.getParentRuleId());
        logVo.setPromName(productPromRuleVo.getPromName());
        logVo.setRuleId(productPromRuleVo.getId());
        logVo.setRuleCreateTime(productPromRuleVo.getUpdateTime());
        logVo.setRuleUpdateTime(productPromRuleVo.getUpdateTime());
        logVo.setShoppingBagSoldNum(productPromRuleVo.getBagSoldNum());
        logVo.setPriceLockType(productPromRuleVo.getPriceLockType());
        logVo.setPriceChangeRemind(productPromRuleVo.getPriceChangeRemind());
        logVo.setChannelId(productPromRuleVo.getChannelId());
        logVo.setMinCount(productPromRuleVo.getMinCount());
        logVo.setPayNum(productPromRuleVo.getPayNum());

        BaseLogVo baseLog = BusyLogUtils.getBaseLog(opType);
        logVo.setClientPoolName(baseLog.getClientPoolName());
        logVo.setClientVersion(baseLog.getClientVersion());
        logVo.setClientIP(baseLog.getClientIP());
        logVo.setServerIP(baseLog.getServerIP());
        logVo.setCreateTime(baseLog.getCreateTime());
        logVo.setOpType(baseLog.getOpType());

        return logVo;
    }

    /**
     * 将GPSPromotionVo转换为日志对象
     * @param gpsPromtionVo
     * @param opType
     * @param memo
     * @return
     */
    public static GPSPromotionLogVo buildGPSPromotionLogVo(GPSPromotionVo gpsPromtionVo, String opType, String memo) {
        GPSPromotionLogVo log = new GPSPromotionLogVo();

        BeanUtils.copyProperties(gpsPromtionVo, log);
        log.setId(null);
        log.setGpsPromotionId(gpsPromtionVo.getPromotionId());

        log.setMemo(memo);
        log.setPromCreateTime(gpsPromtionVo.getCreateTime());
        log.setPromUpdateTime(gpsPromtionVo.getUpdateTime());

        BaseLogVo baseLog = BusyLogUtils.getBaseLog(opType);
        log.setClientPoolName(baseLog.getClientPoolName());
        log.setClientVersion(baseLog.getClientVersion());
        log.setClientIP(baseLog.getClientIP());
        log.setServerIP(baseLog.getServerIP());
        log.setCreateTime(baseLog.getCreateTime());
        log.setOpType(baseLog.getOpType());

        return log;
    }

}
