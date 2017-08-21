package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.schedule.vo.SyncDataLogVo;

public interface SyncDataLogDao {

	/**
	 * 写入一条日志记录
	 * @param inputVo
	 * @return
	 */
	public int insertSyncDataLog(SyncDataLogVo syncDataLogVo);
	
	/**
	 * 返回1000条记录用于做重试处理
	 * @return
	 */
	public List<SyncDataLogVo> getSyncDataLogVoList();
	
	/**
	 * 批量删除已经处理的日志
	 * @param idList
	 * @return
	 */
	public int batchDeleteSyncDataLogVo(List<Long> idList);

	/**
	 * 批量保存同步出错日志
	 * @param inputVoList
	 * @return
	 */
	int batchInsertSyncDataLog(List<SyncDataLogVo> syncDataLogVos);
}
