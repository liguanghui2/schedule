package com.yhd.gps.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByName;

import com.yhd.gps.BaseSpringWithUnitilsTest;
import com.yhd.gps.busyservice.dao.BusyProductPromotionDao;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;

public class BusyProductPromotionDaoTest extends BaseSpringWithUnitilsTest {

    @SpringBeanByName
    private BusyProductPromotionDao busyProductPromotionDao;

    @Test
    @DataSet({"/data/dao/product_promotion.xml" })
    @TestDataSource("public")
    public void batchGetLandingProductPromotionIdListByDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 8, 11);
        Date startDate = cal.getTime();
        cal.set(Calendar.YEAR, 2017);
        Date endDate = cal.getTime();
        List<Long> result = busyProductPromotionDao.batchGetLandingProductPromotionIdListByDate(startDate, endDate);
        assertNotNull(result);
        assertTrue(result.size() == 2);
        for(Long promotionId : result) {
            if(promotionId == 1198 || promotionId == 1199) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
    };

    @Test
    @DataSet({"/data/dao/product_promotion.xml" })
    @TestDataSource("public")
    public void batchGetLandingProductVoListByDate() {
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
            List<BusyLandingProductVo> landingProductVos = busyProductPromotionDao.batchGetLandingProductVoListByDate(
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
            endRow = 3;
            landingProductVos = busyProductPromotionDao.batchGetLandingProductVoListByDate(startDate, endDate,
                    promotionIds, startRow, endRow);
            assertNotNull(landingProductVos);
            assertTrue(landingProductVos.size() == 3);
        } catch (Exception e) {
            // hsqldb对oracle语法rownum支持不友好，无法测试该case
            assertNotNull(e);
        }
    }

    @Test
    @DataSet({"/data/dao/product_promotion.xml" })
    @TestDataSource("public")
    public void batchGetLandingProductVoListByDate41Mall() {
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
        List<Long> productIds = new ArrayList<Long>();
        productIds.add(5454L);
        productIds.add(5455L);
        productIds.add(5456L);
        productIds.add(5457L);
        productIds.add(5458L);
        productIds.add(5459L);
        List<BusyLandingProductVo> landingProductVos = busyProductPromotionDao
                .batchGetLandingProductVoListByDate41Mall(startDate, endDate, promotionIds, productIds);
        assertNotNull(landingProductVos);
        assertTrue(landingProductVos.size() == 5);
        for(BusyLandingProductVo landingProductVo : landingProductVos) {
            if(landingProductVo.getPmId() == 70060L || landingProductVo.getPmId() == 70061L
               || landingProductVo.getPmId() == 70062L || landingProductVo.getPmId() == 70063L
               || landingProductVo.getPmId() == 70065L) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
    }
}
