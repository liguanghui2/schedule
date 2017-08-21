package com.yhd.gps.schedule.historyprice.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.yhd.gps.BaseSpringWithUnitilsTest;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.busy.vo.SimpleHistoryPriceChangeMsgVo;

/**
 * HistoryPriceChangeMsgDaoTest
 * @Author liuxiangrong
 * @CreateTime 2016-1-29 下午02:19:35
 */
public class HistoryPriceChangeMsgDaoTest extends BaseSpringWithUnitilsTest {

    @SpringBean("historyPriceChangeMsgDao")
    private HistoryPriceChangeMsgDao historyPriceChangeMsgDao;

    @Test
    @DataSet({"/data/dao/HISTORY_PRICE_CHANGE_MSG.xml" })
    @TestDataSource("public")
    public void batchInsertHistoryPriceChangeMsg() {
        List<HistoryPriceChangeMsgVo> priceChangeMsgList = new ArrayList<HistoryPriceChangeMsgVo>();

        for(int i = 0; i < 90; i++) {
            HistoryPriceChangeMsgVo priceChangeMsg = new HistoryPriceChangeMsgVo();
            priceChangeMsg.setChannelId(1L);
            priceChangeMsg.setCreateTime(ScheduleDateUtils.addDays(new Date(), -1 * i));
            priceChangeMsg.setStartTime(ScheduleDateUtils.parseDate("2016-01-01 12:00:01"));
            priceChangeMsg.setEndTime(ScheduleDateUtils.parseDate("2016-12-01 12:00:01"));
            priceChangeMsg.setMerchantId(8L);
            priceChangeMsg.setIsDeal(0);
            priceChangeMsg.setOperatorId(2011L);
            priceChangeMsg.setPmInfoId(8888L);
            priceChangeMsg.setPrice(new BigDecimal(i + 100));
            priceChangeMsg.setPriceId(122L);
            priceChangeMsg.setPriceChangeMsgType(ScheduleConstants.PRICE_CHANGE_MSG_TYPE_NORMAL);
            priceChangeMsg.setProductId(12234L);
            priceChangeMsg.setPromName("prom test");
            priceChangeMsg.setRuleType(1);
            priceChangeMsg.setPromotionId(0L);
            priceChangeMsg.setUpdateTime(ScheduleDateUtils.addDays(new Date(), -1 * i));
            priceChangeMsg.setShardingIndex((int) (8888L % 16));

            priceChangeMsgList.add(priceChangeMsg);
        }
        historyPriceChangeMsgDao.batchInsertHistoryPriceChangeMsg(priceChangeMsgList);
        List<Long> pmInfoIds = new ArrayList<Long>();
        pmInfoIds.add(8888L);

        List<SimpleHistoryPriceChangeMsgVo> list = historyPriceChangeMsgDao.queryRecentHistoryPriceChangeMsgs(pmInfoIds,
                ScheduleDateUtils.parseDate("2000-01-01 00:00:00"), ScheduleDateUtils.parseDate("2099-01-01 00:00:00"));
        assertNotNull(list);
        assertEquals(list.size(), 90);
    }

    @Test
    @DataSet({"/data/dao/HISTORY_PRICE_CHANGE_MSG.xml" })
    @TestDataSource("public")
    public void queryUnDealPmIdsBySharding() {
        List<Long> resultList = historyPriceChangeMsgDao.queryUnDealPmIdsBySharding(0,
                ScheduleDateUtils.parseDate("2015-01-01 12:01:01"), new Date());
        assertNotNull(resultList);
        String jsonArray = JSON.toJSONString(resultList);
        System.out.println("\n\n-----queryUnDealHistoryPriceChangeMsgBySharding=" + jsonArray.toString() + "\n\n");
    }

    @Test
    @DataSet({"/data/dao/HISTORY_PRICE_CHANGE_MSG.xml" })
    @TestDataSource("public")
    public void queryRecentHistoryPriceChangeMsgs() {
        List<Long> pmInfoList = new ArrayList<Long>();
        pmInfoList.add(68325L);
    	List<SimpleHistoryPriceChangeMsgVo> resultList = historyPriceChangeMsgDao.queryRecentHistoryPriceChangeMsgs(pmInfoList, ScheduleDateUtils.parseDate("2014-01-01 12:01:00"), new Date());
    	assertNotNull(resultList);
        String jsonArray = JSON.toJSONString(resultList);
        System.out.println("\n\n-----queryRecentHistoryPriceChangeMsg=" + jsonArray.toString() + "\n\n");
    }

    @Test
    @DataSet({"/data/dao/HISTORY_PRICE_CHANGE_MSG.xml" })
    @TestDataSource("public")
	public void batchUpdateHistoryPriceChangeMsg2Dealed(){
    	List<Long> pmList = new ArrayList<Long>();
    	pmList.add(51000L);
    	pmList.add(68325L);
    	
    	List<Long> maxIdList = new ArrayList<Long>();
    	maxIdList.add(2L);
    	maxIdList.add(1L);
    	
    	List<Long> pmInfoList = historyPriceChangeMsgDao.queryUnDealPmIdsBySharding(0,ScheduleDateUtils.parseDate("2015-01-01 12:01:01"),new Date());
    	assertNotNull(pmInfoList);
    	String jsonArray = JSON.toJSONString(pmInfoList);
    	System.out.println("\n\n-----batchUpdateHistoryPriceChangeMsg2Dealed=" + jsonArray + "\n\n");
    	assertEquals(pmInfoList.size(),2);
    	
    	List<SimpleHistoryPriceChangeMsgVo> resultList = historyPriceChangeMsgDao.queryRecentHistoryPriceChangeMsgs(pmInfoList, ScheduleDateUtils.parseDate("2014-01-01 12:01:00"), new Date());
        assertNotNull(resultList);

        List<Long> idList = new ArrayList<Long>();
    	for(SimpleHistoryPriceChangeMsgVo vo:resultList){
    	    if(vo.getIsDeal() == 0){
    	        idList.add(vo.getId());
    	    }
    	}
    	historyPriceChangeMsgDao.batchUpdateHistoryPriceChangeMsg2Dealed(idList);
    	
    	resultList = historyPriceChangeMsgDao.queryRecentHistoryPriceChangeMsgs(pmInfoList, ScheduleDateUtils.parseDate("2014-01-01 12:01:00"), new Date());
    	for(SimpleHistoryPriceChangeMsgVo vo:resultList){
            if(vo.getIsDeal() == 0){
                System.out.println("\n\n-----batchUpdateHistoryPriceChangeMsg2Dealed=" + JSON.toJSONString(vo) + "\n\n");
                assertTrue(false);
                break;
            }
        }
    }

}
