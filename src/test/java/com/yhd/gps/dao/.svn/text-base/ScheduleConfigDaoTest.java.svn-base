package com.yhd.gps.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;

public class ScheduleConfigDaoTest extends BaseSpringTest {
    
    @Autowired
    private BusyMiscConfigDao busyMiscConfigDao;

    @Test
    public void getBusyMiscConfigVoByKey() {
        BusyMiscConfigVo vo = busyMiscConfigDao.getBusyMiscConfigVoByKey(ScheduleConstants.GPS_PRODUCT_SYNC_TIME);
        assertNotNull(vo);
        System.out.println(vo.toString());
        JSON.toJSON(vo);
    }

    @Test
    public void updateBusyMiscConfigVo() {
        BusyMiscConfigVo busyMiscConfigVo = busyMiscConfigDao
                .getBusyMiscConfigVoByKey(ScheduleConstants.GPS_PRODUCT_SYNC_TIME);
        busyMiscConfigVo.setItemValue("2016-04-18 10:20:00");
        busyMiscConfigDao.updateBusyMiscConfigVo(busyMiscConfigVo);

        BusyMiscConfigVo vo = busyMiscConfigDao.getBusyMiscConfigVoByKey(ScheduleConstants.GPS_PRODUCT_SYNC_TIME);
        assertNotNull(vo);
        assertEquals(vo.getItemValue(), "2016-04-18 10:20:00");

        vo.setItemValue(ScheduleDateUtils.format(ScheduleDateUtils.addMinutes(new Date(), -30)));
        busyMiscConfigDao.updateBusyMiscConfigVo(vo);
    }

//    @Test
//    public void getScheduleMiscConfigVoByKey(){
//        ScheduleMiscConfigVo vo  = busyMiscConfigDao.getScheduleMiscConfigVoByKey(GPS_PRODUCT_SYNC_IPADDRESS);
//        assertNotNull(vo);
//        assertEquals(vo.getItemValue(), "null");
//    }
//
//    @Test
//    public void updateScheduleMiscConfigVoByIdAndUpdateTime(){
//        ScheduleMiscConfigVo busyMiscConfigVo = busyMiscConfigDao.getScheduleMiscConfigVoByKey(GPS_PRODUCT_SYNC_IPADDRESS);
//        busyMiscConfigVo.setItemValue("127.0.0.1");
//        busyMiscConfigDao.updateScheduleMiscConfigVoByIdAndUpdateTime(busyMiscConfigVo);
//        
//        BusyMiscConfigVo vo = busyMiscConfigDao.getBusyMiscConfigVoByKey(GPS_PRODUCT_SYNC_IPADDRESS);
//        assertNotNull(vo);
//        assertEquals(vo.getItemValue(), "127.0.0.1");
//
//        vo.setItemValue(GPS_PRODUCT_SYNC_IPADDRESS_NULL_VALUE);
//        busyMiscConfigDao.updateBusyMiscConfigVo(vo);
//    }
    
    @Test
    public void compareDate(){
        Date compareDate = ScheduleDateUtils.addMinutes(new Date(), -1 * 20);
        if(compareDate.after(ScheduleDateUtils.parseDate("2016-04-15 10:10:00"))) {
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }
}
