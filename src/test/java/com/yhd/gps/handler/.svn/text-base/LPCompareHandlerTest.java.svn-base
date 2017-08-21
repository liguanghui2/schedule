package com.yhd.gps.handler;

import java.math.BigDecimal;

import org.junit.Test;

import com.ibm.icu.util.Calendar;
import com.yhd.gps.BaseSpringWithUnitilsTest;
import com.yhd.gps.busyservice.handler.LPCompareHandler;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleContext;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.LPCompareResultVo;
import com.yhd.gps.schedule.vo.PromotionPeriodTimeVo;

public class LPCompareHandlerTest extends BaseSpringWithUnitilsTest {

    /**
     * 非分时段促销
     */
    @Test
    public void testHandle001() {
        // 参数异常
        LPCompareResultVo resultVo = null;
        try {
            resultVo = LPCompareHandler.handle(null, new GPSPromotionVo());
            assertTrue(false);
        } catch (Exception e) {
            assertNotNull(e);
        }
        try {
            resultVo = LPCompareHandler.handle(new BusyLandingProductVo(), null);
            assertTrue(false);
        } catch (Exception e) {
            assertNotNull(e);
        }

        BusyLandingProductVo landingProductVo = new BusyLandingProductVo();
        landingProductVo.setPromotionId(1L);
        landingProductVo.setPmId(68325L);
        landingProductVo.setIsPeriod(0);
        landingProductVo.setTotalQuantityLimitType(-1);
        landingProductVo.setActivityPrice(new BigDecimal("10.0"));
        landingProductVo.setTotalQuantityLimit(100);
        landingProductVo.setActivityPoint(new BigDecimal(5));
        landingProductVo.setSoldNum(50);
        Calendar startTime = Calendar.getInstance();
        startTime.set(2016, 8, 10, 0, 0, 0);
        landingProductVo.setStartTime(startTime.getTime());
        Calendar endTime = Calendar.getInstance();
        endTime.set(2016, 9, 10, 23, 59, 59);
        landingProductVo.setEndTime(endTime.getTime());

        GPSPromotionVo gpsPromotionVo = new GPSPromotionVo();
        gpsPromotionVo.setPromotionId(1L);
        gpsPromotionVo.setPmId(68325L);
        gpsPromotionVo.setPromotionType(0);
        gpsPromotionVo.setLimitType(-1);
        gpsPromotionVo.setPromotionPrice(new BigDecimal("10.00"));
        gpsPromotionVo.setLimitStock(100L);
        gpsPromotionVo.setPromotionPoint(5L);
        gpsPromotionVo.setSoldNum(50L);
        gpsPromotionVo.setStartTime(startTime.getTime());
        gpsPromotionVo.setEndTime(endTime.getTime());

        // 完全匹配
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNull(resultVo);

        // 促销id不匹配
        gpsPromotionVo.setPromotionId(2L);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_ERROR_DATA.equals(resultVo.getErrorCode()));

        // pmId不匹配
        gpsPromotionVo.setPromotionId(1L);
        gpsPromotionVo.setPmId(1L);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_ERROR_DATA.equals(resultVo.getErrorCode()));

        // 促销类型不匹配
        gpsPromotionVo.setPmId(68325L);
        landingProductVo.setIsPeriod(1);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_PROMOTION_TYPE_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 限购类型不匹配
        landingProductVo.setIsPeriod(0);
        landingProductVo.setTotalQuantityLimitType(0);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_LIMIT_TYPE_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 价格不匹配
        landingProductVo.setTotalQuantityLimitType(-1);
        gpsPromotionVo.setPromotionPrice(new BigDecimal(99));
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_PROMOTION_PRICE_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 限购数量不匹配
        gpsPromotionVo.setPromotionPrice(new BigDecimal(10));
        gpsPromotionVo.setLimitStock(99L);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_LIMIT_STOCK_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 积分不匹配
        gpsPromotionVo.setLimitStock(100L);
        gpsPromotionVo.setPromotionPoint(1L);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_PROMOTION_POINT_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 已售数量不匹配
        gpsPromotionVo.setPromotionPoint(5L);
        gpsPromotionVo.setLimitType(0);
        landingProductVo.setTotalQuantityLimitType(1);
        gpsPromotionVo.setSoldNum(1L);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_SOLD_NUM_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 活动开始时间不匹配
        gpsPromotionVo.setSoldNum(50L);
        startTime.set(Calendar.MONTH, 7);
        gpsPromotionVo.setStartTime(startTime.getTime());
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_START_TIME_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 活动截止时间不匹配
        startTime.set(Calendar.MONTH, 8);
        gpsPromotionVo.setStartTime(startTime.getTime());
        endTime.set(Calendar.MONTH, 11);
        gpsPromotionVo.setEndTime(endTime.getTime());
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_END_TIME_NOT_MATCH.equals(resultVo.getErrorCode()));

    }

    /**
     * 分时段促销
     */
    @Test
    public void testHandle002() {
        BusyLandingProductVo landingProductVo = new BusyLandingProductVo();
        landingProductVo.setPromotionId(1L);
        landingProductVo.setPmId(68325L);
        landingProductVo.setIsPeriod(1);
        landingProductVo.setTotalQuantityLimitType(-1);
        landingProductVo.setActivityPrice(new BigDecimal(10));
        landingProductVo.setTotalQuantityLimit(100);
        landingProductVo.setActivityPoint(new BigDecimal(5));
        landingProductVo.setSoldNum(50);
        Calendar startTime = Calendar.getInstance();
        startTime.set(2016, 7, 2, 0, 0, 0);
        landingProductVo.setStartTime(startTime.getTime());
        Calendar endTime = Calendar.getInstance();
        endTime.set(2016, 7, 2, 23, 59, 59);
        landingProductVo.setEndTime(endTime.getTime());

        GPSPromotionVo gpsPromotionVo = new GPSPromotionVo();
        gpsPromotionVo.setPromotionId(1L);
        gpsPromotionVo.setPmId(68325L);
        gpsPromotionVo.setPromotionType(1);
        gpsPromotionVo.setLimitType(-1);
        gpsPromotionVo.setPromotionPrice(new BigDecimal(10));
        gpsPromotionVo.setLimitStock(100L);
        gpsPromotionVo.setPromotionPoint(5L);
        gpsPromotionVo.setSoldNum(50L);
        gpsPromotionVo.setStartTime(startTime.getTime());
        gpsPromotionVo.setEndTime(endTime.getTime());

        PromotionPeriodTimeVo promotionPeriodTimeVo = new PromotionPeriodTimeVo();
        promotionPeriodTimeVo.setPromotionId(1L);
        promotionPeriodTimeVo.setStartDate(startTime.getTime());
        // 设置lp分时段促销结束时间为2016-7-30 23:59:59
        endTime.set(Calendar.DATE, 30);
        promotionPeriodTimeVo.setEndDate(endTime.getTime());

        // 查不到lp分时段表
        LPCompareResultVo resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_ERROR_DATA.equals(resultVo.getErrorCode()));

        // 初始化数据
        ScheduleContext.setValue(ScheduleConstants.LP_PROMOTION_PERIOD_TIME_PREFIX + gpsPromotionVo.getPromotionId(),
                promotionPeriodTimeVo);

        // 不限购
        // 完全匹配
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNull(resultVo);

        // 开始时间不匹配
        startTime.set(Calendar.DATE, 1);
        gpsPromotionVo.setStartTime(startTime.getTime());
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_START_TIME_NOT_MATCH.equals(resultVo.getErrorCode()));

        startTime.set(Calendar.DATE, 2);
        startTime.set(Calendar.HOUR_OF_DAY, 8);
        gpsPromotionVo.setStartTime(startTime.getTime());
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_START_TIME_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 截止时间不匹配
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        gpsPromotionVo.setStartTime(startTime.getTime());
        endTime.set(Calendar.DATE, 31);
        gpsPromotionVo.setEndTime(endTime.getTime());
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_END_TIME_NOT_MATCH.equals(resultVo.getErrorCode()));

        endTime.set(Calendar.DATE, 30);
        endTime.set(Calendar.HOUR_OF_DAY, 22);
        gpsPromotionVo.setEndTime(endTime.getTime());
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_END_TIME_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 重置数据
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        gpsPromotionVo.setEndTime(endTime.getTime());
        // 限总量
        gpsPromotionVo.setLimitType(0);
        landingProductVo.setTotalQuantityLimitType(1);
        // soldNum不匹配
        gpsPromotionVo.setSoldNum(1L);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_SOLD_NUM_NOT_MATCH.equals(resultVo.getErrorCode()));

        // 限每日
        gpsPromotionVo.setLimitType(1);
        landingProductVo.setTotalQuantityLimitType(2);
        // 活动开始时间相同，soldNum不匹配
        gpsPromotionVo.setSoldNum(1L);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_SOLD_NUM_NOT_MATCH.equals(resultVo.getErrorCode()));

        // landingProductVo活动开始时间小于gpsPromotion活动开始时间，表示gpsPromotion是未来的促销，活动已售数量为0
        startTime.set(Calendar.DATE, 3);
        gpsPromotionVo.setStartTime(startTime.getTime());
        gpsPromotionVo.setSoldNum(0L);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNull(resultVo);
        gpsPromotionVo.setSoldNum(1L);
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNotNull(resultVo);
        assertTrue(ScheduleConstants.LP_COMPARE_ERROR_CODE_SOLD_NUM_NOT_MATCH.equals(resultVo.getErrorCode()));

        // landingProductVo活动开始时间大于gpsPromotion活动开始时间，landingProductVo已重置soldNum，不校验活动已售数量，返回null
        startTime.set(Calendar.DATE, 4);
        landingProductVo.setStartTime(startTime.getTime());
        resultVo = LPCompareHandler.handle(landingProductVo, gpsPromotionVo);
        assertNull(resultVo);

        ScheduleContext.reset();
    }
}
