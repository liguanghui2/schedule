package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * pm_price表数据清理（永久下架商品）
 * @Author  liling
 * @CreateTime  2017-7-11 下午03:47:06
 */
public class PmPriceDataCleanJob extends GpsBaseJob {

    private static final Log logger = LogFactory.getLog(GpsPriceChangeMsgSendJob.class);

    private ShardingDataProcessor pmPriceDataCleanProcessor;

    public void setPmPriceDataCleanProcessor(ShardingDataProcessor pmPriceDataCleanProcessor) {
        this.pmPriceDataCleanProcessor = pmPriceDataCleanProcessor;
    }

    @Override
    public void init() {
        jobName = "pm_price表数据清理定时器";
        jobDesc = "pm_price表数据清理";

    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        try {
            pmPriceDataCleanProcessor.process();
            logger.debug("---pm_price表数据清理任务结束执行 --总共耗费时间:" + (System.currentTimeMillis() - start) / 1000 + " 秒---");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
