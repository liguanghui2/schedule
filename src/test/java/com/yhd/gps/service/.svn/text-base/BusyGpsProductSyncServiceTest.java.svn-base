package com.yhd.gps.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.busyservice.service.BusyGpsProductSyncService;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;

public class BusyGpsProductSyncServiceTest extends BaseSpringTest {

    @Autowired
    private BusyGpsProductSyncService busyGpsProductSyncService;

    @Autowired
    private BusyMiscConfigDao busyMiscConfigDao;

    @Before
    public void onSetUp() throws Exception {
        BusyMiscConfigVo busyMiscConfigVo = busyMiscConfigDao
                .getBusyMiscConfigVoByKey(ScheduleConstants.GPS_PRODUCT_SYNC_TIME);
        busyMiscConfigVo.setItemValue(ScheduleDateUtils.format(ScheduleDateUtils.addMinutes(new Date(), -3)));
        busyMiscConfigDao.updateBusyMiscConfigVo(busyMiscConfigVo);
    }

    @Test
    public void compensateGpsProduct() {
        busyGpsProductSyncService.compensateGpsProduct();
    }

    @Test
    public void syncProductToGps() {
        Date endDate = new Date();
        busyGpsProductSyncService.syncProductToGps(ScheduleDateUtils.addMinutes(endDate, -3), endDate, 10L);
    }

    @Test
    public void compensateGpsProductByProductIds() {
        List<Long> productIdList = new ArrayList<Long>();
        productIdList.add(1L);
        productIdList.add(2L);
        busyGpsProductSyncService.compensateGpsProductByProductIds(productIdList);
    }

}
