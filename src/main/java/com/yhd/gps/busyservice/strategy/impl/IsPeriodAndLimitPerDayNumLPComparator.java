package com.yhd.gps.busyservice.strategy.impl;

import com.yhd.gps.busyservice.strategy.ILPCompareStrategy;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.Judger;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.LPCompareResultVo;

/**
 * LP比对：分时段促销限每日总量
 * @Author lipengcheng
 * @CreateTime 2016-7-6 上午10:01:58
 */
public class IsPeriodAndLimitPerDayNumLPComparator extends BaseLPPeriodComparator implements ILPCompareStrategy {

    @Override
    public LPCompareResultVo execute(BusyLandingProductVo landingProductVo, GPSPromotionVo gpsPromotionVo) {
        LPCompareResultVo result = compare(landingProductVo, gpsPromotionVo);
        if(Judger.isLPCompareHasError(result)) {
            return result;
        }

        // 活动开始时间相同，比较活动已售数量
        int isSameStratTime = landingProductVo.getStartTime().compareTo(gpsPromotionVo.getStartTime());
        if(isSameStratTime == 0 && landingProductVo.getSoldNum() != gpsPromotionVo.getSoldNum().intValue()) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_SOLD_NUM_NOT_MATCH);
            return result;
        }
        // landingProductVo活动开始时间小于gpsPromotion活动开始时间，表示gpsPromotion是未来的促销，活动已售数量为0
        if(isSameStratTime < 0 && landingProductVo.getStartTime().compareTo(gpsPromotionVo.getStartTime()) < 0
           && gpsPromotionVo.getSoldNum() > 0) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_SOLD_NUM_NOT_MATCH);
            return result;
        }
        // landingProductVo活动开始时间大于gpsPromotion活动开始时间，landingProductVo已重置soldNum，不校验活动已售数量
        return result;
    }
}
