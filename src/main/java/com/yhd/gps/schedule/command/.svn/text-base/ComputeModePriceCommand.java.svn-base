package com.yhd.gps.schedule.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.yhd.god.client.facade.GodCityPickPriceFacadeService;
import com.yhd.god.common.GpsOfflineClientUtil;
import com.yhd.god.vo.MobileAvgModePriceVo;
import com.yhd.god.vo.input.MobileAvgModePriceRequest;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;
import com.yihaodian.backend.price.base.ResultDto;
import com.yihaodian.backend.price.cityPick.application.CityPickPriceApp;
import com.yihaodian.backend.price.cityPick.vo.ConfirmedPriceVo;
import com.yihaodian.backend.price.cityPick.vo.QueryConfirmedPriceRequest;
import com.yihaodian.backend.price.cityPick.vo.ScheduleQueryVo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleVo;
import com.yihaodian.backend.price.cityPick.vo.SuggestedPriceVo;
import com.yihaodian.backend.price.common.BackendPriceHedwigServiceFactory;
import com.yihaodian.busy.common.GPSUtils;

/**
 * @author:liguanghui1
 * @date ：2017-4-13 下午06:04:39
 */
public class ComputeModePriceCommand extends ShardingDataExecCommand<List<Integer>, List<ConfirmedPriceVo>> {
    private static final Logger LOG = Logger.getLogger(ComputeModePriceCommand.class);
    // 价格后台远程Service
    private static CityPickPriceApp cityPickPriceApp = BackendPriceHedwigServiceFactory.getCityPickPriceApp();
    // 离线远程Service
    private static GodCityPickPriceFacadeService godCityPickPriceFacadeService = GpsOfflineClientUtil
            .getGodCityPickPriceFacadeService();

    public ComputeModePriceCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType) {
        super(shardingIndex, finishSignal, bussinessType);
    }

    @Override
    public List<ConfirmedPriceVo> fetchBussinessDataes(int shardingIndex) {

        ResultDto<List<ScheduleVo>> remoteResult = null;
        List<Integer> ststus = new ArrayList<Integer>();
        ststus.add(ScheduleConstants.PRICE_SCHEDULE_STATUS_MESSAGE_SENDED);
        // 如果没有任务则定时任务退出
        try {
            ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
            scheduleQueryVo.setShardingIndex(shardingIndex);
            scheduleQueryVo.setStatus(ststus);
            remoteResult = cityPickPriceApp.getCityPickPriceSchedule4Schedule(scheduleQueryVo);
        } catch (Exception e) {
            LOG.error("查询Backend远程接口失败！" + e.getMessage());
        }
        if(remoteResult == null || CollectionUtils.isEmpty(remoteResult.getResult())) {
            LOG.info("没有任务可以执行");
            return null;
        }
        List<ScheduleVo> list = remoteResult.getResult();
        QueryConfirmedPriceRequest request = new QueryConfirmedPriceRequest();
        // 对接口返回任务进行遍历
        for(ScheduleVo scheduleVo : list) {
            request.setPriceScheduleId(scheduleVo.getId());
            request.setMerchantId(scheduleVo.getMerchantId());
            request.setProductSourceType(ScheduleConstants.PRODUCT_SOURCE_TYPE_AUTO); // 商品来源，限制为“自动定价档期”
            // 查询商品定价表
            ResultDto<List<ConfirmedPriceVo>> resultDto = null;
            Long currentPage = ScheduleConstants.CURRENT_PAGE; // 当前从第几页开始查
            request.setPageSize(ScheduleConstants.PAGE_SIZE);
            request.setMarked(1); //定时器3调用标记
            int flag=0;
            while(true) {
                request.setCurrentPage(currentPage);
                try {
                    resultDto = cityPickPriceApp.getConfirmedPrice(request);
                } catch (Exception e) {
                    flag++;
                    LOG.info("###第"+flag+"次调用远程接口查询商品定价表异常！###" + e.getMessage());
                    if(flag>2){
                        break;
                    }
                    continue;
                }
                if(ScheduleConstants.STATUS_FAIL.equals(resultDto.getStatus())) {
                    flag++;
                    LOG.info("###第"+flag+"次调用远程接口查询商品定价表异常！###" + resultDto.getErrMsg());
                    if(flag>2){
                        break;
                    }
                    continue;
                }
                if(ScheduleConstants.STATUS_SUCCESS.equals(resultDto.getStatus())
                   && CollectionUtils.isEmpty(resultDto.getResult())) {
                    break;
                }
                List<ConfirmedPriceVo> confirmedPriceVoList = resultDto.getResult();
                List<List<ConfirmedPriceVo>> totalList = GPSUtils.split(confirmedPriceVoList,
                        ScheduleConstants.MAX_NUMBER);
                for(List<ConfirmedPriceVo> subList : totalList) {
                    // 落地建议价
                    try {
                        writingSuggesstionPrice(subList);
                    } catch (Exception e) {
                        LOG.error("###定时器3写入建议价表异常###"+e.getMessage());
                    }
                }
                currentPage++;
            }
            ScheduleVo needUpdatescheduleVo = new ScheduleVo();
            needUpdatescheduleVo.setId(scheduleVo.getId());// 档期ID
            needUpdatescheduleVo.setStatus(ScheduleConstants.PRICE_SCHEDULE_STATUS_MODE_PRICE_COMPUTED);
            // 更新价格档期状态
            try {
                cityPickPriceApp.updatePriceScheduleVo(needUpdatescheduleVo);
            } catch (Exception e) {
                LOG.info("更新价格档期失败,档期ID是:" + scheduleVo.getId() + "异常信息是：" + e.getMessage());
            }

        }
        return null;
    }

    @Override
    public ExecResult<List<Integer>> doWork(List<ConfirmedPriceVo> confirmedPriceVoList) {
        return null;
    }

    @Override
    public int updateData2Processed(List<Long> dataIds) {
        return 0;
    }

    private void writingSuggesstionPrice(List<ConfirmedPriceVo> confirmedPriceVoList) {
        List<MobileAvgModePriceRequest> requestList = new ArrayList<MobileAvgModePriceRequest>();
        for(ConfirmedPriceVo confirmedPriceVo : confirmedPriceVoList) {
            MobileAvgModePriceRequest mobileAvgModePriceRequest = new MobileAvgModePriceRequest();
            mobileAvgModePriceRequest.setPmInfoId(confirmedPriceVo.getPmInfoId());
            mobileAvgModePriceRequest.setProductId(confirmedPriceVo.getProductId());
            mobileAvgModePriceRequest.setMerchantId(confirmedPriceVo.getMerchantId());
            requestList.add(mobileAvgModePriceRequest);
        }
        com.yhd.god.common.ResultDto<Map<Long, MobileAvgModePriceVo>> result = null;
        try {
            result = godCityPickPriceFacadeService.queryMobileAvgAndModePrices(requestList);
        } catch (Exception e) {
            LOG.error("调用计算众数价接口失败:" + e.getMessage());
            return;
        }
        if(result == null || result.getResult().isEmpty()) {
            LOG.info("计算众数价接口没有返回值");
            return;
        }
        Map<Long, MobileAvgModePriceVo> resultMap = result.getResult();
        List<SuggestedPriceVo> suggestionVos = new ArrayList<SuggestedPriceVo>();
        // 构建移动平均价 众数价
        ConfirmedPriceVo updateconfirmedPriceVo = new ConfirmedPriceVo();
        updateconfirmedPriceVo.setPriceScheduleId(confirmedPriceVoList.get(0).getPriceScheduleId());
        for(ConfirmedPriceVo confirmedPriceVo : confirmedPriceVoList) {
            if(!resultMap.containsKey(confirmedPriceVo.getPmInfoId())) {
                continue;
            }
            MobileAvgModePriceVo mobileAvgModePriceVo = resultMap.get(confirmedPriceVo.getPmInfoId());
            //构建一条众数价记录
            if(mobileAvgModePriceVo.getModePrice() != null) {
                SuggestedPriceVo suggestedPriceVo4ModePrice = new SuggestedPriceVo();
                suggestedPriceVo4ModePrice.setPriceScheduleId(confirmedPriceVo.getPriceScheduleId());// 价格档期ID
                suggestedPriceVo4ModePrice.setPmInfoId(confirmedPriceVo.getPmInfoId()); // 商品ID
                suggestedPriceVo4ModePrice.setProductId(confirmedPriceVo.getProductId()); // 产品ID
                suggestedPriceVo4ModePrice.setMerchantId(confirmedPriceVo.getMerchantId()); // 商家ID
                suggestedPriceVo4ModePrice.setAdvicedPrice(mobileAvgModePriceVo.getModePrice()); // 建议价：众数价
                suggestedPriceVo4ModePrice.setReferenceMerchantId(mobileAvgModePriceVo.getRefMerchantId());// 参考一号店自营商家
                                                                                                           // 关联商家id
                suggestedPriceVo4ModePrice.setPriceType(ScheduleConstants.MODE_PRICE);// 建议价 类型
                                                                                      // 5:180天众数价
                suggestedPriceVo4ModePrice.setSourceType(ScheduleConstants.GPS); // 建议价来源 3:GPS
                String priceDesc4ModePrice = null;
                if(mobileAvgModePriceVo.getMerchantId() != null
                   && mobileAvgModePriceVo.getMerchantId().equals(mobileAvgModePriceVo.getRefMerchantId())) {
                    priceDesc4ModePrice = "根据自身商家计算众数价";
                } else {
                    priceDesc4ModePrice = "根据关联商家计算众数价";
                }
                suggestedPriceVo4ModePrice.setPriceDesc(priceDesc4ModePrice); // 价格描述
                suggestionVos.add(suggestedPriceVo4ModePrice);
            }
            //构建一条移动平均价记录
            if(mobileAvgModePriceVo.getAvgPrice() != null && mobileAvgModePriceVo.getSbyAvgPrice() != null
                    && mobileAvgModePriceVo.getFbyLowPoint() != null) {
                SuggestedPriceVo suggestedPriceVo4AvgPrice = new SuggestedPriceVo();
                suggestedPriceVo4AvgPrice.setPriceScheduleId(confirmedPriceVo.getPriceScheduleId());// 价格档期ID
                suggestedPriceVo4AvgPrice.setPmInfoId(confirmedPriceVo.getPmInfoId()); // 商品ID
                suggestedPriceVo4AvgPrice.setProductId(confirmedPriceVo.getProductId()); // 产品ID
                suggestedPriceVo4AvgPrice.setMerchantId(confirmedPriceVo.getMerchantId()); // 商家ID
                suggestedPriceVo4AvgPrice.setAdvicedPrice(mobileAvgModePriceVo.getAvgPrice()); // 建议价：加权移动平均价
                suggestedPriceVo4AvgPrice.setReferenceMerchantId(mobileAvgModePriceVo.getRefMerchantId()); // 参考一号店自营商家
                                                                                                           // 关联商家id
                suggestedPriceVo4AvgPrice.setPriceType(ScheduleConstants.AVG_PRICE); // 建议价 类型
                                                                                     // ：price_type
                                                                                     // 6:移动平均价加权
                suggestedPriceVo4AvgPrice.setSourceType(ScheduleConstants.GPS); // 建议价来源 3:GPS
                StringBuilder priceDesc4AvgPrice = new StringBuilder();
                if(mobileAvgModePriceVo.getSbyAvgPrice() != null) {
                    priceDesc4AvgPrice.append("sby移动平均价是：").append(mobileAvgModePriceVo.getSbyAvgPrice().toString());
                }
                if(mobileAvgModePriceVo.getFbyLowPoint() != null) {
                    priceDesc4AvgPrice.append(";FBY最低扣点是：").append(mobileAvgModePriceVo.getFbyLowPoint().toString());
                }
                suggestedPriceVo4AvgPrice.setPriceDesc(priceDesc4AvgPrice.toString()); // 价格描述 ：
                suggestionVos.add(suggestedPriceVo4AvgPrice);
            }
        }
        // 批量插入商品建议价格表 移动平均价
        ResultDto<Boolean> addResult=null;
        try {
            addResult = cityPickPriceApp.addSuggestedPrice(suggestionVos);
        } catch (Exception e) {
            LOG.error("###定时器3调用接口插入建议价异常###"+e.getMessage());
        }
        if(addResult != null && addResult.getResult() == true) {
            LOG.info("新增建议价成功！！！");
        } else {
            LOG.info("新增建议价失败！！！");
        }
    }
}
