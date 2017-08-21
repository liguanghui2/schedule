package com.yhd.gps.schedule.jd;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.busyservice.service.impl.JDBookSyncServiceImpl;
import com.yhd.gps.schedule.vo.JDBookMessageVo;

public class JDBookSyncServiceTest extends BaseSpringTest {

	@Autowired
    private JDBookSyncServiceImpl jdBookSyncService;
    private BusyPmInfoDao busyPmInfoDao;

    @Before
    public void onSetUp() throws Exception {
        super.onSetUp();
        busyPmInfoDao = mock(BusyPmInfoDao.class);
        jdBookSyncService.setBusyPmInfoDao(busyPmInfoDao);
    }

    @Test
    public void getJDBookInfoByshardingIndex() {
        List<JDBookMessageVo> jdBookMessageVos = new ArrayList<JDBookMessageVo>();
        JDBookMessageVo jdBookMessageVo = new JDBookMessageVo();
        jdBookMessageVo.setOuterId("1234");
        jdBookMessageVo.setPmId(1234L);
        jdBookMessageVo.setProductId(1234L);
        jdBookMessageVos.add(jdBookMessageVo);
        when(busyPmInfoDao.getJDBookSyncInfoByShardingIndex(Mockito.anyInt(), Mockito.anyLong(), Mockito.anyList()))
                .thenReturn(jdBookMessageVos);
        Integer shardingIndex = 1;
        Long offset = 0L;
        Long merchantId = 1L;
        List<Long> merchantIds = new ArrayList<Long>(1);
        merchantIds.add(merchantId);
        List<JDBookMessageVo> result = jdBookSyncService.getJDBookInfoByshardingIndex(shardingIndex, offset,
                merchantIds);
        assertNotNull(result);
        assertTrue(result.get(0).getPmId() == 1234L);
    }
}
