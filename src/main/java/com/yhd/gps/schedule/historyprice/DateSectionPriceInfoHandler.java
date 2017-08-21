package com.yhd.gps.schedule.historyprice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.list.SetUniqueList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.DateSectionPriceHistoryDao;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.busyservice.msg.MsgSender;
import com.yhd.gps.pricechange.vo.DateSectionPriceInfoVo;
import com.yhd.gps.pricechange.vo.GPSLowestPriceMessageVo;
import com.yhd.gps.pricechange.vo.input.QueryDateSectionPriceInfoByPmIdAndDateSectionRequest;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.historyprice.common.HistoryPriceChangeMsgComparator;
import com.yhd.gps.schedule.historyprice.common.HistoryPriceChangeMsgFilter;
import com.yhd.gps.schedule.historyprice.common.KeyUtils;
import com.yhd.gps.schedule.vo.DateSectionPricePersistTuple;
import com.yhd.gps.schedule.vo.JumperMessageLog;
import com.yhd.gps.schedule.vo.StatisticsPriceInfo;
import com.yihaodian.busy.common.BusyConstants;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.busy.vo.SimpleHistoryPriceChangeMsgVo;
import com.yihaodian.front.databus.DatabusConstants;
import com.yihaodian.front.databus.client.FrontDatabusClient;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-3-29 下午02:38:07
 */
public class DateSectionPriceInfoHandler extends MsgSender<GPSLowestPriceMessageVo> {

    private static final Log LOGGER = LogFactory.getLog(DateSectionPriceInfoHandler.class);

    /**
     * 历史价格查询DAO
     */
    private HistoryPriceChangeMsgDao historyPriceChangeMsgDao;

    /**
     * 历史区间价格查询服务类
     */
    private DateSectionPriceHistoryDao dateSectionPriceHistoryDao;

    private final static int MAX_SIZE = 500;

    public void setHistoryPriceChangeMsgDao(HistoryPriceChangeMsgDao historyPriceChangeMsgDao) {
        this.historyPriceChangeMsgDao = historyPriceChangeMsgDao;
    }

    public void setDateSectionPriceHistoryDao(DateSectionPriceHistoryDao dateSectionPriceHistoryDao) {
        this.dateSectionPriceHistoryDao = dateSectionPriceHistoryDao;
    }

    public int computeDateSectionPriceInfo(List<Long> pmIds, int shardingIndex, Set<Long> dealedMsgIds) {
        if(CollectionUtils.isEmpty(pmIds)) {
            return 0;
        }
        // 结束时间, 开始时间, 倒退1个月的时间
        Date endDate = new Date();
        Date before1MonthDate = ScheduleDateUtils.getBefore1MonthDate(endDate);
        Date before7DaysDate = ScheduleDateUtils.parseDate(ScheduleDateUtils.addDays(endDate, -7));
        // 自定义时间
        Date customStartDate = MiscConfigUtils.getCustomStartDate4RegionMonthLevel();
        Date startDate = before1MonthDate;
        // 如果自定义时间比倒退一个月时间还早的话,以自定义时间为准取数据(字典表key:CUSTOM_START_DATE_FOR_REGION_MONTH_LEVEL)
        if(customStartDate != null && customStartDate.compareTo(before1MonthDate) < 0) {
            startDate = customStartDate;
        }

        // 需持久化的时间跨度历史价格信息
        DateSectionPricePersistTuple<DateSectionPriceInfoVo> persistTuple = new DateSectionPricePersistTuple<DateSectionPriceInfoVo>(
            new ArrayList<DateSectionPriceInfoVo>(), new ArrayList<DateSectionPriceInfoVo>());
        // 分批处理
        List<List<Long>> splitPmIds = GPSUtils.split(pmIds, MAX_SIZE);
        for(List<Long> _pmIds : splitPmIds) {
            List<SimpleHistoryPriceChangeMsgVo> historyPriceChangeMsgs = historyPriceChangeMsgDao
                    .queryRecentHistoryPriceChangeMsgs(_pmIds, startDate, endDate);
            if(CollectionUtils.isEmpty(historyPriceChangeMsgs)) {
                return 0;
            }
            // 历史价格表记录按创建时间升序排序
            Collections.sort(historyPriceChangeMsgs, new HistoryPriceChangeMsgComparator());
            Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> pmId2ChannelId2HistoryPriceMsgsMap = groupByPmIdAndChannelId(
                    historyPriceChangeMsgs, dealedMsgIds);

            // 统计表记录按pmInfoId + "-" + channelId + "-" + dateSectionLevel分组
            Map<String, DateSectionPriceInfoVo> pmIdChannelIdSLevel2SectionPriceInfoVoMap = fetchDateSectionPriceInfos(_pmIds);

            // 统计1个月历史价格
            Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> oneMonthData = HistoryPriceChangeMsgFilter
                    .filterMsgsByDateSection(pmId2ChannelId2HistoryPriceMsgsMap, before1MonthDate, false);
            computeDateSectionPriceInfo(BusyConstants.DATE_REGION_MONTH_LEVEL_1, oneMonthData,
                    pmIdChannelIdSLevel2SectionPriceInfoVoMap, persistTuple);

            // 统计7天历史价格
            Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> sevenDaysData = HistoryPriceChangeMsgFilter
                    .filterMsgsByDateSection(pmId2ChannelId2HistoryPriceMsgsMap, before7DaysDate, false);
            computeDateSectionPriceInfo(BusyConstants.DATE_REGION_DAY_LEVEL_7, sevenDaysData,
                    pmIdChannelIdSLevel2SectionPriceInfoVoMap, persistTuple);

            /**
             * 统计自定义时间的历史价格 1.字典表key:CUSTOM_START_DATE_FOR_REGION_MONTH_LEVEL
             * 2.如果需要统计自定义的历史价格需设置对应字典表值enabled:1表示可用状态,item_value:需从何时开始统计的时间。
             */
            if(customStartDate != null) {
                // 过滤后只剩自定义日期之后的数据在合并自定义时间段的最低价数据
                Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> customDaysData = HistoryPriceChangeMsgFilter
                        .filterMsgsByDateSection(pmId2ChannelId2HistoryPriceMsgsMap, customStartDate, true);
                // 重新构建customDaysData
                reBuildCustomDaysData(_pmIds, customDaysData);
                computeDateSectionPriceInfo(BusyConstants.DATE_REGION_MONTH_LEVEL_103, customDaysData,
                        pmIdChannelIdSLevel2SectionPriceInfoVoMap, persistTuple);
            }
        }
        persistDateSectionPriceInfos(persistTuple);
        return pmIds.size();
    }

    private void reBuildCustomDaysData(List<Long> pmIds,
                                       Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> pmId2ChannelId2HistoryPriceMap) {
        // 存在自定义日期数据
        String[] customDate4MinPrice = MiscConfigUtils.getCustomDate4MinPrice();
        if(customDate4MinPrice == null) {
            return;
        }
        // 双12需求,排除11月份商品价格,并增加8.1-10.30号的商品价格数据的比较
        List<SimpleHistoryPriceChangeMsgVo> minHistoryPriceVos = historyPriceChangeMsgDao
                .queryMinHistoryPriceChangeMsgs(pmIds, customDate4MinPrice[0], customDate4MinPrice[1]);
        // 分组过滤
        Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> minPmId2ChannelId2HistoryPriceVos = groupByPmIdAndChannelId(
                minHistoryPriceVos, new HashSet<Long>());
        // 合并数据
        for(Entry<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> minEntry : minPmId2ChannelId2HistoryPriceVos
                .entrySet()) {
            Long pmId = minEntry.getKey();
            Map<Long, List<SimpleHistoryPriceChangeMsgVo>> minChannelId2SimpleHistoryPriceVos = minEntry.getValue();
            Map<Long, List<SimpleHistoryPriceChangeMsgVo>> _channelId2SimpleHistoryPriceVos = pmId2ChannelId2HistoryPriceMap
                    .get(pmId);
            if(null == _channelId2SimpleHistoryPriceVos) {
                continue;
            }
            for(Entry<Long, List<SimpleHistoryPriceChangeMsgVo>> minChannelId2SimpleHistoryPriceVosEntry : minChannelId2SimpleHistoryPriceVos
                    .entrySet()) {
                Long channelId = minChannelId2SimpleHistoryPriceVosEntry.getKey();
                List<SimpleHistoryPriceChangeMsgVo> _historyPriceChangeMsgVos = _channelId2SimpleHistoryPriceVos
                        .get(channelId);
                if(null == _historyPriceChangeMsgVos) {
                    continue;
                }
                List<SimpleHistoryPriceChangeMsgVo> minHistoryPriceChangeMsgVos = minChannelId2SimpleHistoryPriceVosEntry
                        .getValue();
                if(CollectionUtils.isNotEmpty(minHistoryPriceChangeMsgVos)) {
                    for(SimpleHistoryPriceChangeMsgVo simpleHistoryPriceChangeMsgVos : minHistoryPriceChangeMsgVos) {
                        // 将自定义时间段内最低价数据插入到list头部
                        _historyPriceChangeMsgVos.add(0, simpleHistoryPriceChangeMsgVos);
                    }
                }
            }
        }
    }

    private void computeDateSectionPriceInfo(Integer dateSectionLevel,
                                             Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> pmId2ChannelId2MsgsMap,
                                             Map<String, DateSectionPriceInfoVo> dateSectionPriceInfoMap,
                                             DateSectionPricePersistTuple<DateSectionPriceInfoVo> persistTuple) {
        for(Map.Entry<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> entry : pmId2ChannelId2MsgsMap.entrySet()) {
            Map<Long, List<SimpleHistoryPriceChangeMsgVo>> channelId2MsgsMap = entry.getValue();
            if(MapUtils.isEmpty(channelId2MsgsMap)) {
                continue;
            }

            for(Map.Entry<Long, List<SimpleHistoryPriceChangeMsgVo>> innerEntry : channelId2MsgsMap.entrySet()) {
                Long channelId = innerEntry.getKey();
                // 最近时间该商品历史价格变动信息
                List<SimpleHistoryPriceChangeMsgVo> msgs = innerEntry.getValue();
                if(CollectionUtils.isEmpty(msgs)) {
                    continue;
                }
                // 按创建时间升序排序,取最近的价格变动消息
                SimpleHistoryPriceChangeMsgVo recentlyMsg = msgs.get(msgs.size() - 1);

                Long pmId = recentlyMsg.getPmInfoId();

                // 计算出该商品最近时间段内价格最低、最高、平均值
                StatisticsPriceInfo statisticsPrice = new StatisticsPriceInfo();
                statisticsPrice.statistics(msgs);
                final BigDecimal maxPrice = statisticsPrice.maxPrice;
                final BigDecimal minPrice = statisticsPrice.minPrice;
                final BigDecimal avgPrice = statisticsPrice.avgPrice;

                final BigDecimal currentPrice = recentlyMsg.getPrice();
                final Long currentPromRuleId = recentlyMsg.getPromotionId();
                DateSectionPriceInfoVo fresh = new DateSectionPriceInfoVo(recentlyMsg.getProductId(), pmId, channelId,
                    maxPrice, minPrice, avgPrice, currentPrice, null, null, dateSectionLevel, currentPromRuleId);

                // 如果当前价格大于等于最高价
                if(currentPrice.compareTo(maxPrice) >= 0) {
                    fresh.setMaxPricePromRuleId(currentPromRuleId);
                    // 如果当前价格小于等于最低价
                } else if(currentPrice.compareTo(minPrice) <= 0) {
                    fresh.setMinPricePromRuleId(currentPromRuleId);
                }

                String key = KeyUtils.buildDateSectionPriceInfoKey(pmId, channelId, dateSectionLevel);
                DateSectionPriceInfoVo original = dateSectionPriceInfoMap.get(key);
                // 原纪录不存在，需要新增
                if(null == original) {
                    persistTuple.addInsertInfo(fresh);
                } else {
                    fresh.setId(original.getId());
                    fresh.setLastPrice(original.getCurrentPrice());
                    persistTuple.addUpdateInfo(fresh);
                    // 只有自定义情况才发最低价和失去最低价的消息
                    if(BusyConstants.DATE_REGION_MONTH_LEVEL_103.equals(dateSectionLevel)) {
                        sendLowestPriceOrLostLowestPriceMessage(fresh, original, recentlyMsg);
                    }
                }
            }
        }
    }

    private Map<String, DateSectionPriceInfoVo> fetchDateSectionPriceInfos(List<Long> pmIds) {
        // 价格统计信息
        List<DateSectionPriceInfoVo> allDateSectionPriceInfoVos = fetchDateSectionPriceInfosByPmIds(pmIds);
        if(CollectionUtils.isEmpty(allDateSectionPriceInfoVos)) {
            return Collections.emptyMap();
        }

        // <pmInfoId + "-" + channelId + "-" + dateSectionLevel : DateSectionPriceInfoVo>
        Map<String, DateSectionPriceInfoVo> result = new HashMap<String, DateSectionPriceInfoVo>(pmIds.size(), 1F);

        for(DateSectionPriceInfoVo dateSectionPriceInfoVo : allDateSectionPriceInfoVos) {
            Long pmId = dateSectionPriceInfoVo.getPmInfoId();
            Long channelId = dateSectionPriceInfoVo.getChannelId();
            Integer dateSectionLevel = dateSectionPriceInfoVo.getDateSectionLevel();
            String key = KeyUtils.buildDateSectionPriceInfoKey(pmId, channelId, dateSectionLevel);

            result.put(key, dateSectionPriceInfoVo);
        }

        return result;
    }

    private List<DateSectionPriceInfoVo> fetchDateSectionPriceInfosByPmIds(List<Long> pmIds) {
        QueryDateSectionPriceInfoByPmIdAndDateSectionRequest request = new QueryDateSectionPriceInfoByPmIdAndDateSectionRequest();
        // request.setChannelId(channelId);
        // request.setDateSectionLevel(dateSectionLevel);
        request.setPmInfoIds(pmIds);

        return dateSectionPriceHistoryDao.queryDateSectionPriceInfoByPmIdAndDateSection(request);
    }

    /**
     * 持久化时间跨度历史价格信息
     * 
     * @param persistTuple
     */
    @SuppressWarnings("unchecked")
    private void persistDateSectionPriceInfos(DateSectionPricePersistTuple<DateSectionPriceInfoVo> persistTuple) {
        try {
            List<DateSectionPriceInfoVo> insertList = persistTuple.getInsertList();
            if(CollectionUtils.isNotEmpty(insertList)) {
                List<DateSectionPriceInfoVo> uniqueList = SetUniqueList.decorate(insertList);
                dateSectionPriceHistoryDao.insertDateSectionPriceInfo(uniqueList);
            }
            List<DateSectionPriceInfoVo> udpateList = persistTuple.getUpdateList();
            if(CollectionUtils.isNotEmpty(udpateList)) {
                dateSectionPriceHistoryDao.updateDateSectionPriceInfo(udpateList);
            }
        } catch (Exception e) {
            LOGGER.error("persist date section price error:" + e.getMessage(), e);
        }
    }

    /**
     * 根据pmId和channelId将HistoryPriceChangeMsgVo分组
     * @param historyPriceChangeMsgs
     * @param dealedMsgIds
     * @return <pmId : <channelId : List<HistoryPriceChangeMsgVo>>>
     */
    private static final Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> groupByPmIdAndChannelId(List<SimpleHistoryPriceChangeMsgVo> historyPriceChangeMsgs,
                                                                                                           Set<Long> dealedMsgIds) {
        Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> result = new HashMap<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>>(
            historyPriceChangeMsgs.size(), 1F);
        for(SimpleHistoryPriceChangeMsgVo historyPriceChangeMsg : historyPriceChangeMsgs) {
            if(null == historyPriceChangeMsg) {
                continue;
            }
            Long pmId = historyPriceChangeMsg.getPmInfoId();
            if(null == pmId) {
                continue;
            }

            if(ScheduleConstants.HISTORY_PRICE_CHANGE_MSG_UN_DEALED == historyPriceChangeMsg.getIsDeal()) {
                dealedMsgIds.add(historyPriceChangeMsg.getId());
            }

            Map<Long, List<SimpleHistoryPriceChangeMsgVo>> channelId2MsgsMap = result.get(pmId);
            if(null == channelId2MsgsMap) {
                channelId2MsgsMap = new HashMap<Long, List<SimpleHistoryPriceChangeMsgVo>>();
                result.put(pmId, channelId2MsgsMap);
            }
            Long channelId = historyPriceChangeMsg.getChannelId();
            // 如果channelId为null，表示生效价格是基准价，则所有的渠道价格都是一致的，都为基准价
            if(null == channelId) {
                for(Long channelIdTemp : ScheduleConstants.ALL_CHANNEL_ID_LIST) {
                    fillMsgListByChannelId(channelId2MsgsMap, channelIdTemp, historyPriceChangeMsg);
                }
            } else {
                fillMsgListByChannelId(channelId2MsgsMap, channelId, historyPriceChangeMsg);
            }
        }
        return result;
    }

    /**
     * 根据channelId填充HistoryPriceChangeMsgVo的list
     * @param channelId2MsgsMap
     * @param channelId
     * @param historyPriceChangeMsg
     */
    private static final void fillMsgListByChannelId(Map<Long, List<SimpleHistoryPriceChangeMsgVo>> channelId2MsgsMap,
                                                     Long channelId, SimpleHistoryPriceChangeMsgVo historyPriceChangeMsg) {
        List<SimpleHistoryPriceChangeMsgVo> list = channelId2MsgsMap.get(channelId);
        if(null == list) {
            list = new ArrayList<SimpleHistoryPriceChangeMsgVo>();
            channelId2MsgsMap.put(channelId, list);
        }
        list.add(historyPriceChangeMsg);
    }

    /**
     * 发送 "最低价" 或 "失去最低价" 消息
     * 
     * @param fresh
     * @param original
     * @param priceMessage
     */
    private void sendLowestPriceOrLostLowestPriceMessage(DateSectionPriceInfoVo fresh, DateSectionPriceInfoVo original,
                                                         SimpleHistoryPriceChangeMsgVo priceMessage) {
        BigDecimal freshCurrentPrice = fresh.getCurrentPrice();
        BigDecimal originalCurrentPrice = original.getCurrentPrice();
        // 如果当前价比最近当前价一样，不做任何操作
        if(freshCurrentPrice.compareTo(originalCurrentPrice) == 0) {
            return;
        }

        BigDecimal freshMinPrice = fresh.getMinPrice();
        BigDecimal originalMinPrice = original.getMinPrice();

        // 如果最近时间段内价格最低比统计表最低价低,且最后一次修改的当前价等于最低价则发最低价消息
        boolean isLowestPrice = freshMinPrice.compareTo(originalMinPrice) <= 0
                                && freshMinPrice.compareTo(freshCurrentPrice) == 0;
        // 如果上一次改价是最低价，且当前价比最近当前价高，则认为是从最低价恢复到较高价
        boolean isLostLowestPrice = !isLowestPrice && originalCurrentPrice.compareTo(originalMinPrice) == 0
                                    && freshCurrentPrice.compareTo(originalCurrentPrice) > 0;
        // 既不是最低价也不是失去最低价则则直接返回
        if(!isLowestPrice && !isLostLowestPrice) {
            return;
        }
        Long channelId = priceMessage.getChannelId();
        GPSLowestPriceMessageVo message = new GPSLowestPriceMessageVo(priceMessage.getCreateTime(),
            priceMessage.getProductId(), priceMessage.getMerchantId(), priceMessage.getPmInfoId(), freshCurrentPrice,
            fresh.getMaxPrice(), freshMinPrice, fresh.getAvgPrice(), priceMessage.getPromotionId(), channelId);

        if(isLowestPrice) {
            // 发送最低价消息
            message.setPriceChangeType(BusyConstants.PRICE_CHANGE_TYPE_LOWEST);
        } else if(isLostLowestPrice) {
            // 发送失去最低价消息
            message.setPriceChangeType(BusyConstants.PRICE_CHANGE_TYPE_LOST_LOWEST);
        }

        // channelId 渠道id, dateRegionLevel 1月最低价，7天最低价， 自定义时间之内的最低价
        final List<GPSLowestPriceMessageVo> messages = new ArrayList<GPSLowestPriceMessageVo>(1);
        messages.add(message);
        Integer dateSectionLevel = fresh.getDateSectionLevel();
        final String messageType = KeyUtils.buildMessageType(channelId, dateSectionLevel);
        FrontDatabusClient.publishLowestPriceJson(messages, messageType);

        saveJumperMessageLog(DatabusConstants.GPS_LOWEST_PRICE, messages, messageType);
    }

    @Override
    public JumperMessageLog createJumperMessageLog(GPSLowestPriceMessageVo message, String topic, String messageType,
                                                   String content, Date sendTime) {

        return new JumperMessageLog(message.getPmInfoId(), PoolUtils.getPoolIP(), topic, messageType, content, sendTime);
    }

    @Override
    public HistoryPriceChangeMsgVo buildHistoryPriceChangeMsg(GPSLowestPriceMessageVo message) {
        return null;
    }
}