package com.yhd.gps.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.common.ScheduleCacheKeyUtil;
import com.yihaodian.busy.vo.BusyAreaVo;

/**
 * @author:liguanghui1
 * @date ：2016-8-30 下午03:01:51
 */

@SuppressWarnings("deprecation")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ScheduleCacheKeyUtilTest extends AbstractDependencyInjectionSpringContextTests {

    Long pmInfoId = 123L;
    Long channelId = 101L;
    Long productId = 1234L;

    @Test
    public void getBusyPriceChangeMsgCacheKeyTest() {
        String result = ScheduleCacheKeyUtil.getBusyPriceChangeMsgCacheKey(pmInfoId, channelId, null);
        assertTrue("GPS_BUSY_PRICE_CHANGE_MSG_BY_PMINFOID_AND_CHANNELID_AND_AREA_123_101_0_0_0".equals(result));
        
        BusyAreaVo areaVo = new BusyAreaVo();
        result = ScheduleCacheKeyUtil.getBusyPriceChangeMsgCacheKey(pmInfoId, channelId, areaVo);
        assertTrue("GPS_BUSY_PRICE_CHANGE_MSG_BY_PMINFOID_AND_CHANNELID_AND_AREA_123_101_0_0_0".equals(result));
        
        areaVo.setProvinceId(1L);
        areaVo.setCityId(37L);
        result = ScheduleCacheKeyUtil.getBusyPriceChangeMsgCacheKey(pmInfoId, channelId, areaVo);
        assertTrue("GPS_BUSY_PRICE_CHANGE_MSG_BY_PMINFOID_AND_CHANNELID_AND_AREA_123_101_1_37_0".equals(result));
    }

    @Test
    public void getGpsProductCacheKeyTest() {
        String result = ScheduleCacheKeyUtil.getGpsProductCacheKey(productId);
        assertTrue("BUSY_GPS_PRODUCT_KEY_PREFIX_1234".equals(result));
    }
}
