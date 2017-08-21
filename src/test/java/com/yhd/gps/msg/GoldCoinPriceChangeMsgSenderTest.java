package com.yhd.gps.msg;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
import com.yhd.gps.busyservice.msg.GoldCoinPriceChangeMsgSender;
import com.yhd.gps.schedule.service.GoldCoinPriceChangeHistoryService;
import com.yhd.gps.schedule.vo.GoldCoinPriceChangeMsg;
import com.yihaodian.front.busystock.client.BusySpringBeanUtil;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.client.BusystockClientConfigurerPlugin;
import com.yihaodian.front.busystock.client.facade.BusyPriceFacadeService;
import com.yihaodian.front.busystock.vo.GoldCoinPromRuleVo;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BusystockClientConfigurerPlugin.class, ApplicationContext.class,BusySpringBeanUtil.class, BusyStockClientUtil.class})
public class GoldCoinPriceChangeMsgSenderTest{

	private  BusyPriceFacadeService busyPriceFacadeService;
	
	private GoldCoinPriceChangeMsgSender goldCoinPriceChangeMsgSender;
	
	private GoldCoinPriceChangeHistoryService goldCoinPriceChangeHistoryService;
	
    @SuppressWarnings("unchecked")
	@Before
    public void setUp(){
        /** BS模块开始 **/
        PowerMockito.mockStatic(BusystockClientConfigurerPlugin.class);
        ApplicationContext ac = PowerMockito.mock(ApplicationContext.class);
        PowerMockito.when(BusystockClientConfigurerPlugin.getApplicationContext()).thenReturn(ac);
        PowerMockito.mockStatic(BusySpringBeanUtil.class);
        PowerMockito.mockStatic(BusyStockClientUtil.class);
        busyPriceFacadeService = PowerMockito.mock(BusyPriceFacadeService.class);
        PowerMockito.when(BusySpringBeanUtil.getBean("busyPriceFacadeService")).thenReturn(busyPriceFacadeService);
        PowerMockito.when(BusyStockClientUtil.getBusyPriceFacadeService()).thenReturn(busyPriceFacadeService);
        /** BS模块结束 */
        goldCoinPriceChangeMsgSender=new GoldCoinPriceChangeMsgSender();
        goldCoinPriceChangeHistoryService=Mockito.mock(GoldCoinPriceChangeHistoryService.class);
        try {
        goldCoinPriceChangeHistoryService = PowerMockito.mock(GoldCoinPriceChangeHistoryService.class);
        Field goldCoinPriceChangeHistoryServiceField = GoldCoinPriceChangeMsgSender.class.getDeclaredField("goldCoinPriceChangeHistoryService");
        goldCoinPriceChangeHistoryServiceField.setAccessible(true);
		goldCoinPriceChangeHistoryServiceField.set(goldCoinPriceChangeMsgSender, goldCoinPriceChangeHistoryService);
		PowerMockito.when(goldCoinPriceChangeHistoryService.batchInsertGoldCoinHistoryPriceChangeMsg(Mockito.any(List.class))).thenReturn(1);
        }catch (Exception e) {
			e.printStackTrace();
		}
    }

	@SuppressWarnings("unchecked")
	@Test
	public void saveToGoldCoinPriceChangeHistoryAndSendMsgTest(){
		List<GoldCoinPriceChangeMsg> goldCoinPriceChangeMsgs =new ArrayList<GoldCoinPriceChangeMsg>();
		GoldCoinPriceChangeMsg vo=new GoldCoinPriceChangeMsg();
		vo.setId(1234L);
		vo.setPmInfoId(68325L);
		vo.setRuleId(1L);
		goldCoinPriceChangeMsgs.add(vo);
		List<GoldCoinPromRuleVo> resultList=new ArrayList<GoldCoinPromRuleVo>();
		GoldCoinPromRuleVo goldCoinPromRuleVo=new GoldCoinPromRuleVo();
		goldCoinPromRuleVo.setId(1234L);
		goldCoinPromRuleVo.setProductId(1234L);
		goldCoinPromRuleVo.setMerchantId(1L);
		goldCoinPromRuleVo.setPmInfoId(2345L);
		goldCoinPromRuleVo.setChannelId(1L);
		goldCoinPromRuleVo.setPromTotalPrice(new BigDecimal(100));
		goldCoinPromRuleVo.setPromCashPrice(new BigDecimal(100));
		goldCoinPromRuleVo.setGoldCoinPrice(new BigDecimal(99.99));
		goldCoinPromRuleVo.setGoldCoinNum(12);
		goldCoinPromRuleVo.setIsPromotion(1);
		goldCoinPromRuleVo.setPromStartDate(new Date());
		goldCoinPromRuleVo.setPromEndDate(new Date());
		goldCoinPromRuleVo.setUpdateTime(new Date());
		goldCoinPromRuleVo.setBackOperatorId(12L);
		resultList.add(goldCoinPromRuleVo);
		Set<Long> sendedMsgIds=new HashSet<Long>();
		Mockito.when(busyPriceFacadeService.getGoldCoinPromRuleVoListByIds(Mockito.anyList())).thenReturn(resultList);
		Mockito.when(goldCoinPriceChangeHistoryService.batchInsertGoldCoinHistoryPriceChangeMsg(Mockito.anyList())).thenReturn(1);
		goldCoinPriceChangeMsgSender.saveToGoldCoinPriceChangeHistoryAndSendMsg(goldCoinPriceChangeMsgs, sendedMsgIds);
		System.out.println("********************");
	}
}
