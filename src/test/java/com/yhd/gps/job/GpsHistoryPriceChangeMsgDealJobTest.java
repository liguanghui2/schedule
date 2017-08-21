package com.yhd.gps.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.dao.DateSectionPriceHistoryDao;
import com.yhd.gps.pricechange.vo.DateSectionPriceInfoVo;
import com.yhd.gps.pricechange.vo.input.QueryDateSectionPriceInfoByPmIdAndDateSectionRequest;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.schedule.job.HistoryPriceChangeStatisticJob;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.busy.vo.SimpleHistoryPriceChangeMsgVo;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-6 下午06:42:33
 */
public class GpsHistoryPriceChangeMsgDealJobTest extends BaseSpringTest {

    @Autowired
    private HistoryPriceChangeStatisticJob historyPriceChangeStatisticJob;

    @Autowired
    private HistoryPriceChangeMsgDao historyPriceChangeMsgDao;

    @Autowired
    private DateSectionPriceHistoryDao dateSectionPriceHistoryDao;

    private void clearData() {
        historyPriceChangeMsgDao.deleteHistoryPriceChangeMsgByPmIds(PM_INFO_IDS);

        QueryDateSectionPriceInfoByPmIdAndDateSectionRequest request = new QueryDateSectionPriceInfoByPmIdAndDateSectionRequest();
        request.setPmInfoIds(PM_INFO_IDS);
        dateSectionPriceHistoryDao.deleteDateSectionPriceInfo(request);
    }

    private void initHistoryPriceChangeMsgs() {
        clearData();

        for(int i = 0; i < 90; i++) {
            List<HistoryPriceChangeMsgVo> priceChangeMsgList = new ArrayList<HistoryPriceChangeMsgVo>();
            for(Long pmInfoId : PM_INFO_IDS) {
                HistoryPriceChangeMsgVo priceChangeMsg = new HistoryPriceChangeMsgVo();
                priceChangeMsg.setChannelId(1L);
                priceChangeMsg.setCreateTime(ScheduleDateUtils.addDays(new Date(), -1 * i));
                priceChangeMsg.setStartTime(ScheduleDateUtils.parseDate("2016-01-01 12:00:01"));
                priceChangeMsg.setEndTime(ScheduleDateUtils.parseDate("2016-12-01 12:00:01"));
                priceChangeMsg.setMerchantId(8L);
                priceChangeMsg.setIsDeal(0);
                priceChangeMsg.setOperatorId(2011L);
                priceChangeMsg.setPmInfoId(pmInfoId);
                priceChangeMsg.setPrice(new BigDecimal(i + 100));
                priceChangeMsg.setPriceId(122L);
                priceChangeMsg.setPriceChangeMsgType(ScheduleConstants.PRICE_CHANGE_MSG_TYPE_NORMAL);
                priceChangeMsg.setProductId(12234L);
                priceChangeMsg.setPromName("prom test");
                priceChangeMsg.setRuleType(1);
                priceChangeMsg.setPromotionId(0L);
                priceChangeMsg.setUpdateTime(ScheduleDateUtils.addDays(new Date(), -1 * i));
                priceChangeMsg.setShardingIndex((int) (pmInfoId % 16));

                priceChangeMsgList.add(priceChangeMsg);
            }
            historyPriceChangeMsgDao.batchInsertHistoryPriceChangeMsg(priceChangeMsgList);
        }

        List<SimpleHistoryPriceChangeMsgVo> list = historyPriceChangeMsgDao.queryRecentHistoryPriceChangeMsgs(PM_INFO_IDS,
                ScheduleDateUtils.parseDate("2000-01-01 00:00:00"), ScheduleDateUtils.parseDate("2099-01-01 00:00:00"));
        assertNotNull(list);
    }

    @Test
    public void test() {
        initHistoryPriceChangeMsgs();

        historyPriceChangeStatisticJob.execute();

        // 设置了每台机器最多处理4个切片, PM_INFO_ID_0 切片0，可以处理；PM_INFO_ID_1 切片1，可以处理；PM_INFO_ID_9 切片9，不会处理
        QueryDateSectionPriceInfoByPmIdAndDateSectionRequest request = new QueryDateSectionPriceInfoByPmIdAndDateSectionRequest();
        request.setPmInfoIds(PM_INFO_IDS);
        List<DateSectionPriceInfoVo> dateSectionPriceInfoVos = dateSectionPriceHistoryDao
                .queryDateSectionPriceInfoByPmIdAndDateSection(request);

        System.out.println(JSON.toJSON(dateSectionPriceInfoVos));
        assertNotNull(dateSectionPriceInfoVos);
        // assertEquals(dateSectionPriceInfoVos.size(), 4);

        // Map<String, DateSectionPriceInfoVo> pmId_DateSectionLevelMap = new HashMap<String,
        // DateSectionPriceInfoVo>(3,
        // 1F);
        // for(DateSectionPriceInfoVo vo : dateSectionPriceInfoVos) {
        // pmId_DateSectionLevelMap.put(vo.getPmInfoId() + "-" + vo.getDateSectionLevel(), vo);
        // }
        // DateSectionPriceInfoVo pmInfoId0_1_dateSectionPriceInfoVo = pmId_DateSectionLevelMap
        // .get(PM_INFO_ID_0 + "-" + BusyConstants.DATE_REGION_MONTH_LEVEL_1);
        //
        // assertTrue(pmInfoId0_1_dateSectionPriceInfoVo.getMaxPrice().floatValue() == 130.00
        // && pmInfoId0_1_dateSectionPriceInfoVo.getMinPrice().floatValue() == 100.00
        // && pmInfoId0_1_dateSectionPriceInfoVo.getAvgPrice().floatValue() == 115.00
        // && pmInfoId0_1_dateSectionPriceInfoVo.getCurrentPrice().floatValue() == 100.00);
        //
        // DateSectionPriceInfoVo pmInfoId0_3_dateSectionPriceInfoVo = pmId_DateSectionLevelMap
        // .get(PM_INFO_ID_0 + "-" + BusyConstants.DATE_REGION_MONTH_LEVEL_3);
        // assertTrue(pmInfoId0_3_dateSectionPriceInfoVo.getMaxPrice().floatValue() == 190.00
        // && pmInfoId0_3_dateSectionPriceInfoVo.getMinPrice().floatValue() == 100.00
        // && pmInfoId0_3_dateSectionPriceInfoVo.getAvgPrice().floatValue() == 145.00
        // && pmInfoId0_3_dateSectionPriceInfoVo.getCurrentPrice().floatValue() == 100.00);
        //
        // DateSectionPriceInfoVo pmInfoId1_1_dateSectionPriceInfoVo = pmId_DateSectionLevelMap
        // .get(PM_INFO_ID_1 + "-" + BusyConstants.DATE_REGION_MONTH_LEVEL_1);
        //
        // assertTrue(pmInfoId1_1_dateSectionPriceInfoVo.getMaxPrice().floatValue() == 130.00
        // && pmInfoId1_1_dateSectionPriceInfoVo.getMinPrice().floatValue() == 100.00
        // && pmInfoId1_1_dateSectionPriceInfoVo.getAvgPrice().floatValue() == 115.00
        // && pmInfoId1_1_dateSectionPriceInfoVo.getCurrentPrice().floatValue() == 100.00);
        //
        // DateSectionPriceInfoVo pmInfoId1_3_dateSectionPriceInfoVo = pmId_DateSectionLevelMap
        // .get(PM_INFO_ID_1 + "-" + BusyConstants.DATE_REGION_MONTH_LEVEL_3);
        // assertTrue(pmInfoId1_3_dateSectionPriceInfoVo.getMaxPrice().floatValue() == 190.00
        // && pmInfoId1_3_dateSectionPriceInfoVo.getMinPrice().floatValue() == 100.00
        // && pmInfoId1_3_dateSectionPriceInfoVo.getAvgPrice().floatValue() == 145.00
        // && pmInfoId1_3_dateSectionPriceInfoVo.getCurrentPrice().floatValue() == 100.00);

    }
}