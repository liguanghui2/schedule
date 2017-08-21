package com.yhd.gps.schedule.parall.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.parall.BaseTaskManager;
import com.yhd.gps.schedule.parall.param.CompensateHistoryPriceDataParam;
import com.yhd.gps.schedule.parall.result.WrapResult;
import com.yhd.gps.schedule.parall.task.CompensateHistoryPriceDataTask;

/**
 * ---- 补偿历史价格并行任务管理器 (数据初始化,拆分子任务,任务的分发,结果汇总收集)------
 * @Author lipengfei
 * @CreateTime 2016-8-25 下午11:09:31
 */
public class CompensateHistoryPriceDataParallTaskManager extends BaseTaskManager {

    private BusyPmInfoDao busyPmInfoDao;

    private HistoryPriceChangeMsgDao historyPriceChangeMsgDao;

    public void setBusyPmInfoDao(BusyPmInfoDao busyPmInfoDao) {
        this.busyPmInfoDao = busyPmInfoDao;
    }

    public void setHistoryPriceChangeMsgDao(HistoryPriceChangeMsgDao historyPriceChangeMsgDao) {
        this.historyPriceChangeMsgDao = historyPriceChangeMsgDao;
    }

    /**
     * 获取需要补偿数据的商家Ids
     * @return
     */
    @SuppressWarnings("serial")
    private List<Long> initMerchantData() {
        // 按不同的自营商家取数据,默认取北上广成武
        List<Long> merchantIds = new ArrayList<Long>() {{add(1L);add(2L);add(3L);add(8L);add(9L);}};
        // 取自定义的补偿数据的商家,目前字典表没有配置(key:COMPENSATE_LOSE_DATA_BY_MERCHANT4SCHEDULE)
        String[] customCompensateMerchantIds = MiscConfigUtils.getCustomCompensateMerchantIds();
        if(customCompensateMerchantIds != null && customCompensateMerchantIds.length > 0) {
            merchantIds.clear();
            for(int i = 0; i < customCompensateMerchantIds.length; i++) {
                merchantIds.add(Long.valueOf(customCompensateMerchantIds[i]));
            }
        }
        return merchantIds;
    }

    @Override
    @SuppressWarnings("unchecked")
    public WrapResult<Void> executeParallTask() {
        List<Long> merchantIds = initMerchantData();
        final int parallTaskNum = merchantIds.size();
        WrapResult<Void> wrapResult = new WrapResult<Void>(new CountDownLatch(parallTaskNum));
        // 任务分发i可以设置为子任务编号
        for(int i = 0; i < parallTaskNum; i++) {
            CompensateHistoryPriceDataParam compensateHisPriceDataParam = new CompensateHistoryPriceDataParam(merchantIds.get(i), busyPmInfoDao, historyPriceChangeMsgDao);
            executor.execute(new CompensateHistoryPriceDataTask(i, compensateHisPriceDataParam, wrapResult));
        }
        return wrapResult;
    }
}
