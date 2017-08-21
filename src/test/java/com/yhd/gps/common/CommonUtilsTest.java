package com.yhd.gps.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.yhd.gps.schedule.common.CommonUtils;
import com.yhd.pss.spi.common.vo.Response;
import com.yhd.pss.spi.merchant.service.QueryMerchantRemoteService;
import com.yhd.pss.spi.merchant.vo.FullFieldsMerchant;
import com.yhd.pss.spi.merchant.vo.input.QueryMerchantByIdRequest;
import com.yihaodian.pss.client.PssClient;

/**
 * @author sunfei
 * @CreateTime 2017-7-4 下午01:55:55
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PssClient.class })
public class CommonUtilsTest {
    private PssClient pssClient;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(PssClient.class);
        pssClient = Mockito.mock(PssClient.class);
        Mockito.when(PssClient.getInstance(Mockito.anyLong(), Mockito.anyString())).thenReturn(pssClient);
    }

    @Test
    public void testQueryMerchantName() {
        try {
            QueryMerchantRemoteService merchantRemoteService = Mockito.mock(QueryMerchantRemoteService.class);
            Mockito.when(pssClient.getQueryMerchantRemoteService()).thenReturn(merchantRemoteService);
            Response<FullFieldsMerchant> remoteResult = new Response<FullFieldsMerchant>();
            FullFieldsMerchant fullFieldsMerchant = new FullFieldsMerchant();
            remoteResult.setResult(fullFieldsMerchant);
            Mockito.when(merchantRemoteService.queryMerchantById((QueryMerchantByIdRequest) Mockito.anyObject()))
                    .thenReturn(remoteResult);
            CommonUtils.queryMerchantName(1L);
            Mockito.when(merchantRemoteService.queryMerchantById((QueryMerchantByIdRequest) Mockito.anyObject()))
                    .thenThrow(new Exception());
            CommonUtils.queryMerchantName(1L);
        } catch (Exception e) {
        }
    }

    @Test
    public void testSplitList() {
        List<Long> list = new ArrayList<Long>();
        for(Long i = 0L; i < 10L; i++) {
            list.add(i);
        }
        CommonUtils.splitList(list, 5);
    }

}
