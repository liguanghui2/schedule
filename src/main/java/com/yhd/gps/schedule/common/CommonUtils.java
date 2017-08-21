package com.yhd.gps.schedule.common;

import java.util.ArrayList;
import java.util.List;

import com.yhd.pss.spi.common.vo.Response;
import com.yhd.pss.spi.merchant.vo.FullFieldsMerchant;
import com.yhd.pss.spi.merchant.vo.input.QueryMerchantByIdRequest;
import com.yihaodian.pss.client.PssClient;
import com.yihaodian.pss.client.PssClientConfiguration;

/**
 * ---- 请加注释 ------
 * @Author  shengxu1
 * @CreateTime  2017-5-19 下午07:28:12
 */
public class CommonUtils {
    
    private static final long PSS_TIMEOUIT = 3000;
    private static final String GROUP_NAME = PssClientConfiguration.BACKEND_SERVER_GROUP;

    public static String queryMerchantName(Long merchantId) {
        String merchantName = null;
        QueryMerchantByIdRequest merchantByIdRequest = new QueryMerchantByIdRequest();
        merchantByIdRequest.setMerchantId(merchantId);
        try {
            Response<FullFieldsMerchant> remoteResult = PssClient.getInstance(PSS_TIMEOUIT,GROUP_NAME).getQueryMerchantRemoteService().
                    queryMerchantById(merchantByIdRequest);
            if (remoteResult != null && remoteResult.getResult() != null) {
                FullFieldsMerchant fullFieldsMerchant = remoteResult.getResult();
                merchantName = fullFieldsMerchant.getMerchantName();
            }
        } catch (Exception ex) {
        }
        return merchantName;
    }

    /**
     * 将一个list按照partSize分块
     * @param <T>
     * @param list
     * @param partSize
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int partSize) {
        List<List<T>> result = new ArrayList<List<T>>();
        int orgSize = list.size();

        int partNum = orgSize / partSize;
        if(orgSize != partSize * partNum) {
            ++partNum;
        }
        for(int i = 0; i < partNum; i++) {
            int from = i * partSize;
            int to = Math.min((i + 1) * partSize, orgSize);
            result.add(list.subList(from, to));
        }
        return result;
    }

}
