package com.yhd.gps.busyservice.strategy.impl;

import com.yhd.gps.busyservice.strategy.ILPCompareStrategy;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.Judger;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.LPCompareResultVo;

/**
 * LP比对：分时段促销限总量
 * @Author lipengcheng
 * @CreateTime 2016-7-6 上午10:01:58
 */
public class IsPeriodAndLimitTotalNumLPComparator extends BaseLPPeriodComparator implements ILPCompareStrategy {

    @Override
    public LPCompareResultVo execute(BusyLandingProductVo landingProductVo, GPSPromotionVo gpsPromotionVo) {
        LPCompareResultVo result = compare(landingProductVo, gpsPromotionVo);
        if(Judger.isLPCompareHasError(result)) {
            return result;
        }

        // 比较活动已售数量
        if(landingProductVo.getSoldNum() != gpsPromotionVo.getSoldNum().intValue()) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_SOLD_NUM_NOT_MATCH);
            return result;
        }
        return result;
    }
}
