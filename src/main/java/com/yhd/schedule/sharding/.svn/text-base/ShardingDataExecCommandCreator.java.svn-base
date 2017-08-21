package com.yhd.schedule.sharding;

import java.util.concurrent.CountDownLatch;

import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-7 下午05:14:33
 */
public interface ShardingDataExecCommandCreator {

    @SuppressWarnings("rawtypes")
    public ShardingDataExecCommand create(final int shardingIndex, CountDownLatch finishSignal);

    /**
     * 获取业务类型，打印日志使用
     * @return
     */
    public String getBussinessType();

}
