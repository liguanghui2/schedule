package com.yhd.gps.busyservice.strategy.impl;

import com.yhd.gps.busyservice.strategy.ILPCompareStrategy;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.LPCompareResultVo;

/**
 * LP比对：分时段促销不限购
 * @Author lipengcheng
 * @CreateTime 2016-7-6 上午10:01:58
 */
public class IsPeriodAndLimitNoneLPComparator extends BaseLPPeriodComparator implements ILPCompareStrategy {

    @Override
    public LPCompareResultVo execute(BusyLandingProductVo landingProductVo, GPSPromotionVo gpsPromotionVo) {
        LPCompareResultVo result = compare(landingProductVo, gpsPromotionVo);

        // 不限购不比较活动已售数量

        return result;
    }

}
