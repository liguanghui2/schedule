package com.yhd.gps.schedule.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class ExecutorManager {

    private static ExecutorService executor = null;

    static {
        executor = (ExecutorService) SpringBeanUtil.getBean("commandExecutor");
    }

    @SuppressWarnings("all")
    public static <T> Future<T> executeOnly(final Command<T> command) {
        Future<T> future = new FutureTask<T>(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return command.action();
            }
        });
        if(executor != null) {
            executor.execute((FutureTask) future);
        }
        return future;
    }

    @SuppressWarnings("all")
    public static <R, D> Future<R> executeOnly(final ShardingDataExecCommand<R, D> command) {
        Future<R> future = new FutureTask<R>(new Callable<R>() {
            @Override
            public R call() throws Exception {
                return command.action();
            }
        });
        if(executor != null) {
            executor.execute((FutureTask) future);
        }
        return future;
    }

}
