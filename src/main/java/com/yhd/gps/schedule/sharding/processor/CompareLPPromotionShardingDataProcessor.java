package com.yhd.gps.schedule.sharding.processor;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import com.yhd.gps.busyservice.service.BusyProductPromotionService;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.utils.DateUtil;
import com.yhd.schedule.sharding.core.ShardingDataProcessor;

public class CompareLPPromotionShardingDataProcessor extends ShardingDataProcessor {

    private String bussinessType;

    private static final Integer pageSize = 200;

    private BusyProductPromotionService busyProductPromotionService;

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    @Override
    protected String getBussinessType() {
        return bussinessType;
    }

    public void setBusyProductPromotionService(BusyProductPromotionService busyProductPromotionService) {
        this.busyProductPromotionService = busyProductPromotionService;
    }

    @Override
    protected void processCore(int shardingIndex, CountDownLatch finishSignal) throws InterruptedException, ExecutionException {
        // 获取当天0点时间
        Date currDay = ScheduleDateUtils.parseDate(new Date());
        // 比较当前时间前后连续3天的促销差异，从前一天00：00:00开始到后一天23:59:59结束
        Date startDate = DateUtil.addDays(currDay, -1);
        Date endDate = DateUtil.addDays(currDay, 1);
        busyProductPromotionService.compareLPPromotion(startDate, endDate, pageSize);
    }

}
