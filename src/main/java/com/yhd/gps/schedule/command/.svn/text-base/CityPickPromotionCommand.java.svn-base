package com.yhd.gps.schedule.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.busyservice.service.CityPickPriceService;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.common.Command;
import com.yhd.gps.schedule.common.ExecutorManager;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;
import com.yihaodian.backend.price.base.ResultDto;
import com.yihaodian.backend.price.cityPick.application.CityPickPriceApp;
import com.yihaodian.backend.price.cityPick.vo.CityPickPromCreateVo;
import com.yihaodian.backend.price.cityPick.vo.ConfirmedPricePromInputVo;
import com.yihaodian.backend.price.cityPick.vo.ConfirmedPriceVo;
import com.yihaodian.backend.price.cityPick.vo.QueryConfirmedPriceRequest;
import com.yihaodian.backend.price.cityPick.vo.ScheduleQueryVo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleVo;
import com.yihaodian.backend.price.common.BackendPriceHedwigServiceFactory;
import com.yihaodian.busy.common.GPSUtils;

/**
 * Created by zhangshuqiang on 2017/5/2.
 * 
 */
public class CityPickPromotionCommand extends ShardingDataExecCommand<Void, List<ScheduleVo>> {

    private static final Logger LOG = Logger.getLogger(CityPickPromotionCommand.class);

    private static final String CITY_PICK_DATA_COLLECTED_DAYS_KEY = "CITY_PICK_DATA_COLLECTED_DAYS";

    private BusyMiscConfigDao busyMiscConfigDao;

    private CityPickPriceService cityPickPriceService;

    // 价格后台远程Service
    private static CityPickPriceApp cityPickPriceApp = BackendPriceHedwigServiceFactory.getCityPickPriceApp();

    public CityPickPromotionCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,
                                    BusyMiscConfigDao busyMiscConfigDao, CityPickPriceService cityPickPriceService) {
        super(shardingIndex, finishSignal, bussinessType);
        this.busyMiscConfigDao = busyMiscConfigDao;
        this.cityPickPriceService = cityPickPriceService;
    }

    @Override
    public List<ScheduleVo> fetchBussinessDataes(int shardingIndex) {

        ResultDto<List<ScheduleVo>> remoteResult = null;
        List<Integer> status = new ArrayList<Integer>();
        status.add(ScheduleConstants.PRICE_SCHEDULE_STATUS_PRICE_COLLECTED);

        try {
            ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
            scheduleQueryVo.setShardingIndex(shardingIndex);
            scheduleQueryVo.setStatus(status);
            remoteResult = cityPickPriceApp.getCityPickPriceSchedule4Schedule(scheduleQueryVo);
        } catch (Exception e) {
            LOG.error("查询Backend远程接口失败！" + e.getMessage());
        }
        if(remoteResult == null || CollectionUtils.isEmpty(remoteResult.getResult())) {
            LOG.info("没有任务可以执行");
            return null;
        }

        List<ScheduleVo> scheduleVoList = remoteResult.getResult();

        if(CollectionUtils.isNotEmpty(scheduleVoList)) {
            filterScheduleByCollectedDate(scheduleVoList);
        }
        return scheduleVoList;
    }

    @Override
    public ExecResult<Void> doWork(List<ScheduleVo> bussinessDataes) {
        if(CollectionUtils.isNotEmpty(bussinessDataes)) {
            QueryConfirmedPriceRequest request = new QueryConfirmedPriceRequest();
            for(final ScheduleVo scheduleVo : bussinessDataes) {
                request.setPriceScheduleId(scheduleVo.getId());
                request.setMerchantId(scheduleVo.getMerchantId());
                // 查询商品定价表
                ResultDto<List<ConfirmedPriceVo>> resultDto;
                Long currentPage = ScheduleConstants.CURRENT_PAGE; // 当前从第几页开始查
                request.setPageSize(ScheduleConstants.PAGE_SIZE);
                request.setPriceStatus(ScheduleConstants.PRICE_STATUS_CHECK_PENDING);
                request.setProductSourceType(ScheduleConstants.AUTO_CONFIRM_PRICE_SCHEDULE_SOURCE_TYPE);
                while(true) {
                    request.setCurrentPage(currentPage);
                    try {
                        resultDto = cityPickPriceApp.getConfirmedPrice(request);
                    } catch (Exception e) {
                        LOG.info("调用远程接口查询商品定价表异常！" + e.getMessage());
                        continue;
                    }
                    if(ScheduleConstants.STATUS_FAIL.equals(resultDto.getStatus())) {
                        LOG.info("调用远程接口查询商品定价表异常！" + resultDto.getErrMsg());
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
                        // 生成普通促销
                        createPromotion(subList, scheduleVo.getName(), scheduleVo.getStartTime(),
                                scheduleVo.getEndTime());
                    }
                    currentPage++;
                }

                // 防呆处理数据汇总超过2天120分钟，不再尝试定时任务直接设置档期状态为已生效
                Date now = new Date();
                Date twoDaysAfterDataColleted = ScheduleDateUtils.addDays(scheduleVo.getDataCollectedTime(),
                        getDataCollectedDays());
                if(now.after(ScheduleDateUtils.addMinutes(twoDaysAfterDataColleted, 120))) {
                    scheduleVo.setStatus(ScheduleConstants.PRICE_SCHEDULE_STATUS_PRICE_EFFECT);
                    scheduleVo.setAutoPricingTime(new Date());
                    cityPickPriceApp.updatePriceScheduleVo(scheduleVo);

                    // 发送邮件
                    ExecutorManager.executeOnly(new Command<Object>() {

                        @Override
                        public Object action() {
                            cityPickPriceService.sendCityPickPriceEmail(scheduleVo,
                                    ScheduleConstants.PRICE_STATUS_ACTIVE);
                            return null;
                        }
                    });
                }
            }
        }
        return null;
    }

    @Override
    public int updateData2Processed(List<Long> dataIds) {
        return 0;
    }

    /**
     * 查找数据汇总已经超过两天(可配置)的价格档期
     * @param scheduleVoList
     */
    private void filterScheduleByCollectedDate(List<ScheduleVo> scheduleVoList) {
        Date now = new Date();
        int dataCollectedDays = getDataCollectedDays();
        Date twoDaysBefore = ScheduleDateUtils.addDays(now, -dataCollectedDays);
        Iterator iterator = scheduleVoList.iterator();
        while(iterator.hasNext()) {
            ScheduleVo scheduleVo = (ScheduleVo) iterator.next();
            Date dataCollectedTime = scheduleVo.getDataCollectedTime();
            if(dataCollectedTime == null || dataCollectedTime.after(twoDaysBefore)) {
                iterator.remove();
            }
        }
    }

    /**
     * 创建普通促销
     * @param confirmedPriceVoList
     * @return
     */
    private void createPromotion(List<ConfirmedPriceVo> confirmedPriceVoList, String scheduleName, Date startDate,
                                 Date endDate) {
        ConfirmedPricePromInputVo confirmedPricePromInputVo = new ConfirmedPricePromInputVo();
        confirmedPricePromInputVo.setBackOperatorId(2L);
        confirmedPricePromInputVo.setBackOperatorName("后台系统");
        List<CityPickPromCreateVo> cityPickPromCreateVoList = new ArrayList<CityPickPromCreateVo>();
        // 定价ID和pmInfoId映射
        Map<Long, Long> pmInfoIdMap = new HashMap<Long, Long>();

        StringBuffer promNameBuffer = new StringBuffer();
        promNameBuffer.append(scheduleName).append(ScheduleDateUtils.format(startDate)).append("~")
                .append(ScheduleDateUtils.format(endDate));

        List<CityPickPromCreateVo> notPassedList = new ArrayList<CityPickPromCreateVo>();
        for(ConfirmedPriceVo confirmedPriceVo : confirmedPriceVoList) {
            CityPickPromCreateVo cityPickPromCreateVo = new CityPickPromCreateVo();
            cityPickPromCreateVo.setPmInfoId(confirmedPriceVo.getPmInfoId());
            cityPickPromCreateVo.setPriceScheduleId(confirmedPriceVo.getPriceScheduleId());
            cityPickPromCreateVo.setPromPrice(confirmedPriceVo.getActivePrice());
            cityPickPromCreateVo.setStartDate(startDate);
            cityPickPromCreateVo.setEndDate(endDate);
            cityPickPromCreateVo.setPromName(promNameBuffer.toString());
            pmInfoIdMap.put(confirmedPriceVo.getPmInfoId(), confirmedPriceVo.getId());
            cityPickPromCreateVo.setSourceType(ScheduleConstants.CITY_PICK_CONFIRMED_PRICE);
            if(confirmedPriceVo.getActivePrice() == null
               || confirmedPriceVo.getActivePrice().compareTo(BigDecimal.ZERO) <= 0) {
                cityPickPromCreateVo.setResultCode(ScheduleConstants.RESULT_FAIL_NO_ACTIVE_PRICE);
                notPassedList.add(cityPickPromCreateVo);
                continue;
            }
            cityPickPromCreateVoList.add(cityPickPromCreateVo);
        }
        confirmedPricePromInputVo.setPromList(cityPickPromCreateVoList);

        try {
            List<CityPickPromCreateVo> retList = cityPickPriceApp.createCommonPromotion(confirmedPricePromInputVo);
            if(retList == null) {
                retList = new ArrayList<CityPickPromCreateVo>();
            }
            if(CollectionUtils.isNotEmpty(notPassedList)) {
                retList.addAll(notPassedList);
            }
            // 创建普通促销后更新价格表中状态
            if(CollectionUtils.isNotEmpty(retList)) {
                updatePriceStatus(retList, pmInfoIdMap);
            }
        } catch (Exception ex) {
            LOG.error("cityPickPriceApp.createCommonPromotion", ex);
        }
    }

    /**
     * 更新定价状态
     * @param cityPickPromCreateVoList
     * @param pmInfoIdMap
     */
    public void updatePriceStatus(List<CityPickPromCreateVo> cityPickPromCreateVoList, Map<Long, Long> pmInfoIdMap) {
        List<ConfirmedPriceVo> confirmedPriceVos = new ArrayList<ConfirmedPriceVo>();
        for(CityPickPromCreateVo cityPickPromCreateVo : cityPickPromCreateVoList) {
            ConfirmedPriceVo confirmedPriceVo = new ConfirmedPriceVo();
            confirmedPriceVo.setId(pmInfoIdMap.get(cityPickPromCreateVo.getPmInfoId()));

            if(ScheduleConstants.RESULT_FAIL_EXCEPTION.equals(cityPickPromCreateVo.getResultCode())
               || ScheduleConstants.RESULT_FAIL_DAD_DEBTS.equals(cityPickPromCreateVo.getResultCode())
               || ScheduleConstants.RESULT_FAIL_DATA_ERROR.equals(cityPickPromCreateVo.getResultCode())) {
                confirmedPriceVo.setUnEffectiveReason(cityPickPromCreateVo.getDescription());
                confirmedPriceVo.setPriceStatus(ScheduleConstants.PRICE_STATUS_NOT_ACTIVE);
                confirmedPriceVo.setCheckedTime(new Date());
                confirmedPriceVo.setVerifierId(2L);
                confirmedPriceVo.setOperatorId(2L);
                confirmedPriceVo.setIsManual(0);
                confirmedPriceVos.add(confirmedPriceVo);
                LOG.info("创建促销失败, pmInfoId:" + cityPickPromCreateVo.getPmInfoId() + "失败原因："
                         + cityPickPromCreateVo.getDescription());
            } else if(ScheduleConstants.RESULT_SUCCESS_VALID.equals(cityPickPromCreateVo.getResultCode())) {
                confirmedPriceVo.setPromRuleId(cityPickPromCreateVo.getPromId());
                confirmedPriceVo.setPriceStatus(ScheduleConstants.PRICE_STATUS_ACTIVE);
                confirmedPriceVo.setCheckedTime(new Date());
                confirmedPriceVo.setVerifierId(2L);
                confirmedPriceVo.setOperatorId(2L);
                confirmedPriceVo.setIsManual(0);
                // confirmedPriceVo.setActivePrice(cityPickPromCreateVo.getPromPrice());
                confirmedPriceVos.add(confirmedPriceVo);
            } else if(ScheduleConstants.RESULT_IN_OPERATING.equals(cityPickPromCreateVo.getResultCode())) {
                confirmedPriceVo.setPriceStatus(ScheduleConstants.PRICE_STATUS_GROSS_PROFIT_DOING);
                confirmedPriceVo.setGrossProfitId(cityPickPromCreateVo.getGrossProfitId());
                confirmedPriceVo.setCheckedTime(new Date());
                confirmedPriceVo.setVerifierId(2L);
                confirmedPriceVo.setOperatorId(2L);
                confirmedPriceVo.setIsManual(0);
                confirmedPriceVos.add(confirmedPriceVo);
            } else if(ScheduleConstants.RESULT_FAIL_NO_ACTIVE_PRICE.equals(cityPickPromCreateVo.getResultCode())) {
                confirmedPriceVo.setPriceStatus(ScheduleConstants.PRICE_STATUS_CHECK_NOT_PASS);
                confirmedPriceVo.setCheckedTime(new Date());
                confirmedPriceVo.setVerifierId(2L);
                confirmedPriceVo.setOperatorId(2L);
                confirmedPriceVo.setIsManual(0);
                confirmedPriceVos.add(confirmedPriceVo);
            } else {
                LOG.info("创建促销失败, pmInfoId:" + cityPickPromCreateVo.getPmInfoId() + "失败原因："
                         + cityPickPromCreateVo.getDescription());
            }

        }

        try {
            cityPickPriceApp.batchUpdateConfirmedPrice(confirmedPriceVos);
        } catch (Exception ex) {
            LOG.error("cityPickPriceApp.batchUpdateConfirmedPrice", ex);
        }

    }

    private int getDataCollectedDays() {
        int dataCollectedDays = 2;
        try {
            BusyMiscConfigVo busyMiscConfigVo = busyMiscConfigDao
                    .getBusyMiscConfigVoByKey(CITY_PICK_DATA_COLLECTED_DAYS_KEY);
            if(busyMiscConfigVo != null) {
                dataCollectedDays = Integer.valueOf(busyMiscConfigVo.getItemValue());
            }

        } catch (Exception ex) {
            LOG.error("busyMiscConfigDao.getBusyMiscConfigVoByKey", ex);
        }

        return dataCollectedDays;
    }

    public BusyMiscConfigDao getBusyMiscConfigDao() {
        return busyMiscConfigDao;
    }

    public void setBusyMiscConfigDao(BusyMiscConfigDao busyMiscConfigDao) {
        this.busyMiscConfigDao = busyMiscConfigDao;
    }
}
