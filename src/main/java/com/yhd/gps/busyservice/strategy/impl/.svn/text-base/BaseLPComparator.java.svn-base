package com.yhd.gps.busyservice.strategy.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.LPCompareResultVo;
import com.yihaodian.busy.exception.BusyException;

/**
 * LP比对基类
 * @Author lipengcheng
 * @CreateTime 2016-7-6 上午10:01:58
 */
public class BaseLPComparator {
    private static final Log log = LogFactory.getLog(BaseLPComparator.class);

    public LPCompareResultVo compare(BusyLandingProductVo landingProductVo, GPSPromotionVo gpsPromotionVo) {
        if(landingProductVo == null || gpsPromotionVo == null) {
            throw new BusyException("landingProductVo为空或gpsPromotionVo为空");
        }
        LPCompareResultVo result = new LPCompareResultVo(gpsPromotionVo.getPromotionId(), gpsPromotionVo.getPmId());
        // 比较promotionId和pmId
        if(!landingProductVo.getPromotionId().equals(gpsPromotionVo.getPromotionId())
           || !landingProductVo.getPmId().equals(gpsPromotionVo.getPmId())) {
            log.error("取到错误的数据：landingProductVo：" + landingProductVo.toString() + ",gpsPromotionVo:"
                      + gpsPromotionVo.toString());
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_ERROR_DATA);
            return result;
        }
        // 比较促销类型
        if(!landingProductVo.getIsPeriod().equals(gpsPromotionVo.getPromotionType())) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_PROMOTION_TYPE_NOT_MATCH);
            return result;
        }
        /**
         * 比较限购类型
         * 
         * <pre>
         * lp限购与gps_promotion限购类型对照关系
         * totalQuantityLimitType,limitType
         * 1,0
         * 2,1
         * -1,-1
         * </pre>
         */
        //
        // 是否限购
        boolean isLimit = landingProductVo.getTotalQuantityLimitType() != -1 && gpsPromotionVo.getLimitType() != -1;
        if((!isLimit && !landingProductVo.getTotalQuantityLimitType().equals(gpsPromotionVo.getLimitType())) || isLimit
           && !landingProductVo.getTotalQuantityLimitType().equals(gpsPromotionVo.getLimitType() + 1)) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_LIMIT_TYPE_NOT_MATCH);
            return result;
        }
        // 比较价格
        if(landingProductVo.getActivityPrice().compareTo(gpsPromotionVo.getPromotionPrice()) != 0) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_PROMOTION_PRICE_NOT_MATCH);
            return result;
        }
        // 比较活动限购数量
        if(landingProductVo.getTotalQuantityLimit() != gpsPromotionVo.getLimitStock().intValue()) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_LIMIT_STOCK_NOT_MATCH);
            return result;
        }
        // 比较积分
        if(landingProductVo.getActivityPoint().longValue() != gpsPromotionVo.getPromotionPoint()) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_PROMOTION_POINT_NOT_MATCH);
            return result;
        }
        // 全部匹配
        return result;
    }
}
