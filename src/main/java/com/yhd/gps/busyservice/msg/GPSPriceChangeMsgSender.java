package com.yhd.gps.busyservice.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.yhd.gps.busy.mail.BusyMailSendWithTempletUtil;
import com.yhd.gps.busy.mail.BusyMailUtil;
import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.schedule.common.BusyCacheProxy;
import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleCacheConstants;
import com.yhd.gps.schedule.common.ScheduleCacheKeyUtil;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleContext;
import com.yhd.gps.schedule.exception.GpsScheduleBusinessException;
import com.yhd.gps.schedule.vo.DistintcPriceChangCache;
import com.yhd.gps.schedule.vo.JumperMessageLog;
import com.yhd.gps.schedule.vo.MailTempletVo;
import com.yihaodian.busy.common.BusyConstants;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.busy.vo.AreaPriceFatMessageVo;
import com.yihaodian.busy.vo.BusyAreaVo;
import com.yihaodian.busy.vo.BusyPmAreaPriceVo;
import com.yihaodian.busy.vo.BusyPmPriceVo;
import com.yihaodian.busy.vo.BusyPriceChangeMsgVo;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.busy.vo.PriceFatMessageVo;
import com.yihaodian.busy.vo.builder.AreaPriceFatMessageVoBuilder;
import com.yihaodian.busy.vo.builder.HistoryPriceChangeMsgVoBuilder;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.client.facade.BusyAreaPriceFacadeService;
import com.yihaodian.front.busystock.client.facade.BusyPriceFacadeService;
import com.yihaodian.front.busystock.vo.BSPmAreaPriceVo;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;
import com.yihaodian.front.databus.DatabusConstants;
import com.yihaodian.front.databus.client.FrontDatabusClient;

/**
 * GPS价格变化消息发送器
 * @Author xuyong
 * @CreateTime 2015-7-21 上午10:55:52
 */
public class GPSPriceChangeMsgSender extends MsgSender<PriceFatMessageVo> implements BeanFactoryAware {
    private static final Log LOGGER = LogFactory.getLog(GPSPriceChangeMsgSender.class);
    public static final Integer MSG_SIZE = 100;
    private static BusyPriceFacadeService busyPriceFacadeService = BusyStockClientUtil.getBusyPriceFacadeService();
    private static BusyAreaPriceFacadeService busyAreaPriceFacadeService = BusyStockClientUtil
            .getBusyAreaPriceFacadeService();
    private BusyPmInfoDao busyPmInfoDao;
    protected BusyCacheProxy cacheProxy;

    public void setBusyPmInfoDao(BusyPmInfoDao busyPmInfoDao) {
        this.busyPmInfoDao = busyPmInfoDao;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        cacheProxy = (BusyCacheProxy) beanFactory.getBean(ScheduleCacheConstants.BUSY_DATA_CACHE_BEAN_NAME);
    }

    public Integer send(List<BusyPriceChangeMsgVo> priceChangeMsgs, final Set<Long> sendedMsgIds) {
        if(CollectionUtils.isEmpty(priceChangeMsgs)) {
            return 0;
        }
        try {
            Set<Long> pmInfoIdSet = new HashSet<Long>(priceChangeMsgs.size(), 1F);
            Set<Long> priceChangeMsgIds = new HashSet<Long>(priceChangeMsgs.size(), 1F);

            Map<Long, BusyPriceChangeMsgVo> priceChangeMsgMap = new HashMap<Long, BusyPriceChangeMsgVo>(
                priceChangeMsgs.size(), 1F);
            for(BusyPriceChangeMsgVo priceChangeMsg : priceChangeMsgs) {
                priceChangeMsgIds.add(priceChangeMsg.getId());

                pmInfoIdSet.add(priceChangeMsg.getPmInfoId());
                priceChangeMsgMap.put(priceChangeMsg.getPmInfoId(), priceChangeMsg);
            }
            // 求出组合商品主品pmInfoId
            List<Long> mainPmInfoIds = busyPmInfoDao
                    .batchGetMainCombinePmIdBySubPmIds(new ArrayList<Long>(pmInfoIdSet));
            if(CollectionUtils.isNotEmpty(mainPmInfoIds)) {
                // 过滤pmId为null的情况
                for(Long mainPmInfoId : mainPmInfoIds) {
                    if(mainPmInfoId != null) {
                        pmInfoIdSet.add(mainPmInfoId);
                    }
                }
            }
            List<Long> pmIds = new ArrayList<Long>(pmInfoIdSet);
            sendLitePriceChangeMsg(pmIds);
            sendFatPriceChangeMsg(pmIds, priceChangeMsgMap);
            // 发送区域促销价变价消息
            sendAreaPmPriceMessageJson(pmIds, priceChangeMsgMap);
            // 将处理发送的priceChangeMsg的id放入sendedMsgIds，在后续更新priceChangeMsg状态为已发送
            sendedMsgIds.addAll(priceChangeMsgIds);
            return priceChangeMsgs.size();
        } catch (Exception e) {
            LOGGER.error("发送价格变化消息发生异常, error : " + e.getMessage(), e);
        }
        return 0;
    }

    private void sendLitePriceChangeMsg(List<Long> pmIds) {
        try {
            FrontDatabusClient.publishPriceChange(pmIds);
        } catch (Exception e) {
            LOGGER.error("---##---发送价格变化的pmId发生异常--##---" + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private void sendFatPriceChangeMsg(List<Long> pmIds, Map<Long, BusyPriceChangeMsgVo> priceChangeMsgMap) {
        // 查询GPS获取价格
        List<PriceFatMessageVo> messageVos = new ArrayList<PriceFatMessageVo>();
        try {
            List<BusyPmPriceVo> pmPriceVos = batchQueryPmPriceVos(pmIds, priceChangeMsgMap);
            if(CollectionUtils.isEmpty(pmPriceVos)) {
                return;
            }
            List<PriceFatMessageVo> priceFatMessages = new ArrayList<PriceFatMessageVo>(pmPriceVos.size());
            Map<Long, Long> promRule2ChannelIdMap = (Map<Long, Long>) ScheduleContext
                    .getValue(ScheduleConstants.PRODUCT_PROM_RULE_CHANEL_MAP);
            Map<Long, BusyAreaVo> promRule2AreaMap = (Map<Long, BusyAreaVo>) ScheduleContext
                    .getValue(ScheduleConstants.PRODUCT_PROM_RULE_AREA_MAP);
            for(BusyPmPriceVo pmPriceVo : pmPriceVos) {
                BusyPriceChangeMsgVo priceChangeVo = priceChangeMsgMap.get(pmPriceVo.getPmId());
                Long channelId = null;
                BusyAreaVo areaVo = null;
                if(MapUtils.isNotEmpty(promRule2ChannelIdMap)) {
                    channelId = promRule2ChannelIdMap.get(priceChangeVo.getMsgPriceId());
                }
                if(MapUtils.isNotEmpty(promRule2AreaMap)) {
                    areaVo = promRule2AreaMap.get(priceChangeVo.getMsgPriceId());
                }
                PriceFatMessageVo priceFatMessage = distinctPriceChangeMsg(pmPriceVo, priceChangeVo, channelId, areaVo);
                if(null != priceFatMessage) {
                    priceFatMessages.add(priceFatMessage);
                }
            }
            if(CollectionUtils.isNotEmpty(priceFatMessages)) {
                List<List<PriceFatMessageVo>> lists = GPSUtils.split(priceFatMessages, MSG_SIZE);
                for(List<PriceFatMessageVo> list : lists) {
                    messageVos = list;
                    // 发送胖消息
                    FrontDatabusClient.publishPriceChangeJson(list, "PRICE_RULE_CHANGE");
                    // 记录历史价格
                    recordHistoryPriceChangeMsgs(list);
                    // 记录到Jumper消息日志表
                    saveJumperMessageLog(DatabusConstants.GPS_HISTORY_PRICE, list, "PRICE_RULE_CHANGE");
                }
                LOGGER.info("###发送价格变动胖消息打印到日志，便于后续丢消息排查：" + priceFatMessages + "###");
            }
        } catch (Exception e) {
            // 数据丢失发送邮件
            sendEmail(messageVos, e.getMessage());
            LOGGER.error("###发送价格变化的胖消息发生异常###" + e.getMessage(), e);
            throw new GpsScheduleBusinessException(e.getMessage(), e);
        } finally {
            ScheduleContext.reset();
        }
    }

    private List<BSProductPromRuleVo> filterProductPromRuleVos(Map<Long, BusyPriceChangeMsgVo> priceChangeMsgMap) {
        List<Long> promRuleIds = new ArrayList<Long>();
        for(Map.Entry<Long, BusyPriceChangeMsgVo> entry : priceChangeMsgMap.entrySet()) {
            BusyPriceChangeMsgVo busyPriceChangeMsgVo = entry.getValue();
            Long promRuleId = busyPriceChangeMsgVo.getMsgPriceId();
            // 如果是价格促销改价，就获取到生效的促销Id(目前基准价修改记录不存在变动表中)
            if(promRuleId != null
               && ScheduleConstants.MSG_TYPE_PROMOTION_LIST.contains(busyPriceChangeMsgVo.getMsgType())) {
                promRuleIds.add(promRuleId);
            }
        }

        // 存在要查询的促销Id,根据促销Id获取促销信息
        List<BSProductPromRuleVo> productPromRuleVos = new ArrayList<BSProductPromRuleVo>(promRuleIds.size());
        if(CollectionUtils.isNotEmpty(promRuleIds)) {
            List<List<Long>> splitPromRuleIds = GPSUtils.split(promRuleIds, MSG_SIZE);
            // 分批调用查询促销信息
            for(List<Long> ruleIds : splitPromRuleIds) {
                List<BSProductPromRuleVo> splitPromRuleVos = busyPriceFacadeService
                        .batchGetProductPromRuleVoListByRuleId(ruleIds);
                if(CollectionUtils.isEmpty(splitPromRuleVos)) {
                    continue;
                }
                productPromRuleVos.addAll(splitPromRuleVos);
            }
        }
        return productPromRuleVos;
    }

    private void sendAreaPmPriceMessageJson(List<Long> pmIds, Map<Long, BusyPriceChangeMsgVo> priceChangeMsgMap) {
        try {
            List<BSProductPromRuleVo> productPromRuleVos = filterProductPromRuleVos(priceChangeMsgMap);
            // 促销价修改可能影响区域价
            if(CollectionUtils.isEmpty(productPromRuleVos)) {
                return;
            }
            // 根据pmIds、促销Id反查区域表是否存在该促销，存在则发送区域变动消息
            Map<Long, List<BSPmAreaPriceVo>> pmId4PmAreaPriceVo = busyAreaPriceFacadeService
                    .getPmAreaPriceVoListMapByPmIds(pmIds);
            if(MapUtils.isEmpty(pmId4PmAreaPriceVo)) {
                return;
            }
            List<List<BSProductPromRuleVo>> splitProductPromRuleVos = GPSUtils.split(productPromRuleVos, MSG_SIZE);
            for(List<BSProductPromRuleVo> _splitProductPromRuleVos : splitProductPromRuleVos) {
                List<AreaPriceFatMessageVo> areaPriceFatMessageVos = new ArrayList<AreaPriceFatMessageVo>(
                    _splitProductPromRuleVos.size());
                for(BSProductPromRuleVo productPromRuleVo : _splitProductPromRuleVos) {
                    Long ruleId = productPromRuleVo.getId();
                    Long pmId = productPromRuleVo.getPmId();
                    List<BSPmAreaPriceVo> _pmAreaPriceVos = pmId4PmAreaPriceVo.get(pmId);
                    if(CollectionUtils.isEmpty(_pmAreaPriceVos)) {
                        continue;
                    }
                    // 取变价消息类型
                    BusyPriceChangeMsgVo busyPriceChangeMsgVo = priceChangeMsgMap.get(pmId);
                    Integer msgType = null;
                    if(null != busyPriceChangeMsgVo) {
                        msgType = busyPriceChangeMsgVo.getMsgType();
                    }
                    for(BSPmAreaPriceVo pmAreaPriceVo : _pmAreaPriceVos) {
                        Long refPriceIdType = pmAreaPriceVo.getRefPriceIdType();
                        Long refPriceId = pmAreaPriceVo.getRefPriceId();
                        // 是区域促销类型且与该促销Id相等则发消息
                        if(refPriceIdType != null && ruleId != null
                           && BusyConstants.AREA_PRICE_PARENT_PRICE_ID_TYPE_PROMOTE == refPriceIdType.longValue()
                           && ruleId.equals(refPriceId)) {
                            BusyPmAreaPriceVo transform2BusyPmAreaPriceVo = AreaPriceFatMessageVoBuilder
                                    .transform2BusyPmAreaPriceVo(pmAreaPriceVo);
                            areaPriceFatMessageVos.add(AreaPriceFatMessageVoBuilder.fromPmAreaPriceVo(
                                    transform2BusyPmAreaPriceVo, msgType));
                        }
                    }
                    FrontDatabusClient.publishMessageJson(areaPriceFatMessageVos, BusyConstants.GPS_AREA_PRICE_CHANGED);
                    LOGGER.info("###发送区域价格变动胖消息打印到日志，便于后续丢消息排查：" + areaPriceFatMessageVos + "###");
                }
            }
        } catch (Exception e) {
            LOGGER.error("###发送区域价变价消息失败,接口publishAreaPmPriceMessageJson###" + e.getMessage(), e);
        }
    }

    /**
     * 根据pmIds、ChannelId批量获取价格
     * @param pmIds
     * @param priceChangeMsgMap
     * @return
     */
    private List<BusyPmPriceVo> batchQueryPmPriceVos(List<Long> pmIds, Map<Long, BusyPriceChangeMsgVo> priceChangeMsgMap) {
        // 按渠道分组
        Map<Long, List<Long>> channelId2PmIds = new HashMap<Long, List<Long>>();
        // 按区域和渠道分组
        Map<String, Map<Long, List<Long>>> area2ChannelId2PmIds = new HashMap<String, Map<Long, List<Long>>>();
        // 过滤出促销
        List<BSProductPromRuleVo> promRuleVos = filterProductPromRuleVos(priceChangeMsgMap);
        // 查询区域价格
        Map<Long, List<BSPmAreaPriceVo>> pmId4PmAreaPriceVo = busyAreaPriceFacadeService
                .getPmAreaPriceVoListMapByPmIds(pmIds);
        // 记录促销对应的渠道
        Map<Long, Long> promRule2ChannelIdMap = new HashMap<Long, Long>(promRuleVos.size());
        // 记录促销对应的区域
        Map<Long, BusyAreaVo> promRule2AreaMap = new HashMap<Long, BusyAreaVo>(promRuleVos.size());
        // 有区域的，按区域和渠道分组；没有区域的，按渠道分组
        for(BSProductPromRuleVo promRuleVo : promRuleVos) {
            if(promRuleVo == null) {
                continue;
            }
            Long channelId = promRuleVo.getChannelId();
            Long ruleId = promRuleVo.getId();
            StringBuilder areaKey = null;
            if(MapUtils.isNotEmpty(pmId4PmAreaPriceVo)
               && CollectionUtils.isNotEmpty(pmId4PmAreaPriceVo.get(promRuleVo.getPmId()))) {
                List<BSPmAreaPriceVo> _pmAreaPriceVos = pmId4PmAreaPriceVo.get(promRuleVo.getPmId());
                for(BSPmAreaPriceVo pmAreaPriceVo : _pmAreaPriceVos) {
                    Long refPriceIdType = pmAreaPriceVo.getRefPriceIdType();
                    Long refPriceId = pmAreaPriceVo.getRefPriceId();
                    // 是区域促销类型且与该促销Id相等则取出区域信息
                    if(refPriceIdType != null && ruleId != null
                       && BusyConstants.AREA_PRICE_PARENT_PRICE_ID_TYPE_PROMOTE == refPriceIdType.longValue()
                       && ruleId.equals(refPriceId)) {
                        areaKey = new StringBuilder();
                        areaKey.append(pmAreaPriceVo.getProvinceId() == null ? 0 : pmAreaPriceVo.getProvinceId());
                        areaKey.append("-");
                        areaKey.append(pmAreaPriceVo.getCityId() == null ? 0 : pmAreaPriceVo.getCityId());
                        areaKey.append("-");
                        areaKey.append(pmAreaPriceVo.getCountyId() == null ? 0 : pmAreaPriceVo.getCountyId());
                        break;
                    }
                }
            }
            if(areaKey == null) {
                List<Long> pmIdsByChannel = channelId2PmIds.get(channelId);
                if(pmIdsByChannel == null) {
                    pmIdsByChannel = new ArrayList<Long>();
                    channelId2PmIds.put(channelId, pmIdsByChannel);
                }
                pmIdsByChannel.add(promRuleVo.getPmId());
            } else {
                Map<Long, List<Long>> channelId2PmIdsByArea = area2ChannelId2PmIds.get(areaKey.toString());
                if(MapUtils.isEmpty(channelId2PmIdsByArea)) {
                    channelId2PmIdsByArea = new HashMap<Long, List<Long>>();
                    area2ChannelId2PmIds.put(areaKey.toString(), channelId2PmIdsByArea);
                }
                List<Long> pmIdsByChannel = channelId2PmIdsByArea.get(channelId);
                if(pmIdsByChannel == null) {
                    pmIdsByChannel = new ArrayList<Long>();
                    channelId2PmIdsByArea.put(channelId, pmIdsByChannel);
                }
                pmIdsByChannel.add(promRuleVo.getPmId());
                promRule2AreaMap.put(ruleId, buildAreaVo(areaKey.toString()));
            }
            promRule2ChannelIdMap.put(ruleId, channelId);
        }
        List<BusyPmPriceVo> pmPriceVos = new ArrayList<BusyPmPriceVo>(pmIds.size());
        // 获取不同渠道下商品价格
        for(Map.Entry<Long, List<Long>> entry : channelId2PmIds.entrySet()) {
            Long channelId = entry.getKey();
            List<Long> pmIdsByChannel = entry.getValue();
            if(CollectionUtils.isNotEmpty(pmIdsByChannel)) {
                List<BusyPmPriceVo> pmPriceVosByChannelId = busyPriceFacadeService
                        .batchGetPmPriceVoListWithCache4Schedule(pmIdsByChannel, channelId, new BusyAreaVo());
                pmPriceVos.addAll(pmPriceVosByChannelId);
            }
        }
        for(Map.Entry<String, Map<Long, List<Long>>> areaEntry : area2ChannelId2PmIds.entrySet()) {
            Map<Long, List<Long>> channelId2PmIdsByArea = areaEntry.getValue();
            if(MapUtils.isEmpty(channelId2PmIdsByArea)) {
                continue;
            }
            for(Map.Entry<Long, List<Long>> entry : channelId2PmIdsByArea.entrySet()) {
                Long channelId = entry.getKey();
                List<Long> pmIdsByChannel = entry.getValue();
                if(CollectionUtils.isNotEmpty(pmIdsByChannel)) {
                    List<BusyPmPriceVo> pmPriceVosByChannelId = busyPriceFacadeService
                            .batchGetPmPriceVoListWithCache4Schedule(pmIdsByChannel, channelId,
                                    buildAreaVo(areaEntry.getKey()));
                    pmPriceVos.addAll(pmPriceVosByChannelId);
                }
            }
        }
        ScheduleContext.setValue(ScheduleConstants.PRODUCT_PROM_RULE_CHANEL_MAP, promRule2ChannelIdMap);
        ScheduleContext.setValue(ScheduleConstants.PRODUCT_PROM_RULE_AREA_MAP, promRule2AreaMap);
        return pmPriceVos;
    }

    private BusyAreaVo buildAreaVo(String areaKey) {
        if(StringUtils.isBlank(areaKey)) {
            return null;
        }
        BusyAreaVo areaVo = new BusyAreaVo();
        String[] subAreas = areaKey.split("-");
        areaVo.setProvinceId(Long.valueOf(subAreas[0]));
        areaVo.setCityId(Long.valueOf(subAreas[1]));
        areaVo.setCountyId(Long.valueOf(subAreas[2]));
        return areaVo;
    }

    /**
     * 去重消息变更消息
     * @param priceVo
     * @param msgVo
     * @return
     */
    private PriceFatMessageVo distinctPriceChangeMsg(BusyPmPriceVo pmPriceVo, BusyPriceChangeMsgVo priceChangeVo,
                                                     Long channelId, BusyAreaVo areaVo) {
        Long priceOrRuleId = priceChangeVo == null ? pmPriceVo.getId() : priceChangeVo.getMsgPriceId();
        PriceFatMessageVo fresh = buildPriceFatMessageVo(pmPriceVo, priceOrRuleId, channelId, areaVo);
        Long pmId = pmPriceVo.getPmId();
        String cacheKey = ScheduleCacheKeyUtil.getBusyPriceChangeMsgCacheKey(pmId, channelId, areaVo);
        Object cacheValue = cacheProxy.get(cacheKey);
        if(null != cacheValue) {
            DistintcPriceChangCache original = (DistintcPriceChangCache) cacheValue;
            // 价格一致
            if(null != original.getPrice() && original.getPrice().compareTo(fresh.getPrice()) == 0) {
                Long exsitPromRuleId = original.getPromRuleId();
                Long currentPromRuleId = fresh.getPromRuleId();
                // 促销id都为空，表示价格未产生变动
                if(null == exsitPromRuleId && null == currentPromRuleId) {
                    return null;
                }
                // 促销id一致，表示价格未产生变动
                if(null != exsitPromRuleId && null != currentPromRuleId && exsitPromRuleId.equals(currentPromRuleId)) {
                    return null;
                }
            }
        }
        cacheProxy.put(cacheKey, new DistintcPriceChangCache(fresh.getPrice(), fresh.getPromRuleId()),
                ScheduleCacheConstants.BUSY_DISTINTC_PRICE_CHANGE_EXPIRY_MINUTES);
        return fresh;
    }

    private static final PriceFatMessageVo buildPriceFatMessageVo(BusyPmPriceVo pmPriceVo, Long priceOrRuleId,
                                                                  Long channelId, BusyAreaVo areaVo) {
        PriceFatMessageVo result = new PriceFatMessageVo();
        result.setPmInfoId(pmPriceVo.getPmId());
        result.setPrice(pmPriceVo.getCurrentPrice());
        result.setPriceId(priceOrRuleId);
        result.setMerchantId(pmPriceVo.getMerchantId());
        result.setProductId(pmPriceVo.getProductId());
        result.setChannelId(channelId);
        if(areaVo != null) {
            result.setProvinceId(areaVo.getProvinceId());
            result.setCityId(areaVo.getCityId());
            result.setCountyId(areaVo.getCountyId());
        }
        if(null != pmPriceVo.getCurrentRuleVo()) {
            result.setPromRuleId(pmPriceVo.getCurrentRuleVo().getId());
            result.setPromoteType(pmPriceVo.getCurrentRuleVo().getPromoteType());
            result.setPromStartTime(pmPriceVo.getCurrentRuleVo().getPromStartTime());
            result.setPromEndTime(pmPriceVo.getCurrentRuleVo().getPromEndTime());
            result.setPromName(pmPriceVo.getCurrentRuleVo().getPromName());
            result.setBackendOperatorId(pmPriceVo.getCurrentRuleVo().getBackendOperatorId());
            result.setUpdateTime(pmPriceVo.getCurrentRuleVo().getUpdateTime());
            result.setRuleType(pmPriceVo.getCurrentRuleVo().getRuleType());
            result.setActivityId(pmPriceVo.getCurrentRuleVo().getActivityId());
        }
        return result;
    }

    /**
     * 构造sendEmailWithTemplet入参
     */
    private static void sendEmail(List<PriceFatMessageVo> list, String errorMessage) {
        // 如果入参list为空发送没有数据的异常邮件
        try {
            if(CollectionUtils.isEmpty(list)) {
                BusyMailUtil.sendHtmlMail("GPS价格变化消息发送器发送价格变化的胖消息发生异常", errorMessage);
                return;
            }
            MailTempletVo mailTempletVo = new MailTempletVo();
            List<List<PriceFatMessageVo>> lists = GPSUtils.split(list, ScheduleConstants.SEND_MAIL_THRESHOLD);
            for(List<PriceFatMessageVo> resultList : lists) {
                Map<String, Object> map = new HashMap<String, Object>(1, 1F);
                map.put("resultList", resultList);
                mailTempletVo.setTitle("历史价格数据丢失数据邮件");
                mailTempletVo.setTemplateName("HistoryPriceChangeResult_zh_CN.ftl");
                mailTempletVo.setMap(map);
                BusyMailSendWithTempletUtil.sendEmailWithTemplet(mailTempletVo);
            }
        } catch (Exception e) {
            LOGGER.error("###价格变化消息数据丢失发送邮件功能异常###" + e.getMessage(), e);
        }
    }

    @Override
    public JumperMessageLog createJumperMessageLog(PriceFatMessageVo message, String topic, String messageType,
                                                   String content, Date sendTime) {
        return new JumperMessageLog(message.getPmInfoId(), PoolUtils.getPoolIP(), topic, messageType, content, sendTime);
    }

    @Override
    public HistoryPriceChangeMsgVo buildHistoryPriceChangeMsg(PriceFatMessageVo message) {
        return HistoryPriceChangeMsgVoBuilder.buildHistoryPriceChangeMsg(message);
    }
}