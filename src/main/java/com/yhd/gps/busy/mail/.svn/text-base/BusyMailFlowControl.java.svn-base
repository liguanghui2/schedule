package com.yhd.gps.busy.mail;

/**
 * 邮件发送流量控制
 *
 * @author Hikin Yao
 * @version 1.0
 */
public interface BusyMailFlowControl {
    /**
     * 流量控制,判断流量是否超标
     */
    public boolean flowControlOverLoaded(Long invokeTime);

    /**
     * 流量控制状态重置
     *
     * @return
     */
    public boolean resetFlowControl();
}
