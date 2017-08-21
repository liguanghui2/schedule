package com.yhd.gps.busyservice.strategy.impl;

import com.yhd.gps.busyservice.strategy.ILPCompareStrategy;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.Judger;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.LPCompareResultVo;

/**
 * LP比对：精确匹配
 * @Author lipengcheng
 * @CreateTime 2016-7-6 上午10:01:58
 */
public class ExactMatchLPComparator extends BaseLPComparator implements ILPCompareStrategy {

    @Override
    public LPCompareResultVo execute(BusyLandingProductVo landingProductVo, GPSPromotionVo gpsPromotionVo) {
        LPCompareResultVo result = compare(landingProductVo, gpsPromotionVo);
        if(Judger.isLPCompareHasError(result)) {
            return result;
        }

        // 比较活动已售数量(不限购时不比较soldNum)
        if(gpsPromotionVo.getLimitType() != -1
           && landingProductVo.getSoldNum() != gpsPromotionVo.getSoldNum().intValue()) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_SOLD_NUM_NOT_MATCH);
            return result;
        }
        // 比较活动开始时间
        if(!landingProductVo.getStartTime().equals(gpsPromotionVo.getStartTime())) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_START_TIME_NOT_MATCH);
            return result;
        }
        // 比较活动结束时间
        if(!landingProductVo.getEndTime().equals(gpsPromotionVo.getEndTime())) {
            result.setErrorCode(ScheduleConstants.LP_COMPARE_ERROR_CODE_END_TIME_NOT_MATCH);
            return result;
        }
        return result;
    }
}
