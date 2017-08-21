package com.yhd.gps.schedule.parall;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * ---- 任务基类 ------
 * @Author lipengfei
 * @CreateTime 2016-8-25 下午08:18:49
 */
public abstract class BaseTask<R> implements Runnable {

    // 分批处理每次参数大小
    public final static int MSG_SIZE = 50;
    // 任务编号
    protected Integer taskNo;
    // 任务处理完成信号量
    protected CountDownLatch finishSignal;
    // 汇总所有任务返回结果 key为任务编号
    protected Map<Integer, R> resultMap;

    // 处理具体的任务
    protected abstract R runTask();

    @Override
    public void run() {
        resultMap.put(taskNo, runTask());
        finishSignal.countDown();
    }
}
