package com.yhd.gps.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.service.OfflineDataService;
import com.yhd.gps.schedule.vo.PmPriceVo;

/**
 * @author:liguanghui1
 * @date ：2017-4-10 下午01:46:57
 */
public class OfflineDataServiceTest extends BaseSpringTest {

    @Autowired
    private OfflineDataService offlineDataService;

    @Test
    public void testGetPmInfoIdsFromPmPriceForOfflineData01() {
        int shardingIndex = 1;
        Long offset = 100L;
        List<PmPriceVo> result = offlineDataService.getPmInfoIdsFromPmPriceForOfflineData(shardingIndex, offset, null);
        assertNotNull(result);
    }

    @Test
    public void testGetPmInfoIdsFromPmPriceForOfflineData02() {
        int shardingIndex = 1;
        Long offset = null;
        List<PmPriceVo> result = offlineDataService.getPmInfoIdsFromPmPriceForOfflineData(shardingIndex, offset, null);
        assertTrue(result.size() == 0);
    }
}
