package com.yhd.gps.busyservice.service.impl;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import com.yhd.gps.busy.mail.BusyMailUtil;
import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.SpringBeanUtil;
import com.yhd.pss.spi.common.vo.Response;
import com.yhd.pss.spi.merchant.service.QueryMerchantRemoteService;
import com.yhd.pss.spi.merchant.vo.FullFieldsMerchant;
import com.yhd.pss.spi.merchant.vo.input.QueryMerchantByIdRequest;
import com.yihaodian.backend.price.base.ResultDto;
import com.yihaodian.backend.price.cityPick.application.CityPickPriceApp;
import com.yihaodian.backend.price.cityPick.vo.EmailConfigVo;
import com.yihaodian.backend.price.cityPick.vo.EmailTypeVo;
import com.yihaodian.backend.price.cityPick.vo.QuerySchedulePriceRequest;
import com.yihaodian.backend.price.cityPick.vo.SchedulePriceInfo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleVo;
import com.yihaodian.backend.price.common.BackendPriceHedwigBeanFactory;
import com.yihaodian.backend.price.common.BackendPriceHedwigServiceFactory;
import com.yihaodian.front.busystock.client.BusySpringBeanUtil;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.client.BusystockClientConfigurerPlugin;
import com.yihaodian.front.busystock.client.facade.BusyPriceFacadeService;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;
import com.yihaodian.openapi.service.jd.util.JdJosApiServiceUtil;
import com.yihaodian.pss.client.PssClient;

/**
 * Created by zhangshuqiang on 2017/5/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JdJosApiServiceUtil.class, BusystockClientConfigurerPlugin.class, ApplicationContext.class,
        BusySpringBeanUtil.class, BusyStockClientUtil.class, SpringBeanUtil.class, PoolUtils.class,
        BusyMailUtil.class, PssClient.class,BackendPriceHedwigServiceFactory.class, BackendPriceHedwigBeanFactory.class, CityPickPriceApp.class })
public class CityPickPriceServiceImplTest {

    private CityPickPriceServiceImpl cityPickPriceService;

    private BusyPriceFacadeService busyPriceFacadeService;
    private ServletContext servletContext;
    private CityPickPriceApp cityPickPriceApp;
    private QueryMerchantRemoteService queryMerchantRemoteService;

    @Before
    public void onSetUp() throws Exception {

        servletContext = PowerMockito.mock(ServletContext.class);

        /** BS **/
        PowerMockito.mockStatic(BusystockClientConfigurerPlugin.class);
        ApplicationContext ac = PowerMockito.mock(ApplicationContext.class);
        PowerMockito.when(BusystockClientConfigurerPlugin.getApplicationContext()).thenReturn(ac);
        PowerMockito.mockStatic(BusySpringBeanUtil.class);
        busyPriceFacadeService = PowerMockito.mock(BusyPriceFacadeService.class);
        PowerMockito.when(BusySpringBeanUtil.getBean("busyPriceFacadeService")).thenReturn(busyPriceFacadeService);
        PowerMockito.mockStatic(BusyStockClientUtil.class);
        PowerMockito.when(BusyStockClientUtil.getBusyPriceFacadeService()).thenReturn(busyPriceFacadeService);

        /** pss **/
        PowerMockito.mockStatic(PssClient.class);
        PssClient pssClient = mock(PssClient.class);
        PowerMockito.when(PssClient.getInstance(3000L,ScheduleConstants.GROUP_NAME)).thenReturn(pssClient);
        queryMerchantRemoteService = mock(QueryMerchantRemoteService.class);
        PowerMockito.when(pssClient.getQueryMerchantRemoteService()).thenReturn(queryMerchantRemoteService);

        /** CityPickPriceApp **/
        PowerMockito.mockStatic(BackendPriceHedwigBeanFactory.class);
        cityPickPriceApp = PowerMockito.mock(CityPickPriceApp.class);
        PowerMockito.when(BackendPriceHedwigBeanFactory.getBean("cityPickPriceApp")).thenReturn(cityPickPriceApp);
        PowerMockito.mockStatic(BackendPriceHedwigServiceFactory.class);
        PowerMockito.when(BackendPriceHedwigServiceFactory.getCityPickPriceApp()).thenReturn(cityPickPriceApp);

        cityPickPriceService = new CityPickPriceServiceImpl();
        cityPickPriceService.setServletContext(servletContext);
    }

    @Test
    public void sendCityPickPriceEmail() throws Exception {
        //mock emailType
        ResultDto<List<EmailTypeVo>> emailTypeResult = new ResultDto<List<EmailTypeVo>>();
        emailTypeResult.setStatus(ResultDto.STATUS_SUCCESS);
        List<EmailTypeVo> emailTypeVos = new ArrayList<EmailTypeVo>();
        EmailTypeVo emailTypeVo = new EmailTypeVo();
        emailTypeVo.setEmailContent("test");
        emailTypeVo.setEmailType(3);
        emailTypeVo.setEmailSubject("test%s");
        emailTypeVo.setEmailTypeName("test");
        emailTypeVo.setId(1L);
        emailTypeVo.setAttachmentName("test");
        emailTypeVos.add(emailTypeVo);
        emailTypeResult.setResult(emailTypeVos);
        Mockito.when(cityPickPriceApp.findAllEmailTypes()).thenReturn(emailTypeResult);

        //mock email config
        ResultDto<List<EmailConfigVo>> emailConfigResult = new ResultDto<List<EmailConfigVo>>();
        emailConfigResult.setStatus(ResultDto.STATUS_SUCCESS);
        List<EmailConfigVo> emailConfigVos = new ArrayList<EmailConfigVo>();
        EmailConfigVo emailConfigVo = new EmailConfigVo();
        emailConfigVo.setId(1L);
        emailConfigVo.setEmailAddress("test@ydh.com");
        emailConfigVo.setEmailTypeId(1L);
        emailConfigVos.add(emailConfigVo);
        emailConfigResult.setResult(emailConfigVos);
        Mockito.when(cityPickPriceApp.findEmailConfigByMerchantId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(emailConfigResult);

        //mock query price list
        ResultDto<Long> countResult = new ResultDto<Long>();
        countResult.setStatus(ResultDto.STATUS_SUCCESS);
        countResult.setResult(1L);
        Mockito.when(cityPickPriceApp.countSchedulePriceList(Mockito.any(QuerySchedulePriceRequest.class))).thenReturn(countResult);

        ResultDto<List<SchedulePriceInfo>> schedulePriceResult = new ResultDto<List<SchedulePriceInfo>>();
        schedulePriceResult.setStatus(ResultDto.STATUS_SUCCESS);
        List<SchedulePriceInfo> schedulePriceInfos = new ArrayList<SchedulePriceInfo>();
        SchedulePriceInfo schedulePriceInfo = new SchedulePriceInfo();
        schedulePriceInfo.setMerchantId(1L);
        schedulePriceInfo.setPromRuleId(1L);
        schedulePriceInfo.setPriceScheduleId(1L);
        schedulePriceInfo.setCategorySearchName("0-5009-5034-5043:美容护理-缤纷彩妆-睫毛膏/睫毛增长液");
        schedulePriceInfos.add(schedulePriceInfo);
        schedulePriceResult.setResult(schedulePriceInfos);
        Mockito.when(cityPickPriceApp.querySchedulePriceList(Mockito.any(QuerySchedulePriceRequest.class))).thenReturn(schedulePriceResult);

        Mockito.when(servletContext.getRealPath(Mockito.anyString())).thenReturn("/test/");

        Response<FullFieldsMerchant> fullFieldsMerchantResponse = new Response<FullFieldsMerchant>();
        FullFieldsMerchant fullFieldsMerchant = new FullFieldsMerchant();
        fullFieldsMerchant.setId(1L);
        fullFieldsMerchant.setMerchantName("test");
        fullFieldsMerchantResponse.setResult(fullFieldsMerchant);
        Mockito.when(queryMerchantRemoteService.queryMerchantById(Mockito.any(QueryMerchantByIdRequest.class))).thenReturn(fullFieldsMerchantResponse);

        BSProductPromRuleVo bsProductPromRuleVo = new BSProductPromRuleVo();
        Mockito.when(busyPriceFacadeService.getProductPromRuleVoByRuleId(Mockito.anyLong())).thenReturn(bsProductPromRuleVo);

        ResultDto<Map<Long, ScheduleVo>> resMap = new ResultDto<Map<Long, ScheduleVo>>();
        resMap.setStatus(ResultDto.STATUS_SUCCESS);
        Map<Long, ScheduleVo> map = new HashMap<Long, ScheduleVo>();
        ScheduleVo scheduleVo = new ScheduleVo();
        scheduleVo.setId(1L);
        map.put(1L, scheduleVo);
        resMap.setResult(map);
        Mockito.when(cityPickPriceApp.getCityPickPriceScheduleByIds(Mockito.anyList())).thenReturn(resMap);

        ScheduleVo tested = new ScheduleVo();
        tested.setId(1L);
        tested.setMerchantId(1L);
        tested.setName("test");
        tested.setStartTime(new Date());
        tested.setEndTime(new Date());
        cityPickPriceService.sendCityPickPriceEmail(tested, 0);
    }

}