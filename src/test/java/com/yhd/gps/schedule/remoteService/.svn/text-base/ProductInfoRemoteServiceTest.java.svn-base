package com.yhd.gps.schedule.remoteService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.yhd.gps.schedule.remoteService.impl.ProductInfoRemoteServiceImpl;
import com.yhd.pss.spi.common.vo.Response;
import com.yhd.pss.spi.merchant.service.QueryMerchantRemoteService;
import com.yhd.pss.spi.merchant.vo.SimpleMerchant;
import com.yhd.pss.spi.merchant.vo.input.QueryAllCityMerchantsRequest;
import com.yhd.pss.spi.pminfo.service.QueryPmInfoRemoteService;
import com.yhd.pss.spi.pminfo.vo.FullFieldsPmInfoWithProductVo;
import com.yhd.pss.spi.pminfo.vo.PmInfo;
import com.yhd.pss.spi.pminfo.vo.input.QueryBasePmInfoByMerchantIdAndProductIdsRequest;
import com.yhd.pss.spi.pminfo.vo.input.QueryPmInfoWithProductByMerchantIdRequest;
import com.yhd.shareservice.exceptions.HedwigException;
import com.yihaodian.pss.client.PssClient;

/**
 * @author sunfei
 * @CreateTime 2017-7-2 下午11:35:28
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PssClient.class })
public class ProductInfoRemoteServiceTest {
    private ProductInfoRemoteServiceImpl productInfoRemoteService = new ProductInfoRemoteServiceImpl();

    private PssClient pssClient;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(PssClient.class);
        pssClient = Mockito.mock(PssClient.class);
        Mockito.when(PssClient.getInstance(Mockito.anyLong(), Mockito.anyString())).thenReturn(pssClient);
    }

    @Test
    public void testQueryAllCityMerchantIds() {
        try {
            QueryMerchantRemoteService merchantRemoteService = Mockito.mock(QueryMerchantRemoteService.class);
            Mockito.when(pssClient.getQueryMerchantRemoteService()).thenReturn(merchantRemoteService);
            Response<List<SimpleMerchant>> response = new Response<List<SimpleMerchant>>();
            List<SimpleMerchant> list = new ArrayList<SimpleMerchant>();
            SimpleMerchant simpleMerchant = new SimpleMerchant();
            simpleMerchant.setId(1L);
            list.add(simpleMerchant);
            response.setResult(list);
            Mockito.when(
                    merchantRemoteService.queryAllCityMerchants((QueryAllCityMerchantsRequest) Mockito.anyObject()))
                    .thenReturn(response);
            productInfoRemoteService.queryAllCityMerchantIds();
            Mockito.when(
                    merchantRemoteService.queryAllCityMerchants((QueryAllCityMerchantsRequest) Mockito.anyObject()))
                    .thenThrow(new HedwigException(null, null, null, null));
            productInfoRemoteService.queryAllCityMerchantIds();
        } catch (Exception e) {
        }
    }

    @Test
    public void testQueryPmInfosByMerchantId() {
        try {
            // case1 入参为空
            productInfoRemoteService.queryPmInfosByMerchantId(null, null, null);
            // case2
            QueryPmInfoRemoteService pmInfoRemoteService = Mockito.mock(QueryPmInfoRemoteService.class);
            Mockito.when(pssClient.getQueryPmInfoRemoteService()).thenReturn(pmInfoRemoteService);
            Response<List<FullFieldsPmInfoWithProductVo>> response = new Response<List<FullFieldsPmInfoWithProductVo>>();
            List<FullFieldsPmInfoWithProductVo> list = new ArrayList<FullFieldsPmInfoWithProductVo>();
            response.setResult(list);
            Mockito.when(
                    pmInfoRemoteService
                            .queryPmInfoWithProductByMerchantId((QueryPmInfoWithProductByMerchantIdRequest) Mockito
                                    .anyObject())).thenReturn(response);
            productInfoRemoteService.queryPmInfosByMerchantId(1L, 1L, 100L);
            // case3
            Mockito.when(
                    pmInfoRemoteService
                            .queryPmInfoWithProductByMerchantId((QueryPmInfoWithProductByMerchantIdRequest) Mockito
                                    .anyObject())).thenThrow(new HedwigException(null, null, null, null));
            productInfoRemoteService.queryPmInfosByMerchantId(1L, 1L, 100L);
        } catch (Exception e) {
        }
    }

    @Test
    public void testGetPmInfoByMerchantIdAndProductIds() {
        try {
            // case1 入参为空
            productInfoRemoteService.getPmInfoByMerchantIdAndProductIds(null, null);
            // case2
            List<Long> productIds = new ArrayList<Long>();
            productIds.add(1L);
            QueryPmInfoRemoteService pmInfoRemoteService = Mockito.mock(QueryPmInfoRemoteService.class);
            Mockito.when(pssClient.getQueryPmInfoRemoteService()).thenReturn(pmInfoRemoteService);
            Response<List<PmInfo>> remoteResult = new Response<List<PmInfo>>();
            List<PmInfo> list = new ArrayList<PmInfo>();
            PmInfo pmInfo = new PmInfo();
            list.add(pmInfo);
            remoteResult.setResult(list);
            Mockito.when(
                    pmInfoRemoteService
                            .queryBasePmInfoByMerchantIdAndProductIds((QueryBasePmInfoByMerchantIdAndProductIdsRequest) Mockito
                                    .anyObject())).thenReturn(remoteResult);
            productInfoRemoteService.getPmInfoByMerchantIdAndProductIds(1L, productIds);
            // case3
            Mockito.when(
                    pmInfoRemoteService
                            .queryBasePmInfoByMerchantIdAndProductIds((QueryBasePmInfoByMerchantIdAndProductIdsRequest) Mockito
                                    .anyObject())).thenThrow(new HedwigException(null, null, null, null));
            productInfoRemoteService.getPmInfoByMerchantIdAndProductIds(1L, productIds);
        } catch (Exception e) {
        }
    }

}
