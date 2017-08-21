package com.yhd.gps.busyservice.handler;

import org.apache.commons.lang.StringUtils;

import com.yhd.gps.busyservice.strategy.ILPCompareStrategy;
import com.yhd.gps.busyservice.strategy.impl.ExactMatchLPComparator;
import com.yhd.gps.busyservice.strategy.impl.IsPeriodAndLimitNoneLPComparator;
import com.yhd.gps.busyservice.strategy.impl.IsPeriodAndLimitPerDayNumLPComparator;
import com.yhd.gps.busyservice.strategy.impl.IsPeriodAndLimitTotalNumLPComparator;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.LPCompareResultVo;
import com.yihaodian.busy.exception.BusyException;

/**
 * LP比对handler
 * @Author lipengcheng
 * @CreateTime 2016-7-6 上午09:32:38
 */
public class LPCompareHandler {

    public static LPCompareResultVo handle(BusyLandingProductVo landingProductVo, GPSPromotionVo gpsPromotionVo) {
        if(landingProductVo == null || gpsPromotionVo == null) {
            throw new BusyException("landingProductVo为空或gpsPromotionVo为空");
        }

        ILPCompareStrategy strategy = null;
        if(ScheduleConstants.LANDING_PAGE_PROMOTION_TYPE_NOT_PERIOD.equals(gpsPromotionVo.getPromotionType())) {
            // 不是分时段促销，进行精确匹配
            strategy = new ExactMatchLPComparator();
        } else if(ScheduleConstants.LANDING_PAGE_PROMOTION_TYPE_IS_PERIOD.equals(gpsPromotionVo.getPromotionType())
                  && ScheduleConstants.LANDING_PAGE_PROMOTION_LIMIT_TOTAL.equals(gpsPromotionVo.getLimitType())) {
            // 分时段促销限总量
            strategy = new IsPeriodAndLimitTotalNumLPComparator();
        } else if(ScheduleConstants.LANDING_PAGE_PROMOTION_TYPE_IS_PERIOD.equals(gpsPromotionVo.getPromotionType())
                  && ScheduleConstants.LANDING_PAGE_PROMOTION_LIMIT_PER_DAY.equals(gpsPromotionVo.getLimitType())) {
            // 分时段促销限每日总量
            strategy = new IsPeriodAndLimitPerDayNumLPComparator();
        } else if(ScheduleConstants.LANDING_PAGE_PROMOTION_TYPE_IS_PERIOD.equals(gpsPromotionVo.getPromotionType())
                  && ScheduleConstants.LANDING_PAGE_PROMOTION_LIMIT_NONE.equals(gpsPromotionVo.getLimitType())) {
            // 分时段促销不限购
            strategy = new IsPeriodAndLimitNoneLPComparator();
        } else {
            // 匹配不到，说明数据有问题
            throw new BusyException("促销限购类型错误无法比对：" + gpsPromotionVo.toString());
        }
        LPCompareResultVo resultVo = strategy.execute(landingProductVo, gpsPromotionVo);
        // 如果errorCode为空说明无异常，返回null
        if(resultVo != null && StringUtils.isEmpty(resultVo.getErrorCode())) {
            resultVo = null;
        }
        return resultVo;
    }
}
