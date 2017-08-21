package com.yhd.gps.schedule.parall.param;

import org.junit.Test;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.busyservice.dao.impl.BusyPmInfoDaoImpl;
import com.yhd.gps.busyservice.dao.impl.HistoryPriceChangeMsgDaoImpl;

/**
 * @author:liguanghui1
 * @date ：2016-9-5 下午03:08:08
 */

public class CompensateHistoryPriceDataParamTest extends BaseSpringTest {

    @SuppressWarnings("static-access")
    @Test
    public void TestVo() {
        BusyPmInfoDao busyPmInfoDao = new BusyPmInfoDaoImpl();
        HistoryPriceChangeMsgDao historyPriceChangeMsgDao = new HistoryPriceChangeMsgDaoImpl();
        CompensateHistoryPriceDataParam compensateHistoryPriceDataParam = new CompensateHistoryPriceDataParam(12L,
            busyPmInfoDao, historyPriceChangeMsgDao);
        compensateHistoryPriceDataParam.setHistoryPriceChangeMsgDao(historyPriceChangeMsgDao);
        compensateHistoryPriceDataParam.setBusyPmInfoDao(busyPmInfoDao);
        compensateHistoryPriceDataParam.setMerchantId(1002L);
        compensateHistoryPriceDataParam.getHistoryPriceChangeMsgDao();
        compensateHistoryPriceDataParam.getBusyPmInfoDao();
        assertNotNull(compensateHistoryPriceDataParam.getBusyPriceFacadeService());
        assertNotNull(compensateHistoryPriceDataParam.getQuerypminfoservice());
        Long merchantId = compensateHistoryPriceDataParam.getMerchantId();
        assertTrue(merchantId.compareTo(1002L) == 0);
    }
}
