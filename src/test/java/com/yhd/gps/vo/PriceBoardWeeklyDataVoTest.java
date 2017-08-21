package com.yhd.gps.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.yhd.gps.schedule.vo.PriceBoardWeeklyDataVo;

/**
 * @author sunfei
 * @CreateTime 2017-7-4 下午01:47:43
 */
public class PriceBoardWeeklyDataVoTest {
    @Test
    public void test() {
        PriceBoardWeeklyDataVo vo = new PriceBoardWeeklyDataVo();
        vo.setCreateTime(new Date());
        vo.setId(1L);
        vo.setMaxPrice(new BigDecimal(10));
        vo.setMaxPriceDuration(new BigDecimal(10));
        vo.setMaxPriceFrequency(1);
        vo.setMaxPriceRuleType(0);
        vo.setMerchantId(1L);
        vo.setMinPrice(new BigDecimal(10));
        vo.setMinPriceDuration(new BigDecimal(10));
        vo.setMinPriceFrequency(1);
        vo.setMinPriceRuleType(0);
        vo.setModePrice(new BigDecimal(10));
        vo.setModePriceDuration(new BigDecimal(10));
        vo.setModePriceFrequency(1);
        vo.setModePriceRuleType(0);
        vo.setPmInfoId(1001L);
        vo.setProductId(1001L);
        vo.setWeekEndDate(new Date());
        vo.setWeekNum(1);
        vo.setWeekStartDate(new Date());
        vo.setYear("2017");

        vo.getCreateTime();
        vo.getId();
        vo.getMaxPrice();
        vo.getMaxPriceDuration();
        vo.getMaxPriceFrequency();
        vo.getMaxPriceRuleType();
        vo.getMerchantId();
        vo.getMinPrice();
        vo.getMinPriceDuration();
        vo.getMinPriceFrequency();
        vo.getMinPriceRuleType();
        vo.getModePrice();
        vo.getModePriceDuration();
        vo.getModePriceFrequency();
        vo.getModePriceRuleType();
        vo.getModePrice();
        vo.getModePriceDuration();
        vo.getModePriceFrequency();
        vo.getModePriceRuleType();
        vo.getPmInfoId();
        vo.getProductId();
        vo.getWeekEndDate();
        vo.getWeekNum();
        vo.getWeekStartDate();
        vo.getYear();
        vo.toString();
    }

}
