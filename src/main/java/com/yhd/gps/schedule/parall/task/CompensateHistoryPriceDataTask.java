package com.yhd.gps.schedule.parall.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.schedule.parall.BaseTask;
import com.yhd.gps.schedule.parall.param.CompensateHistoryPriceDataParam;
import com.yhd.gps.schedule.parall.result.WrapResult;
import com.yhd.gps.schedule.vo.builder.HistoryPriceChangeMsgVoBuilder4Schedule;
import com.yhd.pss.spi.common.vo.Response;
import com.yhd.pss.spi.pminfo.service.QueryPmInfoRemoteService;
import com.yhd.pss.spi.pminfo.vo.PmInfo;
import com.yhd.pss.spi.pminfo.vo.SimpleProductAndPmInfoVo;
import com.yhd.pss.spi.pminfo.vo.input.QueryBasePmInfoByMerchantIdAndProductIdsRequest;
import com.yhd.pss.spi.pminfo.vo.input.QuerySimpleProductAndPminfoByProIdAndMerchantIdRequest;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.busy.vo.BusyPmPriceVo;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.front.busystock.client.facade.BusyPriceFacadeService;

/**
 * ---- 补偿历史价格数据任务 ------
 * @Author lipengfei
 * @CreateTime 2016-8-25 下午09:52:05
 */
public class CompensateHistoryPriceDataTask extends BaseTask<Void> {
    
    protected final Log logger = LogFactory.getLog(CompensateHistoryPriceDataTask.class);

    private CompensateHistoryPriceDataParam compensateHistoryPriceDataParam;
    
    /**
     * @param compensateHistoryPriceDataParam 
     * @param resultMap 共享
     * @param finishSignal 共享
     */
    public CompensateHistoryPriceDataTask(Integer taskNo, CompensateHistoryPriceDataParam compensateHistoryPriceDataParam, WrapResult<Void> wrapResult) {
        this.compensateHistoryPriceDataParam = compensateHistoryPriceDataParam;
        this.taskNo = taskNo;
        this.resultMap = wrapResult.getResultMap();
        this.finishSignal = wrapResult.getFinishSignal();
    }
    
    @Override
    protected Void runTask() {
        Long merchantId = compensateHistoryPriceDataParam.getMerchantId();
        BusyPmInfoDao busyPmInfoDao = compensateHistoryPriceDataParam.getBusyPmInfoDao();
        QueryPmInfoRemoteService queryPmInfoService = CompensateHistoryPriceDataParam.getQuerypminfoservice();
        
        List<Long> allPmIds = new ArrayList<Long>();
        try {
            // 每次最多1000条
            Long pageSize = 1000L;
            Long currentPage = 1L;
            Long productId = 0L;
            boolean isContinueSerarch = true;
            QuerySimpleProductAndPminfoByProIdAndMerchantIdRequest request = new QuerySimpleProductAndPminfoByProIdAndMerchantIdRequest();
            QueryBasePmInfoByMerchantIdAndProductIdsRequest request_ = new QueryBasePmInfoByMerchantIdAndProductIdsRequest();
            request.setMerchantId(merchantId);
            request.setPageSize(pageSize);
            request_.setMerchantId(merchantId);
            while(isContinueSerarch) {
                request.setCurrentPage(currentPage);
                request.setProductId(productId);
                Response<List<SimpleProductAndPmInfoVo>> response = queryPmInfoService.querySimpleProductAndPmInfoByProIdAndMerchantId(request);
                List<SimpleProductAndPmInfoVo> restlt = response.getResult();
                if(CollectionUtils.isEmpty(restlt)) {
                    return null;
                }
                // 过滤出可销可见非赠品的产品
                Set<Long> productIds = new HashSet<Long>(restlt.size());
                for(SimpleProductAndPmInfoVo vo : restlt) {
                    if(vo != null && vo.getIsGift() == 0 && vo.getCanSale() == 1 && vo.getCanShow() == 1) {
                        productIds.add(vo.getProductId());
                    }
                }
                
                // 根据产品查询具体的商品
                List<List<Long>> allPIds = GPSUtils.split(new ArrayList<Long>(productIds), 100);
                Set<Long> pmIds = new HashSet<Long>();
                for(List<Long> pIds : allPIds) {
                    request_.setProductIds(pIds);
                    Response<List<PmInfo>> queryBasePmInfoByMerchantIdAndProductIds = queryPmInfoService.queryBasePmInfoByMerchantIdAndProductIds(request_);
                    List<PmInfo> pmInfos = queryBasePmInfoByMerchantIdAndProductIds.getResult();
                    if(CollectionUtils.isNotEmpty(pmInfos)) {
                        for(PmInfo pmInfo : pmInfos) {
                            pmIds.add(pmInfo.getId());
                        }
                    }
                }
                // 求出组合商品主品pmInfoId
                List<Long> mainPmInfoIds = busyPmInfoDao.batchGetMainCombinePmIdBySubPmIds(new ArrayList<Long>(pmIds));
                if(CollectionUtils.isNotEmpty(mainPmInfoIds)) {
                    // 过滤pmId为null的情况
                    for(Long mainPmInfoId : mainPmInfoIds) {
                        if(mainPmInfoId != null) {
                            pmIds.add(mainPmInfoId);
                        }
                    }
                }
                recordHistoryPriceChange(new ArrayList<Long>(pmIds));
                allPmIds.addAll(pmIds);
                if(CollectionUtils.isEmpty(restlt) || restlt.size() < pageSize) {
                    isContinueSerarch = false;
                } else {
                    productId = restlt.get(999).getProductId();// 获取每批最后一个productId
                }
                // currentPage++;//注意：此处不能加此
            }
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info("---总的补偿商品数据:" + allPmIds.size() + "---");
        return null;
    }

    /**
     * 记录到历史价格统计表
     * @param pmIds
     */
    private void recordHistoryPriceChange(final List<Long> pmIds) {
        try {
            BusyPriceFacadeService busyPriceFacadeService = CompensateHistoryPriceDataParam.getBusyPriceFacadeService();
            HistoryPriceChangeMsgDao historyPriceChangeMsgDao = compensateHistoryPriceDataParam.getHistoryPriceChangeMsgDao();
            
            // 查询GPS获取价格
            List<BusyPmPriceVo> pmPriceVos = busyPriceFacadeService.batchGetPmPriceVoListWithCache4Schedule(pmIds);
            if(CollectionUtils.isEmpty(pmPriceVos)) {
                return;
            }
            List<List<BusyPmPriceVo>> lists = GPSUtils.split(pmPriceVos, MSG_SIZE);
            for(List<BusyPmPriceVo> list : lists) {
                // 记录历史价格
                List<HistoryPriceChangeMsgVo> historyPriceChangeMsgs = new ArrayList<HistoryPriceChangeMsgVo>(
                    list.size());
                for(BusyPmPriceVo busyPmPriceVo : list) {
                    if(null == busyPmPriceVo) {
                        continue;
                    }
                    historyPriceChangeMsgs.add(HistoryPriceChangeMsgVoBuilder4Schedule
                            .buildHistoryPriceChangeMsg(busyPmPriceVo));
                }
                historyPriceChangeMsgDao.batchInsertHistoryPriceChangeMsg(historyPriceChangeMsgs);
            }
        } catch (Exception e) {
            logger.error("###记录历史价格表出错###" + e.getMessage(), e);
        }
    }
}
