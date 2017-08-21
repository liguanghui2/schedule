package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.schedule.vo.JDBookSyncLogVo;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public interface JDBookSyncLogDao {

    /**
     * 批量新增京东图书同步日志
     * @param jdBookSyncLogVos
     * @return
     */
    public int batchInsertJDBookSyncLogVoList(List<JDBookSyncLogVo> jdBookSyncLogVos);
}
