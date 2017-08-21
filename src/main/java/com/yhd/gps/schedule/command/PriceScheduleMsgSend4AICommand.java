package com.yhd.gps.schedule.command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.newheight.hessianService.service.AimisProductService;
import com.newheight.support.EdmServiceContainer;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.utils.DateUtil;
import com.yhd.pss.spi.common.vo.Response;
import com.yhd.pss.spi.pminfo.service.PmInfoService;
import com.yhd.pss.spi.pminfo.vo.PmInfoAndProductVo;
import com.yhd.pss.spi.pminfo.vo.input.QueryPmInfoAndProductInfoAndCategoryInfoByMerchantIdRequest;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;
import com.yhd.shareservice.cache.autoclear.util.CollectionUtil;
import com.yihaodian.backend.price.base.ResultDto;
import com.yihaodian.backend.price.cityPick.application.CityPickPriceApp;
import com.yihaodian.backend.price.cityPick.vo.ConfirmedPriceVo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleQueryVo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleVo;
import com.yihaodian.backend.price.common.BackendPriceHedwigServiceFactory;
import com.yihaodian.pss.client.PssClient;

/**
 * @author:liguanghui1
 * @date ：2017-4-25 下午02:00:11
 */
public class PriceScheduleMsgSend4AICommand extends ShardingDataExecCommand<List<Long>, List<Long>> {

    private static final Logger LOG = Logger.getLogger(PriceScheduleMsgSend4AICommand.class);
    private Long currentPage = 1L;
    private Long pageSize = 100L;
    // 价格后台远程Service
    private static CityPickPriceApp cityPickPriceApp = BackendPriceHedwigServiceFactory.getCityPickPriceApp();

    // pss远程接口
    @SuppressWarnings("deprecation")
    PmInfoService pmInfoService = PssClient.getInstance(ScheduleConstants.PSS_TIME_OUT, ScheduleConstants.GROUP_NAME)
            .getPmInfoService();
    // AI远程接口
    private AimisProductService aimisProductService = EdmServiceContainer.getInstance().getService(
            AimisProductService.class);

    public PriceScheduleMsgSend4AICommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType) {
        super(shardingIndex, finishSignal, bussinessType);
    }

    @Override
    public List<Long> fetchBussinessDataes(int shardingIndex) {
        List<Integer> status = new ArrayList<Integer>();
        status.add(ScheduleConstants.PRICE_SCHEDULE_STATUS_NEW); // 新建定价档期
        status.add(ScheduleConstants.PRICE_SCHEDULE_STATUS_AI_RULE_SET); // 手工AI配置完成
        Date now = new Date();
        List<Long> list = MiscConfigUtils.getConfigListByItemKey("delays");
        Long delays = 7L;
        if(CollectionUtils.isNotEmpty(list)) {
            delays = list.get(0);
            LOG.error("###定时器2查到的字典值delays###"+delays);
        } else {
            LOG.error("####查询字典表获取未来多少天生效的档期值没有返回值！！给了一个默认值7####");
        }
        Date startTime = PriceScheduleMsgSend4AICommand.getStartTime(now, delays.intValue());
        Date endTime = PriceScheduleMsgSend4AICommand.getEndTime(now, delays.intValue());
        ResultDto<List<ScheduleVo>> result = null;
        try {
            ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
            scheduleQueryVo.setShardingIndex(shardingIndex);
            scheduleQueryVo.setOffset(0L);
            scheduleQueryVo.setStartTime(startTime);
            scheduleQueryVo.setEndTime(endTime);
            scheduleQueryVo.setStatus(status);
            result = cityPickPriceApp.getCityPickPriceSchedule4Schedule(scheduleQueryVo);
        } catch (Exception e) {
            LOG.error("####查询价格后台远程接口失败：异常信息：####" + e.getMessage());
            return null;
        }
        if(result == null || result.getStatus() == 0) {
            LOG.error("####查询价格后台远程接口失败:错误信息：####" + (result == null ? "" : result.getErrMsg()));
            return null;
        }
        if(CollectionUtils.isEmpty(result.getResult())) {
            LOG.error("####没有满足条件的数据####");
            return null;
        }
        List<ScheduleVo> scheduleVoList = result.getResult();
        // 根据商家ID从选品系统拉取在售商品清单
        QueryPmInfoAndProductInfoAndCategoryInfoByMerchantIdRequest request = new QueryPmInfoAndProductInfoAndCategoryInfoByMerchantIdRequest();
        Response<Long> remoteCountResult = null;

        for(ScheduleVo scheduleVo : scheduleVoList) {
            request.setMerchantId(scheduleVo.getMerchantId());
            try {
                remoteCountResult = pmInfoService.queryCountPmInfoProductByMerchantIdWithPg(request);
            } catch (Exception e) {
                LOG.error("####查询价格后台远程接口失败：异常信息：####" + e.getMessage());
            }
            if(remoteCountResult == null || remoteCountResult.getResult().compareTo(0L) < 0) {
                LOG.error("####没有满足条件的数据####档期ID是：" + scheduleVo.getId());
                continue;
            }
            addConfirmedPrice(request, scheduleVo);

            ScheduleVo updateScheduleVo = new ScheduleVo();
            updateScheduleVo.setId(scheduleVo.getId());
            updateScheduleVo.setStatus(ScheduleConstants.PRICE_SCHEDULE_STATUS_MESSAGE_SENDED);
            updateScheduleVo.setScheduleProcessingTime(new Date());
            // 更新价格档期状态为已发送档期消息
            try {
                cityPickPriceApp.updatePriceScheduleVo(updateScheduleVo);
            } catch (Exception e) {
                LOG.error("####更新价格档期状态失败####" + "档期ID:" + scheduleVo.getId() + "异常信息:" + e.getMessage());
            }
            // 通知AI开始计算档期价格
            try {
                aimisProductService.notifyAiComputing(scheduleVo.getId());
            } catch (Exception e) {
                LOG.error("####通知AI开始计算档期价格远程接口调用失败####" + "档期ID:" + scheduleVo.getId() + "异常信息:" + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public ExecResult<List<Long>> doWork(List<Long> bussinessDataes) {
        return null;
    }

    @Override
    public int updateData2Processed(List<Long> dataIds) {
        return 0;
    }

    private void addConfirmedPrice(QueryPmInfoAndProductInfoAndCategoryInfoByMerchantIdRequest request,
                                   ScheduleVo scheduleVo) {
        int i = 0;
        while(true) {
            request.setCurrentPage(currentPage);
            request.setPageSize(pageSize);
            Response<List<PmInfoAndProductVo>> RemoteResultPmInfoAndProductVoList = null;
            try {
                RemoteResultPmInfoAndProductVoList = pmInfoService.queryPmInfoProductByMerchantIdWithPg(request);
            } catch (Exception e) {
                i++;
                LOG.error("####第" + i + "次查询PSS接口异常：异常信息：####" + e.getMessage());
                if(i == 3) {
                    break;
                }
                continue;
            }
            List<PmInfoAndProductVo> pmInfoAndProductVoList = RemoteResultPmInfoAndProductVoList.getResult();
            if(CollectionUtil.isEmpty(pmInfoAndProductVoList)) {
                break;
            }
            // 去除组合品，系列虚品，销售类型为"城市精选预定商品"
            for(Iterator<PmInfoAndProductVo> it = pmInfoAndProductVoList.iterator(); it.hasNext();) {
                PmInfoAndProductVo PmInfoAndProductVo = (PmInfoAndProductVo) it.next();
                if(ScheduleConstants.SERIES_PRODUCTS.equals(PmInfoAndProductVo.getProductType())
                   || ScheduleConstants.COMBINE_PRODUCT.equals(PmInfoAndProductVo.getProductType())
                   || ScheduleConstants.CITY_PICK_PRODUCT.equals(PmInfoAndProductVo.getProductSaleType())) {
                    it.remove();
                }
            }
            if(pmInfoAndProductVoList.size() == 0) {
                return;
            }
            List<ConfirmedPriceVo> confirmedPriceVoList = new ArrayList<ConfirmedPriceVo>();
            for(PmInfoAndProductVo pmInfoAndProductVo : pmInfoAndProductVoList) {
                ConfirmedPriceVo confirmedPriceVo = new ConfirmedPriceVo();
                confirmedPriceVo.setPriceScheduleId(scheduleVo.getId()); // 档期ID
                confirmedPriceVo.setProductId(pmInfoAndProductVo.getProductId()); // 产品ID
                confirmedPriceVo.setPmInfoId(pmInfoAndProductVo.getPmInfoId()); // 商品ID
                confirmedPriceVo.setMerchantId(pmInfoAndProductVo.getMerchantId()); // 商家ID
                confirmedPriceVo.setProductSourceType(ScheduleConstants.AUTO_CONFIRM_PRICE_SCHEDULE_SOURCE_TYPE); // 商品来源(1:自动定价档期
                                                                                                                  // 2:新品导入
                                                                                                                  // 3:无定时器档期)
                confirmedPriceVoList.add(confirmedPriceVo);
            }
            // 保存到商品定价表
            ResultDto<Boolean> remoteResult = cityPickPriceApp.addConfirmedPrice(confirmedPriceVoList);
            if(remoteResult == null || remoteResult.getResult() != true) {
                continue;
            }
            currentPage++;
        }
    }

    private static Date getStartTime(Date date, Integer delays) {
        Date future = DateUtil.addDays(date, delays); // 获取7天后的日期
        return DateUtil.parseDate(future); // 7天后的00:00:00
    }

    private static Date getEndTime(Date date, Integer delays) {
        Date future = DateUtil.addDays(date, delays); // 获取7天后的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(future);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date newDate = calendar.getTime();
        return newDate;
    }
}
