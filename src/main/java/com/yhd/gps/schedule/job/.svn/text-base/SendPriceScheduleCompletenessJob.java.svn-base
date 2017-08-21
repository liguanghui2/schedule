package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * @author:lshengxu1
 * @date ：2017-5-15 下午01:29:10
 */
public class SendPriceScheduleCompletenessJob extends GpsBaseJob {

    private static final Logger LOG = Logger.getLogger(SendPriceScheduleCompletenessJob.class);
    private ShardingDataProcessor sendPriceScheduleCompletenessProcessor;

    public void setSendPriceScheduleCompletenessProcessor(ShardingDataProcessor sendPriceScheduleCompletenessProcessor) {
        this.sendPriceScheduleCompletenessProcessor = sendPriceScheduleCompletenessProcessor;
    }

    @Override
    public void init() {
        jobName = "定时发送定价当期完整性告警邮件Job";
        jobDesc = "定时发送定价当期完整性告警邮件Job";
    }

    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        sendPriceScheduleCompletenessProcessor.process();
        LOG.debug("---####定时发送定价当期完整性告警邮件定时任务执行结束####----耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

}
