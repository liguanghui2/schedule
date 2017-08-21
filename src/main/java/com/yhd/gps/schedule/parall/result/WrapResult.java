package com.yhd.gps.schedule.parall.result;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * ---- 结果集 ------
 * @Author  lipengfei
 * @CreateTime  2016-8-26 上午10:44:08
 */
public class WrapResult<V> {

    private CountDownLatch finishSignal;
    
    private Map<Integer, V> resultMap = new HashMap<Integer, V>();

    public CountDownLatch getFinishSignal() {
        return finishSignal;
    }

    public void setFinishSignal(CountDownLatch finishSignal) {
        this.finishSignal = finishSignal;
    }

    public Map<Integer, V> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<Integer, V> resultMap) {
        this.resultMap = resultMap;
    }

    public WrapResult(CountDownLatch finishSignal) {
        super();
        this.finishSignal = finishSignal;
    }
}

