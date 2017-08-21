package com.yhd.gps.schedule.jd;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import com.yhd.gps.busy.mail.BusyMailService;
import com.yhd.gps.busy.mail.BusyMailUtil;
import com.yhd.gps.busyservice.dao.JDBookSyncLogDao;
import com.yhd.gps.busyservice.service.impl.JDBookSyncServiceImpl;
import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.SpringBeanUtil;
import com.yhd.gps.schedule.vo.JDBookMessageVo;
import com.yhd.pss.spi.baseinfo.service.ModifyProductRemoteService;
import com.yhd.pss.spi.baseinfo.vo.input.ProductListPriceAndProductIdRequest;
import com.yhd.pss.spi.common.vo.Response;
import com.yihaodian.front.busystock.client.BusySpringBeanUtil;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.client.BusystockClientConfigurerPlugin;
import com.yihaodian.front.busystock.client.facade.BusyPriceFacadeService;
import com.yihaodian.openapi.dto.jd.hessian.request.QueryJdWarePPriceGetRequest;
import com.yihaodian.openapi.dto.jd.hessian.response.JdPriceChange;
import com.yihaodian.openapi.dto.jd.hessian.response.JdWarePPriceGetResponse;
import com.yihaodian.openapi.dto.jd.hessian.response.QueryJdWarePPriceGetResponse;
import com.yihaodian.openapi.service.jd.util.JdJosApiServiceUtil;
import com.yihaodian.pss.client.PssClient;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JdJosApiServiceUtil.class, BusystockClientConfigurerPlugin.class, ApplicationContext.class,
                 BusySpringBeanUtil.class, BusyStockClientUtil.class, SpringBeanUtil.class, PoolUtils.class,
                 BusyMailUtil.class, PssClient.class })
public class JDBookSyncServiceUsePowerMockTest {

    private JDBookSyncServiceImpl jdBookSyncService = new JDBookSyncServiceImpl();
    private JdJosApiServiceUtil jdJosApiServiceUtil;
    private BusyPriceFacadeService busyPriceFacadeService;
    private JDBookSyncLogDao jdBookSyncLogDao;

    @Before
    public void onSetUp() throws Exception {
        jdBookSyncLogDao = PowerMockito.mock(JDBookSyncLogDao.class);
        jdBookSyncService.setJdBookSyncLogDao(jdBookSyncLogDao);
        /** open-api **/
        PowerMockito.mockStatic(JdJosApiServiceUtil.class);
        jdJosApiServiceUtil = mock(JdJosApiServiceUtil.class);
        PowerMockito.when(JdJosApiServiceUtil.getInstance()).thenReturn(jdJosApiServiceUtil);
        /** open-api **/

        /** BS模块开始 **/
        PowerMockito.mockStatic(BusystockClientConfigurerPlugin.class);
        ApplicationContext ac = PowerMockito.mock(ApplicationContext.class);
        PowerMockito.when(BusystockClientConfigurerPlugin.getApplicationContext()).thenReturn(ac);
        PowerMockito.mockStatic(BusySpringBeanUtil.class);
        busyPriceFacadeService = PowerMockito.mock(BusyPriceFacadeService.class);
        PowerMockito.when(BusySpringBeanUtil.getBean("busyPriceFacadeService")).thenReturn(busyPriceFacadeService);
        PowerMockito.mockStatic(BusyStockClientUtil.class);
        PowerMockito.when(BusyStockClientUtil.getBusyPriceFacadeService()).thenReturn(busyPriceFacadeService);
        /** BS模块结束 */

        /** pss **/
        PowerMockito.mockStatic(PssClient.class);
        PssClient pssClient = mock(PssClient.class);
        PowerMockito.when(PssClient.getInstance(3000L,ScheduleConstants.GROUP_NAME)).thenReturn(pssClient);
        ModifyProductRemoteService modifyProductRemoteService = mock(ModifyProductRemoteService.class);
        PowerMockito.when(pssClient.getModifyProductRemoteService()).thenReturn(modifyProductRemoteService);
        Response<Integer> response = new Response<Integer>();
        when(
                modifyProductRemoteService
                        .updateProductListPriceByProductId((ProductListPriceAndProductIdRequest) Mockito.anyObject()))
                .thenReturn(response);
        /** pss **/

        PowerMockito.mockStatic(SpringBeanUtil.class);
        BeanFactory ctx = Mockito.mock(BeanFactory.class);
        Field ctxField = ReflectionUtils.findField(SpringBeanUtil.class, "ctx");
        ReflectionUtils.makeAccessible(ctxField);
        ReflectionUtils.setField(ctxField, null, ctx);
        PowerMockito.when(SpringBeanUtil.getBean("jdBookSyncService")).thenReturn(jdBookSyncService);
        PowerMockito.spy(PoolUtils.class);
        PowerMockito.doReturn("gps-schedule").when(PoolUtils.class, "computePoolName");
        PowerMockito.mockStatic(BusyMailUtil.class);
        BusyMailService busyMailService = mock(BusyMailService.class);
        PowerMockito.when(SpringBeanUtil.getBean("busyMailService")).thenReturn(busyMailService);
    }

    @Test
    public void syncJDBookPrice() {
        List<JDBookMessageVo> jdBookMessageVos = new ArrayList<JDBookMessageVo>();
        String remark = null;
        // 参数校验
        try {
            int result = jdBookSyncService.syncJDBookPrice(jdBookMessageVos, remark);
            assertTrue(result == 0);
        } catch (Exception e) {
            assertTrue(false);
        }

        // 京东出参为空，走异常流程
        JDBookMessageVo jdBookMessageVo = new JDBookMessageVo();
        jdBookMessageVo.setOuterId("1234");
        jdBookMessageVo.setPmId(1234L);
        jdBookMessageVo.setProductId(1234L);
        jdBookMessageVos.add(jdBookMessageVo);
        QueryJdWarePPriceGetResponse response = new QueryJdWarePPriceGetResponse();
        response.setResponseCode(JDBookSyncMessageConsumer.OPEN_API_SUCCESS);
        JdWarePPriceGetResponse jdWarePPriceGetResponse = new JdWarePPriceGetResponse();
        jdWarePPriceGetResponse.setCode(JDBookSyncMessageConsumer.JD_SUCCESS);
        List<JdPriceChange> jdPriceChangeList = new ArrayList<JdPriceChange>();
        jdWarePPriceGetResponse.setJdPriceChangeList(jdPriceChangeList);
        response.setJdWarePPriceGetResponse(jdWarePPriceGetResponse);
        when(jdJosApiServiceUtil.queryJdWarePPrices((QueryJdWarePPriceGetRequest) Mockito.anyObject())).thenReturn(
                response);

        when(busyPriceFacadeService.batchUpdatePmPriceVoList(Mockito.anyList())).thenReturn(1);
        when(jdBookSyncLogDao.batchInsertJDBookSyncLogVoList(Mockito.anyList())).thenReturn(1);

        try {
            int result = jdBookSyncService.syncJDBookPrice(jdBookMessageVos, remark);
            assertTrue(result == 0);
        } catch (Exception e) {
            assertTrue(false);
        }

        // 入参一个，京东出参一个，走正常流程
        JdPriceChange jdPriceChange = new JdPriceChange();
        jdPriceChange.setId("J_1234");
        jdPriceChange.setOp("10");
        jdPriceChange.setM("12");
        jdPriceChangeList.add(jdPriceChange);
        jdWarePPriceGetResponse.setJdPriceChangeList(jdPriceChangeList);
        response.setJdWarePPriceGetResponse(jdWarePPriceGetResponse);
        when(jdJosApiServiceUtil.queryJdWarePPrices((QueryJdWarePPriceGetRequest) Mockito.anyObject())).thenReturn(
                response);

        when(busyPriceFacadeService.batchUpdatePmPriceVoList(Mockito.anyList())).thenReturn(1);
        when(jdBookSyncLogDao.batchInsertJDBookSyncLogVoList(Mockito.anyList())).thenReturn(1);

        try {
            int result = jdBookSyncService.syncJDBookPrice(jdBookMessageVos, remark);
            assertTrue(result == 1);
        } catch (Exception e) {
            assertTrue(false);
        }

        // 入参两个，京东返回一个的场景
        JDBookMessageVo jdBookMessageVo2 = new JDBookMessageVo();
        jdBookMessageVo2.setOuterId("2345");
        jdBookMessageVo2.setPmId(2345L);
        jdBookMessageVo2.setProductId(2345L);
        jdBookMessageVos.add(jdBookMessageVo2);
        when(jdJosApiServiceUtil.queryJdWarePPrices((QueryJdWarePPriceGetRequest) Mockito.anyObject())).thenReturn(
                response);

        try {
            int result = jdBookSyncService.syncJDBookPrice(jdBookMessageVos, remark);
            assertTrue(result == 1);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
