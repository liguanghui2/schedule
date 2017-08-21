package com.yhd.gps.schedule.parall;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.schedule.parall.result.WrapResult;

/**
 * ---- 同一任务管理器基类 ------
 * @Author  lipengfei
 * @CreateTime  2016-8-25 下午11:51:45
 */
public abstract class BaseTaskManager {

    protected final Log logger = LogFactory.getLog(BaseTaskManager.class);
    
    // 任务线程池
    protected ExecutorService executor;
    
    // 最长等待时间
    public int maxWaitTime = 120;
    
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public abstract <R> R executeParallTask();
    
    public void waitParallTaskResult(WrapResult wrapResult) {
        // 等待总任务处理完毕，如果超时则强制中断
        try {
            wrapResult.getFinishSignal().await(maxWaitTime, TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.error("###总任务调度时间超过最大等待时间,超时强制中断!###");
        }
    }
}

