package com.yhd.gps.schedule.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.busyservice.service.CityPickPriceService;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yihaodian.backend.price.base.ResultDto;
import com.yihaodian.backend.price.cityPick.application.CityPickPriceApp;
import com.yihaodian.backend.price.cityPick.vo.CityPickPromCreateVo;
import com.yihaodian.backend.price.cityPick.vo.ConfirmedPricePromInputVo;
import com.yihaodian.backend.price.cityPick.vo.ConfirmedPriceVo;
import com.yihaodian.backend.price.cityPick.vo.QueryConfirmedPriceRequest;
import com.yihaodian.backend.price.cityPick.vo.ScheduleQueryVo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleVo;
import com.yihaodian.backend.price.common.BackendPriceHedwigBeanFactory;
import com.yihaodian.backend.price.common.BackendPriceHedwigServiceFactory;

/**
 * Created by zhangshuqiang on 2017/5/3.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BackendPriceHedwigServiceFactory.class, BackendPriceHedwigBeanFactory.class, CityPickPriceApp.class})
public class CityPickPromotionCommandTest {

    CityPickPromotionCommand cityPickPromotionCommand;

    CityPickPriceApp cityPickPriceApp;

    BusyMiscConfigDao busyMiscConfigDao;

    CityPickPriceService cityPickPriceService;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(BackendPriceHedwigBeanFactory.class);
        cityPickPriceApp = PowerMockito.mock(CityPickPriceApp.class);
        PowerMockito.when(BackendPriceHedwigBeanFactory.getBean("cityPickPriceApp")).thenReturn(cityPickPriceApp);
        PowerMockito.mockStatic(BackendPriceHedwigServiceFactory.class);
        PowerMockito.when(BackendPriceHedwigServiceFactory.getCityPickPriceApp()).thenReturn(cityPickPriceApp);
        busyMiscConfigDao = PowerMockito.mock(BusyMiscConfigDao.class);
        cityPickPriceService = PowerMockito.mock(CityPickPriceService.class);
        cityPickPromotionCommand = new CityPickPromotionCommand(5, new CountDownLatch(4),
                "CITY_PICK_COMMON_PROMOTION", busyMiscConfigDao, cityPickPriceService);
    }

    @Test
    public void doWork() throws Exception {
        List<ScheduleVo> bussinessDataes = new ArrayList<ScheduleVo>();
        ScheduleVo scheduleVo = new ScheduleVo();
        scheduleVo.setId(1l);
        scheduleVo.setMerchantId(1l);
        scheduleVo.setStartTime(new Date());
        scheduleVo.setEndTime(new Date());
        scheduleVo.setDataCollectedTime(new Date());
        bussinessDataes.add(scheduleVo);

        ResultDto<List<ConfirmedPriceVo>> resultDto1 = new ResultDto<List<ConfirmedPriceVo>>();
        resultDto1.setStatus(ResultDto.STATUS_SUCCESS);

        ResultDto<List<ConfirmedPriceVo>> resultDto = new ResultDto<List<ConfirmedPriceVo>>();
        resultDto.setStatus(ResultDto.STATUS_SUCCESS);

        QueryConfirmedPriceRequest request = new QueryConfirmedPriceRequest();
        request.setPriceScheduleId(1l);
        request.setMerchantId(1l);
        request.setPageSize(ScheduleConstants.PAGE_SIZE);
        request.setPriceStatus(ScheduleConstants.PRICE_STATUS_CHECK_PENDING);
        request.setCurrentPage(2l);

        PowerMockito.when(cityPickPriceApp.getConfirmedPrice(Mockito.argThat(new ArgumentMatcher<QueryConfirmedPriceRequest>() {
            @Override
            public boolean matches(Object o) {
                if(o != null)
                {
                    QueryConfirmedPriceRequest confirmedPriceRequest = (QueryConfirmedPriceRequest) o;
                    return confirmedPriceRequest.getCurrentPage().longValue() == 2;
                } else {
                    return false;
                }

            }
        }))).thenReturn(resultDto1);

        List<ConfirmedPriceVo> confirmedPriceVos = new ArrayList<ConfirmedPriceVo>();
        ConfirmedPriceVo confirmedPriceVo = new ConfirmedPriceVo();
        confirmedPriceVo.setPmInfoId(1l);
        confirmedPriceVo.setPriceStatus(0);
        confirmedPriceVo.setId(1l);
        confirmedPriceVos.add(confirmedPriceVo);
        resultDto.setResult(confirmedPriceVos);
        request.setCurrentPage(1l);

        PowerMockito.when(cityPickPriceApp.getConfirmedPrice(Mockito.argThat(new ArgumentMatcher<QueryConfirmedPriceRequest>() {
            @Override
            public boolean matches(Object o) {
                if(o != null)
                {
                    QueryConfirmedPriceRequest confirmedPriceRequest = (QueryConfirmedPriceRequest) o;
                    return confirmedPriceRequest.getCurrentPage().longValue() == 1;
                } else {
                    return false;
                }

            }
        }))).thenReturn(resultDto);

        List<CityPickPromCreateVo> cityPickPromCreateVoList = new ArrayList<CityPickPromCreateVo>();
        CityPickPromCreateVo cityPickPromCreateVo = new CityPickPromCreateVo();
        cityPickPromCreateVo.setPmInfoId(1l);
        cityPickPromCreateVo.setResultCode(ScheduleConstants.RESULT_SUCCESS_VALID);
        cityPickPromCreateVo.setPromId(1l);
        cityPickPromCreateVoList.add(cityPickPromCreateVo);
        PowerMockito.when(cityPickPriceApp.createCommonPromotion(Mockito.any(ConfirmedPricePromInputVo.class))).thenReturn(cityPickPromCreateVoList);

        cityPickPromotionCommand.doWork(bussinessDataes);
    }

    @Test
    public void fetchBussinessDataes() throws Exception {
        ResultDto<List<ScheduleVo>> resultDto = new ResultDto<List<ScheduleVo>>();
        resultDto.setStatus(ResultDto.STATUS_SUCCESS);
        List<ScheduleVo> scheduleVos = new ArrayList<ScheduleVo>();
        ScheduleVo scheduleVo = new ScheduleVo();
        scheduleVo.setId(1l);
        scheduleVo.setDataCollectedTime(ScheduleDateUtils.addDays(new Date(), -3));
        scheduleVos.add(scheduleVo);
        resultDto.setResult(scheduleVos);

        Mockito.when(cityPickPriceApp.getCityPickPriceSchedule4Schedule(Mockito.any(ScheduleQueryVo.class))).thenReturn(resultDto);

        BusyMiscConfigVo busyMiscConfigVo = new BusyMiscConfigVo();
        busyMiscConfigVo.setItemValue("2");
        Mockito.when(busyMiscConfigDao.getBusyMiscConfigVoByKey(Mockito.anyString())).thenReturn(busyMiscConfigVo);

        cityPickPromotionCommand.fetchBussinessDataes(4);
    }
}