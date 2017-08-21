package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.schedule.parall.manager.CompensateHistoryPriceDataParallTaskManager;
import com.yhd.gps.schedule.parall.result.WrapResult;

/**
 * 补偿历史价格表无数据的记录job(取所有自营商家的商品)
 * @Author  lipengfei
 * @CreateTime  2016-8-11 下午04:51:45
 */
public class CompensateHistoryPriceDataJob extends GpsBaseJob {

    private static final Log logger = LogFactory.getLog(CompensateHistoryPriceDataJob.class);
    
    private CompensateHistoryPriceDataParallTaskManager compensateHistoryPriceDataParallTaskManager;
    
    public void setCompensateHistoryPriceDataParallTaskManager(CompensateHistoryPriceDataParallTaskManager compensateHistoryPriceDataParallTaskManager) {
        this.compensateHistoryPriceDataParallTaskManager = compensateHistoryPriceDataParallTaskManager;
    }

    @Override
    public void init() {
        jobName = "补偿历史价格表无数据的记录job定时器";
        jobDesc = "补偿历史价格表无数据的记录job";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        try {
            WrapResult<Void> wrapResult = compensateHistoryPriceDataParallTaskManager.executeParallTask();
            compensateHistoryPriceDataParallTaskManager.waitParallTaskResult(wrapResult);
            logger.debug("---补偿历史价格表无数据的记录job任务结束执行 --总共耗费时间:" + (System.currentTimeMillis() - start) / 1000
                         + " 秒---");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}