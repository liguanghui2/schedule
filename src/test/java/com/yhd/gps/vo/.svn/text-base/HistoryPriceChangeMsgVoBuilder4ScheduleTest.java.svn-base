package com.yhd.gps.vo;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.vo.builder.HistoryPriceChangeMsgVoBuilder4Schedule;
import com.yihaodian.busy.vo.BusyPmPriceVo;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

/**
 * @author:liguanghui1
 * @date ：2016-8-31 上午10:10:57
 */

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class HistoryPriceChangeMsgVoBuilder4ScheduleTest {
    
    HistoryPriceChangeMsgVoBuilder4Schedule historyPriceChangeMsgVoBuilder4Schedule=new HistoryPriceChangeMsgVoBuilder4Schedule();
    @Test
    public void buildHistoryPriceChangeMsgTest01(){
        BusyPmPriceVo vo=new BusyPmPriceVo();
        vo.setProductId(123L);
        vo.setPmId(12L);
        vo.setMerchantId(100L);
        vo.setChannelId(12L);
        BigDecimal currentPrice=new BigDecimal(100.99);
        vo.setCurrentPrice(currentPrice);
        vo.setId(110L);
        Date date=new Date();
        Long time=date.getTime();
        Date promStartTime=new Date(time-10000);
        Date promEndTime=new Date(time+10000);
        vo.setPromStartTime(promStartTime);
        vo.setPromEndTime(promEndTime);
        vo.setRuleType(2);
        vo.setPromoteType(12);
        HistoryPriceChangeMsgVo historyPriceChangeMsgVo =HistoryPriceChangeMsgVoBuilder4Schedule.buildHistoryPriceChangeMsg(vo);
        assertTrue(historyPriceChangeMsgVo.getProductId().compareTo(123L)==0);
        assertTrue(historyPriceChangeMsgVo.getPriceId().compareTo(110L)==0);
    }
    @Test
    public void buildHistoryPriceChangeMsgTest02(){
        BusyPmPriceVo vo=new BusyPmPriceVo();
        vo.setProductId(123L);
        vo.setPmId(12L);
        vo.setMerchantId(100L);
        vo.setChannelId(12L);
        BigDecimal currentPrice=new BigDecimal(100.99);
        vo.setCurrentPrice(currentPrice);
        vo.setId(110L);
        Date date=new Date();
        Long time=date.getTime();
        Date promStartTime=new Date(time-10000);
        Date promEndTime=new Date(time+10000);
        vo.setPromStartTime(promStartTime);
        vo.setPromEndTime(promEndTime);
        vo.setRuleType(2);
        vo.setPromoteType(12);
        BSProductPromRuleVo bsProductPromRuleVo=new BSProductPromRuleVo();
        bsProductPromRuleVo.setId(123L);
        vo.setCurrentRuleVo(bsProductPromRuleVo);
        HistoryPriceChangeMsgVo historyPriceChangeMsgVo =HistoryPriceChangeMsgVoBuilder4Schedule.buildHistoryPriceChangeMsg(vo);
        assertTrue(historyPriceChangeMsgVo.getProductId().compareTo(123L)==0);
        assertTrue(historyPriceChangeMsgVo.getPriceId().compareTo(110L)==0);
    }

}
