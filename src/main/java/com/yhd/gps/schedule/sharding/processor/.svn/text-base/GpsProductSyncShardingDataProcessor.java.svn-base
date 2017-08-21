package com.yhd.gps.schedule.sharding.processor;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.busyservice.service.BusyGpsProductSyncService;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.command.GpsProductSyncCommand;
import com.yhd.gps.schedule.common.ExecutorManager;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-5-19 上午10:36:19
 */
public class GpsProductSyncShardingDataProcessor extends ShardingDataProcessor {

    private static final Logger LOG = Logger.getLogger(GpsProductSyncShardingDataProcessor.class);
    private BusyGpsProductSyncService busyGpsProductSyncService;
    private BusyMiscConfigDao busyMiscConfigDao;

    private String bussinessType;
    private int handleSyncDataLogMaxCount = 100;
    private int syncProductToGpsMaxCount = 100;
    private Long pageSize = 20L;
    private int syncPeriod = 20; // 同步的数据查表时间区间
    private int concurrentSize = 10;
    private int processMaxTime = 5;

    public void setBusyGpsProductSyncService(BusyGpsProductSyncService busyGpsProductSyncService) {
        this.busyGpsProductSyncService = busyGpsProductSyncService;
    }

    public void setBusyMiscConfigDao(BusyMiscConfigDao busyMiscConfigDao) {
        this.busyMiscConfigDao = busyMiscConfigDao;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public void setHandleSyncDataLogMaxCount(int handleSyncDataLogMaxCount) {
        this.handleSyncDataLogMaxCount = handleSyncDataLogMaxCount;
    }

    public void setSyncProductToGpsMaxCount(int syncProductToGpsMaxCount) {
        this.syncProductToGpsMaxCount = syncProductToGpsMaxCount;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public void setSyncPeriod(int syncPeriod) {
        this.syncPeriod = syncPeriod;
    }

    public void setConcurrentSize(int concurrentSize) {
        this.concurrentSize = concurrentSize;
    }

    public void setProcessMaxTime(int processMaxTime) {
        this.processMaxTime = processMaxTime;
    }

    @Override
    protected void processCore(int shardingIndex, CountDownLatch finishSignal) throws InterruptedException, ExecutionException {
        handleSyncDataLog();

        syncProductToGps();

        finishSignal.countDown();
    }

    private void syncProductToGps() throws InterruptedException {
        // 先取出最后同步的时间
        BusyMiscConfigVo lastSyncDate = MiscConfigUtils.getLastSyncDate();
        if(lastSyncDate == null || StringUtils.isBlank(lastSyncDate.getItemValue())) {
            return;
        }

        // 同步数据时间区间的开始时间
        String startTimeStr = lastSyncDate.getItemValue();
        Date startTime = ScheduleDateUtils.parseDate(startTimeStr);
        Date endTime = ScheduleDateUtils.addMinutes(startTime, 0);

        int count = 0;
        // 同步数据时间区间的开始时间小于(当前时间-时间周期syncPeriod)
        while(startTime.before(ScheduleDateUtils.addMinutes(new Date(), -120))) {
            if(count++ > syncProductToGpsMaxCount) {
                break;
            }

            CountDownLatch gpsProductSyncSignal = new CountDownLatch(concurrentSize);
            for(int concurrent = 1; concurrent <= concurrentSize; concurrent++) {
                endTime = ScheduleDateUtils.addMinutes(startTime, syncPeriod);

                LOG.info("sync concurrent thread : " + concurrent + ", 数据抓取开始时间 : "
                         + ScheduleDateUtils.format(startTime) + ", 数据抓取结束时间: " + ScheduleDateUtils.format(endTime));

                GpsProductSyncCommand command = new GpsProductSyncCommand(busyGpsProductSyncService,
                    gpsProductSyncSignal, startTime, endTime, pageSize);
                startTime = ScheduleDateUtils.addMinutes(endTime, 0);

                ExecutorManager.executeOnly(command);
            }
            gpsProductSyncSignal.await(processMaxTime, TimeUnit.MINUTES);

            LOG.info("-------开始时间 : " + startTime + " 产品数据同步完成-------------------------");

            // 保存最后同步的时间
            lastSyncDate.setItemValue(ScheduleDateUtils.format(endTime));
            busyMiscConfigDao.updateBusyMiscConfigVo(lastSyncDate);

            startTime = endTime;
        }
    }

    private void handleSyncDataLog() {
        int handleSyncDataLogCount = 0;
        // 先重试出错的
        while(true) {
            if(handleSyncDataLogCount++ > handleSyncDataLogMaxCount) {
                break;
            }
            busyGpsProductSyncService.handleSyncDataLog();
        }
    }

    @Override
    protected String getBussinessType() {
        return bussinessType;
    }

}