package com.yhd.gps.busyservice.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newheight.hessianService.model.aiCitySelect.CityPriceInfo;
import com.newheight.hessianService.service.AimisProductService;
import com.newheight.support.EdmServiceContainer;
import com.yhd.gps.busyservice.service.AdvicePriceService;
import com.yhd.gps.busyservice.service.CityPickPriceService;
import com.yhd.gps.schedule.common.Command;
import com.yhd.gps.schedule.common.CommonUtils;
import com.yhd.gps.schedule.common.ExecutorManager;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleContext;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yihaodian.backend.price.base.ResultDto;
import com.yihaodian.backend.price.cityPick.application.CityPickPriceApp;
import com.yihaodian.backend.price.cityPick.vo.ConfirmedPriceVo;
import com.yihaodian.backend.price.cityPick.vo.EmailConfigVo;
import com.yihaodian.backend.price.cityPick.vo.EmailTypeVo;
import com.yihaodian.backend.price.cityPick.vo.PmSensitiveInfoVo;
import com.yihaodian.backend.price.cityPick.vo.QuerySensitiveRequest;
import com.yihaodian.backend.price.cityPick.vo.ScheduleQueryVo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleVo;
import com.yihaodian.backend.price.cityPick.vo.SensitivePageVo;
import com.yihaodian.backend.price.cityPick.vo.SuggPriceAndTypeVo;
import com.yihaodian.backend.price.cityPick.vo.SuggestedPriceVo;
import com.yihaodian.backend.price.common.BackendPriceHedwigServiceFactory;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.client.facade.BusyPriceFacadeService;
import com.yihaodian.front.busystock.vo.BSPmPriceVo;
import com.yihaodian.pis.service.api.SixthDaysPriceService;
import com.yihaodian.pis.service.api.vo.OpponSiteBasePrice;
import com.yihaodian.ycm.dcenter.util.mailSender.SendUtil;

/**
 * ---- 请加注释 ------
 * @Author  shengxu1
 * @CreateTime  2017-4-28 下午04:34:29
 */
public class AdvicePriceServiceImpl implements AdvicePriceService{

    private static final Log logger = LogFactory.getLog(AdvicePriceServiceImpl.class);
    
    // 价格后台远程Service
    private static final CityPickPriceApp cityPickPriceApp = BackendPriceHedwigServiceFactory.getCityPickPriceApp();
    
    //  AI建议价
    private static final AimisProductService aimisProductService = EdmServiceContainer.getInstance().getService(AimisProductService.class);
    
    // GPS接口
    private static final BusyPriceFacadeService busyPriceFacadeService  = BusyStockClientUtil.getBusyPriceFacadeService();
    
    private CityPickPriceService cityPickPriceService;
    
    private SixthDaysPriceService sixthDaysPriceService;
    
    public CityPickPriceService getCityPickPriceService() {
        return cityPickPriceService;
    }

    public void setCityPickPriceService(CityPickPriceService cityPickPriceService) {
        this.cityPickPriceService = cityPickPriceService;
    }

    public SixthDaysPriceService getSixthDaysPriceService() {
        return sixthDaysPriceService;
    }

    public void setSixthDaysPriceService(SixthDaysPriceService sixthDaysPriceService) {
        this.sixthDaysPriceService = sixthDaysPriceService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ConfirmedPriceVo> getScheduleConfirmedPrice(int shardingIndex, Integer pageSize) {
        List<ConfirmedPriceVo> confirmedPriceVos = null;
        if(shardingIndex <= 0 || pageSize == null){
            return confirmedPriceVos;
        }
        
        //  获取所有的scheduleIds放入上下文
        Set<Long> scheduleIds = (Set<Long>) ScheduleContext.getValue("scheduleIds");
        if(scheduleIds == null){
            scheduleIds = new HashSet<Long>();
            ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
            Calendar c = Calendar.getInstance();
            //  获取配置的天数值
            List<Long> list = MiscConfigUtils.getConfigListByItemKey("CITY_PICK_MODE_OVER_DAYS");
            Integer days = 2;
            if(CollectionUtils.isNotEmpty(list)) {
                days = list.get(0).intValue();
            } else {
                logger.error("####查询字典表获取众数价计算完成时间天数没有返回值！！给了一个默认值2####");
            }
            c.add(Calendar.DATE,-days);    // 获取两天之前的时间
            scheduleQueryVo.setScheduleProcessingTime(c.getTime());
            scheduleQueryVo.setShardingIndex(shardingIndex);
            List<Integer> status = new ArrayList<Integer>();
            // 档期状态为"众数价计算完成"
            status.add(ScheduleConstants.SCHEDULE_STATUS_PRICE_COMPUTED);
            scheduleQueryVo.setStatus(status);
            ResultDto<List<ScheduleVo>> scheduleRes = cityPickPriceApp.getCityPickPriceSchedule4Schedule(scheduleQueryVo);
            if(scheduleRes.getStatus() == 1){
                List<ScheduleVo> schedules = scheduleRes.getResult();
                if(schedules != null){
                    for(ScheduleVo scheduleVo : schedules){
                        scheduleIds.add(scheduleVo.getId());
                    }
                }
            }
            ScheduleContext.setValue("scheduleIds", scheduleIds);
        }
        
        if(CollectionUtils.isEmpty(scheduleIds)){
            return confirmedPriceVos;
        }
        
        //  获取档期商品数据(只有自动定价档期的)
        ResultDto<List<ConfirmedPriceVo>> res = cityPickPriceApp.getConfirmedPriceWithoutAdvicePriceByScheduleId(new ArrayList<Long>(scheduleIds),ScheduleConstants.PRODUCT_SOURCE_TYPE_AUTO, pageSize);
        if(res.getStatus() == 1){
            confirmedPriceVos = res.getResult();
        }
        return confirmedPriceVos;
    }

    @Override
    public ResultDto<Boolean> handleConfirmedPrice(List<ConfirmedPriceVo> confirmedPriceVos) {
        logger.error("confirmedPriceVos的大小为：" + confirmedPriceVos.size());
        ResultDto<Boolean> res = new ResultDto<Boolean>();
        res.setResult(false);
        if(CollectionUtils.isEmpty(confirmedPriceVos)){
            return res;
        }
        
        //  按scheduleId分组
        Map<Long,List<ConfirmedPriceVo>> schedule2ConfirmedPrice = new HashMap<Long,List<ConfirmedPriceVo>>();
        for(ConfirmedPriceVo confirmedPriceVo : confirmedPriceVos){
            Long scheduleId = confirmedPriceVo.getPriceScheduleId();
            List<ConfirmedPriceVo> confirmedPriceVoList = schedule2ConfirmedPrice.get(scheduleId);
            if(confirmedPriceVoList == null){
                confirmedPriceVoList = new ArrayList<ConfirmedPriceVo>();
                schedule2ConfirmedPrice.put(scheduleId, confirmedPriceVoList);
            }
            confirmedPriceVoList.add(confirmedPriceVo);
        }
        
        for(Map.Entry<Long, List<ConfirmedPriceVo>> entry : schedule2ConfirmedPrice.entrySet()){
            final Long scheduleId = entry.getKey();
            List<ConfirmedPriceVo> confirmedPriceList = entry.getValue();
            //  分组调用PIS接口(下面调用PIS接口不能超过200,AI接口不能超过100)
            List<List<ConfirmedPriceVo>> splitConfirmedPriceList = GPSUtils.split(confirmedPriceList, ScheduleConstants.MAX_SIZE);
            if(CollectionUtils.isNotEmpty(splitConfirmedPriceList)){
                for(List<ConfirmedPriceVo> subConfirmedPriceList : splitConfirmedPriceList){
                    Map<Long,ConfirmedPriceVo> pmId2ConfirmedPrice = new HashMap<Long,ConfirmedPriceVo>();
                    List<Long> pmIds = new ArrayList<Long>();
                    for(ConfirmedPriceVo confirmedPriceVo : subConfirmedPriceList){
                        pmId2ConfirmedPrice.put(confirmedPriceVo.getPmInfoId(), confirmedPriceVo);
                        pmIds.add(confirmedPriceVo.getPmInfoId());
                    }
                    if(CollectionUtils.isNotEmpty(pmIds)){
                        Map<Long,OpponSiteBasePrice> tMallPmId2Price = queryOpponBasePriceByPmIds(pmIds, ScheduleConstants.TMALL_NANJING_SITE);
                        Map<Long,OpponSiteBasePrice> suNingPmId2Price = queryOpponBasePriceByPmIds(pmIds, ScheduleConstants.SUNING_NANJING_SITE);

                        //  调AI接口获取建议价
                        Map<Long,CityPriceInfo> aiPriceMap = new HashMap<Long,CityPriceInfo>(pmIds.size());
                        try {
                            List<CityPriceInfo> aiPriceList = aimisProductService.queryComputedProInfos(scheduleId,pmIds);
                            if(CollectionUtils.isNotEmpty(aiPriceList)){
                                for(CityPriceInfo cityPriceInfo : aiPriceList){
                                    if(cityPriceInfo == null){
                                        continue;
                                    }
                                    aiPriceMap.put(cityPriceInfo.getPmInfoId(), cityPriceInfo);
                                }
                            }
                        } catch (Exception e) {
                            logger.error("###调用AI接口获取建议价发生异常！####");
                        }
                        
                        List<SuggestedPriceVo> suggePriceList = new ArrayList<SuggestedPriceVo>();
                        for(Long pmId : pmIds){
                            ConfirmedPriceVo confirmedPriceVo = pmId2ConfirmedPrice.get(pmId);
                            if(confirmedPriceVo != null){
                                OpponSiteBasePrice tMallopponSiteBasePrice = tMallPmId2Price.get(pmId);
                                if(tMallopponSiteBasePrice != null){
                                    SuggestedPriceVo tMallPagePriceVo = buildSuggestedPriceVoByType(ScheduleConstants.PRICE_TYPE_TMALL_PAGE_PRICE,pmId,confirmedPriceVo,tMallopponSiteBasePrice);
                                    SuggestedPriceVo tMallLowPriceVo = buildSuggestedPriceVoByType(ScheduleConstants.PRICE_TYPE_TMALL_FLOOR_PRICE,pmId,confirmedPriceVo,tMallopponSiteBasePrice);
                                    suggePriceList.add(tMallPagePriceVo);
                                    suggePriceList.add(tMallLowPriceVo);
                                }
                                OpponSiteBasePrice suNingOpponSiteBasePrice = suNingPmId2Price.get(pmId);
                                if(suNingOpponSiteBasePrice != null){
                                    SuggestedPriceVo suNingPagePriceVo = buildSuggestedPriceVoByType(ScheduleConstants.PRICE_TYPE_SUNING_PAGE_PRICE,pmId,confirmedPriceVo,suNingOpponSiteBasePrice);
                                    SuggestedPriceVo suNingLowPriceVo = buildSuggestedPriceVoByType(ScheduleConstants.PRICE_TYPE_SUNING_FLOOR_PRICE,pmId,confirmedPriceVo,suNingOpponSiteBasePrice);
                                    suggePriceList.add(suNingPagePriceVo);
                                    suggePriceList.add(suNingLowPriceVo);
                                }
                                CityPriceInfo aiCityPrice = aiPriceMap.get(pmId);
                                if(aiCityPrice != null){
                                    SuggestedPriceVo aiPriceVo = buildSuggestedPriceVoByType(ScheduleConstants.PRICE_TYPE_AI_MODEL_PRICE,pmId,confirmedPriceVo,aiCityPrice);
                                    suggePriceList.add(aiPriceVo);
                                }
                            }
                        }
                        //  批量插入建议价
                        if(CollectionUtils.isNotEmpty(suggePriceList)){
                            cityPickPriceApp.addSuggestedPrice(suggePriceList);
                        }
                        
                        try {
                            Map<Long,BSPmPriceVo> pmId2Price = new HashMap<Long,BSPmPriceVo>();
                            //  pmId对应基准价的map
                            List<BSPmPriceVo> bsPmPrices = busyPriceFacadeService.batchGetPmPriceVoListWithCache(pmIds);
                            if(CollectionUtils.isNotEmpty(bsPmPrices)){
                                for(BSPmPriceVo bsPmPriceVo : bsPmPrices){
                                    if(bsPmPriceVo == null){
                                        continue;
                                    }
                                    pmId2Price.put(bsPmPriceVo.getPmId(), bsPmPriceVo);
                                }
                            }
                            
                            Map<Long, SuggPriceAndTypeVo> compSuggRes = cityPickPriceApp.computeRecommendedPrice(scheduleId, pmIds);
                            for(ConfirmedPriceVo confirmedPriceVo : subConfirmedPriceList){
                                Long pmId = confirmedPriceVo.getPmInfoId();
                                SuggPriceAndTypeVo suggPrice = compSuggRes.get(pmId);
                                if(suggPrice != null){
                                    confirmedPriceVo.setAdvicedPrice(suggPrice.getAdvicedPrice());
                                    confirmedPriceVo.setAdvicedPriceSource(suggPrice.getAdvicedPriceSource());
									//在数据汇总前，商品的状态为空，此时更新成待审核
                                    confirmedPriceVo.setPriceStatus(0);
                                    //  获取商品基准价信息
                                    BSPmPriceVo bsPmPriceVo = pmId2Price.get(pmId);
                                    if(bsPmPriceVo != null){
                                        //  建议价比基准价低，就写入到促销价中
                                        BigDecimal nonMemberPrice = bsPmPriceVo.getNonMemberPrice();
                                        BigDecimal advicedPrice = suggPrice.getAdvicedPrice();
                                        if(advicedPrice != null && nonMemberPrice != null 
                                                && (advicedPrice.compareTo(nonMemberPrice) == -1)){
                                            confirmedPriceVo.setActivePrice(advicedPrice);
                                        }
                                    }
                                }
                            }
                            //  更新定价表
                            cityPickPriceApp.batchUpdateConfirmedPrice(subConfirmedPriceList);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("计算建议价失败，参数为：pmIds=" + pmIds,e);
                        }
                    }
                }
            }
            
            //  检查当前档期的建议价是否已经全部计算完成
            List<Long> schedules = new ArrayList<Long>();
            schedules.add(scheduleId);
            ResultDto<List<ConfirmedPriceVo>> scheRes = cityPickPriceApp.getConfirmedPriceWithoutAdvicePriceByScheduleId(schedules,ScheduleConstants.PRODUCT_SOURCE_TYPE_AUTO, 1);
            if(CollectionUtils.isEmpty(scheRes.getResult())){
                //  更新档期状态为汇总数据完毕
                List<Long> scheduleIds = new ArrayList<Long>();
                scheduleIds.add(scheduleId);
                ResultDto<Map<Long, ScheduleVo>>  scheduleRes = cityPickPriceApp.getCityPickPriceScheduleByIds(scheduleIds);
                if(scheduleRes.getStatus() == 1){
                    Map<Long, ScheduleVo> schedulemap = scheduleRes.getResult();
                    for(Map.Entry<Long, ScheduleVo> e : schedulemap.entrySet()){
                        ScheduleVo schedule = e.getValue();
                        if(schedule != null){
                            schedule.setStatus(ScheduleConstants.SCHEDULE_STATUS_PRICE_COLLECTED);
                            schedule.setDataCollectedTime(new Date());
                            cityPickPriceApp.updatePriceScheduleVo(schedule);
                        }
                    }
                }
                
                ExecutorManager.executeOnly(new Command<Object>() {
                    @Override
                    public Object action() {
                        //  档期处理完毕获取档期信息发送邮件
                        List<Long> schedules = new ArrayList<Long>();
                        schedules.add(scheduleId);
                        ResultDto<Map<Long, ScheduleVo>> scheduleRes = cityPickPriceApp.getCityPickPriceScheduleByIds(schedules);
                        if(scheduleRes.getStatus() == 1){
                            Map<Long, ScheduleVo> scheduleMap = scheduleRes.getResult();
                            if(scheduleMap.size() > 0 ){
                                ScheduleVo scheduleVo = scheduleMap.get(scheduleId);
                                if(scheduleVo != null){
                                    sendEmail(scheduleVo);
                                }
                            }
                        }
                        return null;
                    }
                });
            }
        }
        return res;
    }
    
    public ResultDto<Boolean> sendEmail(ScheduleVo scheduleVo){
        ResultDto<Boolean> res = new ResultDto<Boolean>();
        res.setResult(false);
        if(scheduleVo == null){
            return res;
        }
        Long emailId = null;
        String emailContent = "";
        String emailSubject = "";
        //  获取邮件内容和主题
        EmailTypeVo emailTypeVo = cityPickPriceService.findAllEmailByType(ScheduleConstants.SCHEDULE_TO_AUDIT_EMAIL_TYPE);
        if(emailTypeVo != null){
            emailContent = emailTypeVo.getEmailContent();
            emailSubject = emailTypeVo.getEmailSubject();
            emailId = emailTypeVo.getId();
        }
        
        //  获取地址信息
        Long merchantId = scheduleVo.getMerchantId();
        ResultDto<List<EmailConfigVo>> configRes = cityPickPriceApp.findEmailConfigByMerchantId(merchantId, emailId);
        if(configRes.getStatus() == 0){
            return res;
        }
        String merchantName = CommonUtils.queryMerchantName(merchantId);
        List<EmailConfigVo> configVos = configRes.getResult();
        if(CollectionUtils.isNotEmpty(configVos)){
            EmailConfigVo emailConfigVo = configVos.get(0);
            String emailAddress = emailConfigVo.getEmailAddress();
            //  转化mail信息
            String startTime = ScheduleDateUtils.format(scheduleVo.getStartTime(),ScheduleDateUtils.DEFAULT_DATE_PATTERN);
            String endTime = ScheduleDateUtils.format(scheduleVo.getEndTime(),ScheduleDateUtils.DEFAULT_DATE_PATTERN);
            String formatContent = String.format(emailContent, new Object[]{merchantName,scheduleVo.getName(),startTime+"~"+endTime});
            String formatSubject = String.format(emailSubject, new Object[]{merchantName});
            //  发送邮件
            String emailResult = SendUtil.sendInnerEmail(emailAddress, formatContent, formatSubject);
            if (!emailResult.equals("00")) {
                logger.error("发送邮件失败,状态码" + emailResult + ",收件人:" + emailAddress);
            }
            res.setResult(true);
            return res;
        }
        return res;
    }
    
    /**
     * 封装建议价VO
     * 
     * */
    private <T> SuggestedPriceVo buildSuggestedPriceVoByType(int type, Long pmId, ConfirmedPriceVo confirmedPriceVo, T t){
        if(t == null){
            return null;
        }
        SuggestedPriceVo suggestedPriceVo = new SuggestedPriceVo();
        suggestedPriceVo.setPriceScheduleId(confirmedPriceVo.getPriceScheduleId());
        suggestedPriceVo.setPmInfoId(pmId);
        suggestedPriceVo.setProductId(confirmedPriceVo.getProductId());
        suggestedPriceVo.setMerchantId(confirmedPriceVo.getMerchantId());
        suggestedPriceVo.setPriceType(type);
        
        Float minPrice = null;
        Float normalPrice = null;
        Double advicedPrice = null;
        switch(type) {
            case 1:
            case 3:
                suggestedPriceVo.setSourceType(1);      //  来源为PIS
                minPrice = ((OpponSiteBasePrice)t).getComomBasePrice();
                if(minPrice >= 0){
                    suggestedPriceVo.setMinPrice(new BigDecimal(minPrice));
                }
                normalPrice = ((OpponSiteBasePrice)t).getPrice();
                if(normalPrice >= 0){
                    suggestedPriceVo.setNormalPrice(new BigDecimal(normalPrice));
                }
                break;
            case 2:
            case 4:
                suggestedPriceVo.setSourceType(1);      //  来源为PIS
                minPrice = ((OpponSiteBasePrice)t).getLowestBasePrice();
                if(minPrice >= 0){
                    suggestedPriceVo.setMinPrice(new BigDecimal(minPrice));
                }
                normalPrice = ((OpponSiteBasePrice)t).getOpponLowestPrice();
                if(normalPrice >= 0){
                    suggestedPriceVo.setNormalPrice(new BigDecimal(normalPrice));
                }
                break;
            case 7:
                suggestedPriceVo.setSourceType(2);      //  来源为AI
                advicedPrice = ((CityPriceInfo)t).getRecommendedPrice();
                suggestedPriceVo.setAdvicedPrice(new BigDecimal(advicedPrice));
                break;
        }
        return suggestedPriceVo;
    }

    @Override
    public List<SensitivePageVo> getPmSensitiveInfoVoByCondition(int shardingIndex, Integer pageSize, Integer currentPage)
    {
        List<SensitivePageVo> sensitivePageVos = null;
        QuerySensitiveRequest querySensitiveRequest = new QuerySensitiveRequest();
        querySensitiveRequest.setPageSize(Long.valueOf(pageSize));
        querySensitiveRequest.setCurrentPage(Long.valueOf(currentPage));
        querySensitiveRequest.setShardingIndex(shardingIndex);
        querySensitiveRequest.setSensitiveType(ScheduleConstants.SENSITIVE_TYPE_CITY_PICK);
        querySensitiveRequest.setStatus(0);

        ResultDto<List<SensitivePageVo>> sensitiveResult =cityPickPriceApp.batchGetPmSensitiveInfoVoByCondition(
                querySensitiveRequest);
        if (ResultDto.STATUS_SUCCESS.equals(sensitiveResult.getStatus()))
        {
            sensitivePageVos = sensitiveResult.getResult();
        }
        return sensitivePageVos;
    }

    /**
     * 调PIS接口获取外部价格
     * siteId：猫超南京：1162，苏宁南京：1099
     * merchantId：默认传1（针对1162,1099适用）
     * */
    @Override
    //TODO 这里site和merchant先做成可配置的
    public Map<Long, OpponSiteBasePrice> queryOpponBasePriceByPmIds(List<Long> pmIds, Integer siteId)
    {
        Map<Long, OpponSiteBasePrice> retMap = new HashMap<Long, OpponSiteBasePrice>();
        try {
            List<OpponSiteBasePrice> opponSiteBasePrices = sixthDaysPriceService.queryOpponBasePriceByPmIds(pmIds, siteId, 1);
            if(CollectionUtils.isNotEmpty(opponSiteBasePrices)){
                for(OpponSiteBasePrice OpponSiteBasePrice : opponSiteBasePrices){
                    retMap.put(OpponSiteBasePrice.getPmId(), OpponSiteBasePrice);
                }
            }
        } catch (Exception ex)
        {
            logger.error("sixthDaysPriceService.queryOpponBasePriceByPmIds", ex);
        }
        return retMap;
    }

    /**
     * 从定价表中获取PIS价格
     * @param pmIds
     * @return
     */
    @Override
    public Map<Long, BigDecimal> queryAiPriceByPmIds(List<Long> pmIds) {
        return cityPickPriceApp.getAiSuggestPriceByPmIds(pmIds);
    }

    @Override
    public void updateSensitivePrice(List<SensitivePageVo> sensitivePageVos) {
        List<PmSensitiveInfoVo> pmSensitiveInfos = transformSensitive(sensitivePageVos);
        List<List<PmSensitiveInfoVo>> subLists = GPSUtils.split(pmSensitiveInfos, ScheduleConstants.MAX_SIZE);
        for (List<PmSensitiveInfoVo> subList : subLists)
        {
            try {
                ResultDto<Integer> resultDto = cityPickPriceApp.batchUpdatePmSensitiveInfoVoByPmIds(subList);
                if (resultDto == null || !ResultDto.STATUS_SUCCESS.equals(resultDto.getStatus()))
                {
                    logger.error("error return when cityPickPriceApp.batchUpdatePmSensitiveInfoVoByPmIds:"
                            +(resultDto == null ? "" : resultDto.getErrMsg()));
                }
            } catch (Exception ex)
            {
                logger.error("cityPickPriceApp.batchUpdatePmSensitiveInfoVoByPmIds", ex);
            }
        }

    }

    public List<PmSensitiveInfoVo> transformSensitive(List<SensitivePageVo> sensitivePageVos)
    {
        List<PmSensitiveInfoVo> pmSensitiveInfos = new ArrayList<PmSensitiveInfoVo>();
        for (SensitivePageVo sensitivePageVo : sensitivePageVos)
        {
            PmSensitiveInfoVo pmSensitiveInfo = new PmSensitiveInfoVo();
            pmSensitiveInfo.setSensitiveMaxPrice(sensitivePageVo.getSensitiveMaxPrice());
            pmSensitiveInfo.setSensitiveMinPrice(sensitivePageVo.getSensitiveMinPrice());
            pmSensitiveInfo.setPmId(sensitivePageVo.getPmId());
            pmSensitiveInfo.setSourceType(sensitivePageVo.getSourceType());
            pmSensitiveInfo.setSensitiveType(sensitivePageVo.getSensitiveType());
            pmSensitiveInfo.setId(sensitivePageVo.getId());
            pmSensitiveInfo.setUpdateTime(new Date());
            pmSensitiveInfo.setUpdatorId(2L);
            pmSensitiveInfo.setMerchantId(sensitivePageVo.getMerchantId());
            pmSensitiveInfos.add(pmSensitiveInfo);
        }

        return pmSensitiveInfos;
    }
}

