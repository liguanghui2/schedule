package com.yhd.gps.schedule.command;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import com.yhd.gps.busyservice.service.BusyGpsProductSyncService;
import com.yhd.gps.schedule.common.Command;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-5-19 上午10:29:57
 */
public class GpsProductSyncCommand implements Command<Long> {

    private BusyGpsProductSyncService busyGpsProductSyncService;
    private CountDownLatch finishSignal;
    private Date startTime;
    private Date endTime;
    private Long pageSize;

    public GpsProductSyncCommand(BusyGpsProductSyncService busyGpsProductSyncService, CountDownLatch finishSignal,
                                 Date startTime, Date endTime, Long pageSize) {
        super();
        this.busyGpsProductSyncService = busyGpsProductSyncService;
        this.finishSignal = finishSignal;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pageSize = pageSize;
    }

    @Override
    public Long action() {
        Long count = busyGpsProductSyncService.syncProductToGps(startTime, endTime, pageSize);
        finishSignal.countDown();

        return count;
    }

}