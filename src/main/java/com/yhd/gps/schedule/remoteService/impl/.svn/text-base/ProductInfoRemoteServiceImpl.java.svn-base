package com.yhd.gps.schedule.remoteService.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.yhd.god.common.GODConstants;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.remoteService.ProductInfoRemoteService;
import com.yhd.pss.spi.common.vo.Response;
import com.yhd.pss.spi.merchant.vo.SimpleMerchant;
import com.yhd.pss.spi.merchant.vo.input.QueryAllCityMerchantsRequest;
import com.yhd.pss.spi.pminfo.vo.FullFieldsPmInfoWithProductVo;
import com.yhd.pss.spi.pminfo.vo.PmInfo;
import com.yhd.pss.spi.pminfo.vo.input.QueryBasePmInfoByMerchantIdAndProductIdsRequest;
import com.yhd.pss.spi.pminfo.vo.input.QueryPmInfoWithProductByMerchantIdRequest;
import com.yhd.shareservice.exceptions.HedwigException;
import com.yihaodian.pss.client.PssClient;

/**
 * @author sunfei
 * @CreateTime 2017-6-29 下午02:02:44
 */
public class ProductInfoRemoteServiceImpl implements ProductInfoRemoteService {
    private Logger logger = Logger.getLogger(ProductInfoRemoteServiceImpl.class);

    @Override
    public List<Long> queryAllCityMerchantIds() {
        List<Long> merchantIds = new ArrayList<Long>();
        try {
            Response<List<SimpleMerchant>> response = PssClient
                    .getInstance(ScheduleConstants.PSS_TIME_OUT, ScheduleConstants.GROUP_NAME)
                    .getQueryMerchantRemoteService().queryAllCityMerchants(new QueryAllCityMerchantsRequest());
            if(response != null && response.getResult() != null) {
                for(Iterator<SimpleMerchant> iterator = response.getResult().iterator(); iterator.hasNext();) {
                    SimpleMerchant m = iterator.next();
                    if(null != m && null != m.getId()) {
                        merchantIds.add(m.getId());
                    }
                }
            }
        } catch (HedwigException e) {
            logger.error("查询pss接口QueryMerchantRemoteService().queryAllCityMerchants出现异常", e);
        }
        return merchantIds;
    }

    @Override
    public List<FullFieldsPmInfoWithProductVo> queryPmInfosByMerchantId(Long merchantId, Long currentPage, Long pageSize) {
        if(null == merchantId || null == currentPage || null == pageSize) {
            return Collections.emptyList();
        }
        List<FullFieldsPmInfoWithProductVo> result = null;
        QueryPmInfoWithProductByMerchantIdRequest request = new QueryPmInfoWithProductByMerchantIdRequest();
        request.setMerchantId(merchantId);
        request.setCurrentPage(currentPage);
        request.setPageSize(pageSize);
        try {
            Response<List<FullFieldsPmInfoWithProductVo>> response = PssClient
                    .getInstance(ScheduleConstants.PSS_TIME_OUT, ScheduleConstants.GROUP_NAME)
                    .getQueryPmInfoRemoteService().queryPmInfoWithProductByMerchantId(request);
            if(response != null && response.getResult() != null) {
                result = response.getResult();
            }

        } catch (HedwigException e) {
            logger.error("查询pss接口QueryPmInfoRemoteService().queryPmInfoWithProductByMerchantId出现异常", e);
        }
        return result;
    }

    @Override
    public List<PmInfo> getPmInfoByMerchantIdAndProductIds(Long merchantId, List<Long> productIds) {
        if(merchantId == null || CollectionUtils.isEmpty(productIds)) {
            return Collections.emptyList();
        }
        try {
            QueryBasePmInfoByMerchantIdAndProductIdsRequest input = new QueryBasePmInfoByMerchantIdAndProductIdsRequest();
            input.setMerchantId(merchantId);
            input.setProductIds(productIds);
            Response<List<PmInfo>> remoteResult = PssClient
                    .getInstance(GODConstants.TIME_OUT, ScheduleConstants.GROUP_NAME)
                    .getQueryPmInfoRemoteService().queryBasePmInfoByMerchantIdAndProductIds(input);
            if(CollectionUtils.isNotEmpty(remoteResult.getResult())) {
                return remoteResult.getResult();
            }
        } catch (Exception e) {
            logger.error("查询pss接口QueryPmInfoRemoteService().queryBasePmInfoByMerchantIdAndProductIds出现异常", e);
        }
        return Collections.emptyList();
    }

}
