package com.yhd.gps.job;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.job.GpsProductSyncJob;

/**
 * 产品同步
 * @Author liuxiangrong
 * @CreateTime 2016-4-13 下午07:10:24
 */
public class GpsProductSyncJobTest extends BaseSpringTest {

    @Autowired
    private GpsProductSyncJob gpsProductSyncJob;

    @Autowired
    private BusyMiscConfigDao busyMiscConfigDao;

    @Before
    public void onSetUp() throws Exception {
        BusyMiscConfigVo busyMiscConfigVo = busyMiscConfigDao
                .getBusyMiscConfigVoByKey(ScheduleConstants.GPS_PRODUCT_SYNC_TIME);
        busyMiscConfigVo.setItemValue(ScheduleDateUtils.format(ScheduleDateUtils.addMinutes(new Date(), -30)));
        busyMiscConfigDao.updateBusyMiscConfigVo(busyMiscConfigVo);
    }

    @Test
    public void test() {
        gpsProductSyncJob.execute();
    }
}