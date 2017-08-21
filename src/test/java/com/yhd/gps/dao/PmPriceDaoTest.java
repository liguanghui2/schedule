package com.yhd.gps.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByName;

import com.yhd.gps.BaseSpringWithUnitilsTest;
import com.yhd.gps.busyservice.dao.PmPriceDao;
import com.yhd.gps.schedule.vo.PmPriceVo;

/**
 * @author:liguanghui1
 * @date ：2017-4-10 下午04:20:33
 */
public class PmPriceDaoTest extends BaseSpringWithUnitilsTest {

    @SpringBeanByName
    private PmPriceDao pmPriceDao;

    @Test
    @DataSet({"/data/dao/PM_PRICE.xml" })
    @TestDataSource("public")
    public void getPmInfoIdsFromPmPriceForOfflineDataTest01() {
        int shardingIndex = 1;
        Long offset = 0L;
        List<PmPriceVo> result = pmPriceDao.getPmInfoIdsFromPmPriceForOfflineData(shardingIndex, offset, null);
        assertNotNull(result);
        assertTrue(result.get(0).getPmId().compareTo(1234L) == 0);
    }
    
    @Test
    @DataSet({"/data/dao/PM_PRICE.xml" })
    @TestDataSource("public")
    public void getPmPricesByProductIdsTest(){
        List<Long> productIds = new ArrayList<Long>();
        productIds.add(1L);
        productIds.add(2L);
        List<PmPriceVo> result = pmPriceDao.getPmPricesByProductIds(productIds);
        assertNotNull(result);
    }
}
