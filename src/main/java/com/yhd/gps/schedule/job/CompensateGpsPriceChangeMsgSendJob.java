package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * 补偿价格变动表已处理、历史价格表无数据的记录
 * @Author  lipengfei
 * @CreateTime  2016-8-11 下午04:51:45
 */
public class CompensateGpsPriceChangeMsgSendJob extends GpsBaseJob {

    private static final Log logger = LogFactory.getLog(CompensateGpsPriceChangeMsgSendJob.class);

    private ShardingDataProcessor compensateGpsPriceChangeMsgSendProcessor;

    public void setCompensateGpsPriceChangeMsgSendProcessor(ShardingDataProcessor compensateGpsPriceChangeMsgSendProcessor) {
        this.compensateGpsPriceChangeMsgSendProcessor = compensateGpsPriceChangeMsgSendProcessor;
    }

    @Override
    public void init() {
        jobName = "补偿价格变动表已处理、历史价格表无数据定时器";
        jobDesc = "补偿价格变动表已处理、历史价格表无数据的记录";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        try {
            compensateGpsPriceChangeMsgSendProcessor.setProcessShardingMaxTime(120);
            compensateGpsPriceChangeMsgSendProcessor.process();
            logger.debug("---补偿价格变动表已处理、历史价格表无数据的记录任务结束执行 --总共耗费时间:" + (System.currentTimeMillis() - start) / 1000
                         + " 秒---");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}