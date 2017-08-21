package com.yhd.gps.busyservice.strategy.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleContext;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.LPCompareResultVo;
import com.yhd.gps.schedule.vo.PromotionPeriodTimeVo;
import com.yihaodian.busy.exception.BusyException;

/**
 * LP比对基类
 * @Author lipengcheng
 * @CreateTime 2016-7-6 上午10:01:58
 */
public class BaseLPPeriodComparator extends BaseLPComparator {
    private static final Log log = LogFactory.getLog(BaseLPPeriodComparator.class);

    @SuppressWarnings("deprecation")
    public LPCompareResultVo compare(BusyLandingProductVo landingProductVo, GPSPromotionVo gpsPromotionVo) {
        if(landingProductVo == null || gpsPromotionVo == null) {
            throw new BusyException("landingProductVo为空或gpsPromotionVo为空");
        }
        LPCompareResultVo result = super.compare(landingProductVo, gpsPromotionVo);

        // 分时段促销取lp分时段表的活动起止时间
        PromotionPeriodTimeVo promotionPeriodTimeVo = (PromotionPeriodTimeVo) ScheduleContext
                .getValue(ScheduleConstants.LP_PROMOTION_PERIOD_TIME_PREFIX + gpsPromotionVo.getPromotionId());
        if(promotionPeriodTimeVo == null) {
            log.error("分时段促销：" + gpsPromotionVo.getPromotionId() + " 查不到promotionv2_period_time表记录");
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_ERROR_DATA);
            return result;
        }

        // 比较活动开始时间
        if(promotionPeriodTimeVo.getStartDate().after(gpsPromotionVo.getStartTime())
           || landingProductVo.getStartTime().getHours() != gpsPromotionVo.getStartTime().getHours()
           || landingProductVo.getStartTime().getMinutes() != gpsPromotionVo.getStartTime().getMinutes()
           || landingProductVo.getStartTime().getSeconds() != gpsPromotionVo.getStartTime().getSeconds()) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_START_TIME_NOT_MATCH);
            return result;
        }
        // 比较活动结束时间
        if(promotionPeriodTimeVo.getEndDate().before(gpsPromotionVo.getEndTime())
           || landingProductVo.getEndTime().getHours() != gpsPromotionVo.getEndTime().getHours()
           || landingProductVo.getEndTime().getMinutes() != gpsPromotionVo.getEndTime().getMinutes()
           || landingProductVo.getEndTime().getSeconds() != gpsPromotionVo.getEndTime().getSeconds()) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_END_TIME_NOT_MATCH);
            return result;
        }

        return result;
    }
}
