package com.yhd.gps.busy.mail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.busyservice.dao.impl.BusyPmInfoDaoImpl;
import com.yhd.gps.busyservice.dao.impl.HistoryPriceChangeMsgDaoImpl;
import com.yhd.gps.schedule.parall.param.CompensateHistoryPriceDataParam;
import com.yhd.gps.schedule.parall.result.WrapResult;
import com.yhd.gps.schedule.parall.task.CompensateHistoryPriceDataTask;

/**
 * @author:liguanghui1
 * @date ：2016-9-5 下午01:41:35
 */
public class CompensateHistoryPriceDataTaskTest extends BaseSpringTest {

    @Test
    public void runTaskTest() {
        BusyPmInfoDao busyPmInfoDao = new BusyPmInfoDaoImpl();
        HistoryPriceChangeMsgDao historyPriceChangeMsgDao = new HistoryPriceChangeMsgDaoImpl();
        CountDownLatch finishSignal = new CountDownLatch(12);
        @SuppressWarnings({"rawtypes", "unchecked" })
        WrapResult<Void> wrap = new WrapResult(finishSignal);
        CompensateHistoryPriceDataParam compensateHistoryPriceDataParam = new CompensateHistoryPriceDataParam(12L,
            busyPmInfoDao, historyPriceChangeMsgDao);
        CompensateHistoryPriceDataTask compensateHistoryPriceDataTask = new CompensateHistoryPriceDataTask(1,
            compensateHistoryPriceDataParam, wrap);
        try {
            invoke(compensateHistoryPriceDataTask, "runTask");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void recordHistoryPriceChangeTest() {
        BusyPmInfoDao busyPmInfoDao = new BusyPmInfoDaoImpl();
        HistoryPriceChangeMsgDao historyPriceChangeMsgDao = new HistoryPriceChangeMsgDaoImpl();
        CountDownLatch finishSignal = new CountDownLatch(12);
        @SuppressWarnings({"rawtypes", "unchecked" })
        WrapResult<Void> wrap = new WrapResult(finishSignal);
        CompensateHistoryPriceDataParam compensateHistoryPriceDataParam = new CompensateHistoryPriceDataParam(12L,
            busyPmInfoDao, historyPriceChangeMsgDao);
        CompensateHistoryPriceDataTask compensateHistoryPriceDataTask = new CompensateHistoryPriceDataTask(1,
            compensateHistoryPriceDataParam, wrap);
        List<Long> pmIds = new ArrayList<Long>();
        pmIds.add(67479L);
        pmIds.add(67480L);
        pmIds.add(67481L);
        try {
            invoke(compensateHistoryPriceDataTask, "recordHistoryPriceChange", pmIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object invoke(Object object, String methodName, Object... args) throws Exception {
        Method method = ReflectionUtils.findMethod(object.getClass(), methodName, null);
        method.setAccessible(true);
        return method.invoke(object, args);
    }
}
