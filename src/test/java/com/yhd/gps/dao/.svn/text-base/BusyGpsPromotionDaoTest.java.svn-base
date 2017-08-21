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
import com.yhd.gps.busyservice.dao.BusyGpsPromotionDao;
import com.yhd.gps.promotion.vo.GPSPromotionVo;

public class BusyGpsPromotionDaoTest extends BaseSpringWithUnitilsTest {

    @SpringBeanByName
    private BusyGpsPromotionDao busyGpsPromotionDao;

    @Test
    @DataSet({"/data/dao/gps_promotion.xml" })
    @TestDataSource("public")
    public void batchGetGpsPromotionIdListByDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 8, 11);
        Date startDate = cal.getTime();
        cal.set(Calendar.YEAR, 2017);
        Date endDate = cal.getTime();
        List<Long> result = busyGpsPromotionDao.batchGetGpsPromotionIdListByDate(startDate, endDate);
        assertNotNull(result);
        assertTrue(result.size() == 3);
        for(Long promotionId : result) {
            if(promotionId == 1 || promotionId == 2 || promotionId == 3) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
    };

    @Test
    @DataSet({"/data/dao/gps_promotion.xml" })
    @TestDataSource("public")
    public void batchGetGpsPromotionVoByPromotionIdsAndPmIds() {
        List<Long> promotionIds = new ArrayList<Long>();
        promotionIds.add(1L);
        promotionIds.add(2L);
        promotionIds.add(3L);
        promotionIds.add(4L);
        List<Long> pmIds = new ArrayList<Long>();
        pmIds.add(7600L);
        pmIds.add(7601L);
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 8, 11);
        Date startDate = cal.getTime();
        cal.set(Calendar.YEAR, 2017);
        Date endDate = cal.getTime();
        List<GPSPromotionVo> promotionVos = busyGpsPromotionDao.batchGetGpsPromotionVoByPromotionIdsAndPmIds(
                promotionIds, pmIds, startDate, endDate);
        assertNotNull(promotionVos);
        assertTrue(promotionVos.size() == 3);
        for(GPSPromotionVo promotionVo : promotionVos) {
            if(promotionVo.getId() == 1L || promotionVo.getId() == 2L || promotionVo.getId() == 3L) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
    }

}
