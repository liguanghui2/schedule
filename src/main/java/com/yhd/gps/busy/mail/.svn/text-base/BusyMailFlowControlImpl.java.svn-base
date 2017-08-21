package com.yhd.gps.busy.mail;

import java.util.LinkedList;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyMailFlowControlImpl implements BusyMailFlowControl {
    private Long timeRangeLimit;// 采样时间范围,单位ms
    private Integer sizeLimit;// 某段时间内,调用次数超过这个值,就认为流量过大
    private LinkedList<Long> invokeTimeList = new LinkedList<Long>();

    /**
     * 如果最后一次调用,队列刚好满了,并且最后一次调用时间与第一次调用时间差超过限制时间区间, 则移除头部元素.只要流量未超标,队列始终处于未满状态
     * 如果经过上面处理后,队列依然满了并且时间超过了限制范围,则认为流量超标
     * 
     * @param invokeTime
     * @return true:流量超标 false:流量不超标
     */
    public synchronized boolean flowControlOverLoaded(Long invokeTime) {
        boolean result = false;// 默认流量状态不超标
        invokeTimeList.add(invokeTime);
        // 如果最后一次调用,队列刚好满了,并且最后一次调用时间与第一次调用时间没有超过限制时间区间,
        // 则移除头部元素.只要流量未超标,队列始终处于未满状态
        if(invokeTimeList.size() == sizeLimit
           && (invokeTimeList.getLast() - invokeTimeList.getFirst() > timeRangeLimit)) {
            invokeTimeList.removeFirst();
        }
        // 如果经过上面处理后,队列依然满了并且时间超过了限制范围,则认为流量超标
        if(invokeTimeList.size() >= sizeLimit) {
            result = true;
        }
        return result;
    }

    @Override
    public synchronized boolean resetFlowControl() {
        invokeTimeList.clear();
        return false;
    }

    public void setTimeRangeLimit(Long timeRangeLimit) {
        this.timeRangeLimit = timeRangeLimit;
    }

    public synchronized void setSizeLimit(Integer sizeLimit) {
        this.sizeLimit = sizeLimit;
    }
}
