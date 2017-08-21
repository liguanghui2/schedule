package com.yhd.gps.busyservice.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.BusyPriceJumperLogDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.vo.JumperMessageLog;
import com.yihaodian.busy.exception.BusyException;

/**
 * 记录价格消息发送日志
 * 
 * @Author wangxiaowu
 * @CreateTime 2014-8-5 上午10:29:57
 */
public class BusyPriceJumperLogDaoImpl extends ScheduleBaseDao implements BusyPriceJumperLogDao {

    @Override
    public void batchInsertMessageLog(final List<JumperMessageLog> messageLogs) {
        if(CollectionUtils.isEmpty(messageLogs)) {
            return;
        }
        try {
            batchInsert("insertJumperMessageLogVo", messageLogs);
        } catch (Exception e) {
            throw new BusyException("insertJumperMessageLogVo批量失败!", e.getCause());
        }
    }
}
