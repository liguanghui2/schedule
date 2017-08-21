package com.yhd.gps.schedule.sharding.processor;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.yhd.gps.busyservice.service.BusyPromRuleCleanService;
import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-5-19 下午05:28:03
 */
public class PromotionDataesCleanShardingDataProcessor extends ShardingDataProcessor {

    private static final Logger LOG = Logger.getLogger(GpsProductSyncShardingDataProcessor.class);

    // pageSize=1000;outDays=90;deleteDays=30;isRunable=true;whiteip=10.63.13.56
    private static final class CleanConfig {
        private static final Integer pageSize = 1000;

        private static final Integer outDays = 90;

        private static final Date outDate = DateUtils.addDays(new Date(), -1 * outDays);

        private static final Integer deleteDays = 30;

        private static final Date deleteDate = DateUtils.addDays(new Date(), -1 * deleteDays);
    }

    private BusyPromRuleCleanService busyPromRuleCleanService;
    private String bussinessType;

    public void setBusyPromRuleCleanService(BusyPromRuleCleanService busyPromRuleCleanService) {
        this.busyPromRuleCleanService = busyPromRuleCleanService;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    @Override
    protected void processCore(int shardingIndex, CountDownLatch finishSignal) throws InterruptedException, ExecutionException {
        cleanOutDatePromRule();
        cleanOutDateGpsPromotion();

        finishSignal.countDown();
    }

    // 清理过期的product_prom_rule
    private void cleanOutDatePromRule() {
        Long start = System.currentTimeMillis();
        try {
            Long deleteRow = busyPromRuleCleanService.cleanOutDatePromRule(CleanConfig.outDate, CleanConfig.deleteDate,
                    CleanConfig.pageSize);
            LOG.debug("---###---[GPS]清理过期product_prom_rule数据定时任务开始执行结束:---总共耗费时间:"
                      + (System.currentTimeMillis() - start) / 1000 + " 秒, 清理数据 " + deleteRow + "条 ---###---");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    // 清理过期的gps_promotion
    private void cleanOutDateGpsPromotion() {
        Long start = System.currentTimeMillis();
        try {
            Long deleteRow = busyPromRuleCleanService.cleanOutDateGpsPromotion(CleanConfig.outDate,
                    CleanConfig.deleteDate, CleanConfig.pageSize);
            LOG.debug("---###---[GPS]清理过期gps_promotion数据定时任务开始执行结束:---总共耗费时间:" + (System.currentTimeMillis() - start)
                      / 1000 + " 秒, 清理数据 " + deleteRow + "条 ---###---");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected String getBussinessType() {
        return bussinessType;
    }
}
