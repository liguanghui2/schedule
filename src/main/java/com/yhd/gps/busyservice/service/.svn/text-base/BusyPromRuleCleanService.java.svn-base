package com.yhd.gps.busyservice.service;

import java.util.Date;

public interface BusyPromRuleCleanService {

    /**
     * 清除product_prom_rule表中的过期的历史数据 1、查询历史数据 2、清理历史数据 3、记录删除日志到日志表
     * @return
     */
    public Long cleanOutDatePromRule(Date outDate, Date deleteDate, Integer pageSize);

    /**
     * 清除gps_promotion表中的过期的历史数据 1、查询历史数据 2、清理历史数据 3、记录删除日志到日志表
     * @return
     */
    public Long cleanOutDateGpsPromotion(Date outDate, Date deleteDate, Integer pageSize);

}
