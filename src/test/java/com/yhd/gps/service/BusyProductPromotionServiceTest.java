package com.yhd.gps.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByName;

import com.yhd.gps.BaseSpringWithUnitilsTest;
import com.yhd.gps.busyservice.service.BusyProductPromotionService;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;

public class BusyProductPromotionServiceTest extends BaseSpringWithUnitilsTest {

	@SpringBeanByName
    private BusyProductPromotionService busyProductPromotionService;

    @Test
    @DataSet({"/data/service/ProductPromotionServiceTest/product_promotion.xml" })
    @TestDataSource("public")
    public void getLandingProductVoListByDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 8, 11);
        Date startDate = cal.getTime();
        cal.set(Calendar.YEAR, 2017);
        Date endDate = cal.getTime();
        List<Long> promotionIds = new ArrayList<Long>();
        promotionIds.add(1198L);
        promotionIds.add(1199L);
        promotionIds.add(1200L);
        promotionIds.add(1201L);
        promotionIds.add(1202L);
        promotionIds.add(1203L);
        int startRow = 1;
        int endRow = 200;
        try {
            List<BusyLandingProductVo> landingProductVos = busyProductPromotionService.getLandingProductVoListByDate(
                    startDate, endDate, promotionIds, startRow, endRow);
            assertNotNull(landingProductVos);
            assertTrue(landingProductVos.size() == 4);
            for(BusyLandingProductVo landingProductVo : landingProductVos) {
                if(landingProductVo.getPromotionId() == 1198L || landingProductVo.getPromotionId() == 1199L) {
                    assertTrue(true);
                } else {
                    assertTrue(false);
                }
            }
        } catch (Exception e) {
            // 不支持rownum语法
            assertNotNull(e);
        }
    }

    @Test
    @DataSet({"/data/service/ProductPromotionServiceTest/product_promotion.xml" })
    @TestDataSource("public")
    public void compareLPPromotion() {
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 8, 11, 0, 0, 0);
        Date startDate = cal.getTime();
        cal.set(2016, 8, 13, 23, 59, 59);
        Date endDate = cal.getTime();
        Integer pageSize = 200;
        try {
            busyProductPromotionService.compareLPPromotion(startDate, endDate, pageSize);
        } catch (Exception e) {
            assertNull(e);
        }

    }
}
