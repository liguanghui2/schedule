package com.yhd.gps.schedule.job;

import org.apache.log4j.Logger;

import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * @author:liguanghui1
 * @date ：2017-3-15 上午10:29:47
 */
public class InitBaseDataMsgSendJob extends GpsBaseJob {

    private static final Logger LOG = Logger.getLogger(InitBaseDataMsgSendJob.class);

    private ShardingDataProcessor initOffLineDataShardingDataProcessor;

    public void setInitOffLineDataShardingDataProcessor(ShardingDataProcessor initOffLineDataShardingDataProcessor) {
        this.initOffLineDataShardingDataProcessor = initOffLineDataShardingDataProcessor;
    }

    /*
     * 初始化Job
     */
    @Override
    public void init() {
        jobName = "离线pool初始化数据定时器";
        jobDesc = "获取pm_price表的pmId发消息给offline-data";
    }

    /*
     * 执行Job
     */
    @Override
    public void doJobTask() {
        Long start = System.currentTimeMillis();
        initOffLineDataShardingDataProcessor.process();
        LOG.debug("---####[GPS]获取pm_price表的pm_id发消息给offline-data定时任务执行结束####----耗时："
                  + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

}
