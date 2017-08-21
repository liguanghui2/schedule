package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.schedule.vo.GpsScheduleWarningRuleVo;

/**
 * 
 * 定时器报警规则表
 * @Author lipengcheng
 * @CreateTime 2017-3-3 上午10:12:03
 */
public interface GpsScheduleWarningRuleDao {

    /**
     * 根据切片和id查规则
     * @param shardingIndex
     * @param ids
     * @return
     */
    List<GpsScheduleWarningRuleVo> getGpsScheduleWarningRuleVo(int shardingIndex, List<Long> ids);

    /**
     * 全查询
     * @return
     */
    List<GpsScheduleWarningRuleVo> getAllGpsScheduleWarningRuleVoList();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    GpsScheduleWarningRuleVo getGpsScheduleWarningRuleVoById(Long id);

    /**
     * 新增规则
     * @param gpsScheduleWarningRuleVo
     * @return
     */
    Long insertGpsScheduleWarningRuleVo(GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo);

    /**
     * 修改规则
     * @param gpsScheduleWarningRuleVo
     * @return
     */
    int updateGpsScheduleWarningRuleVo(GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo);

    /**
     * 删除规则
     * @param GpsScheduleWarningRuleVo
     * @return
     */
    int deleteGpsScheduleWarningRuleVoById(Long id);
}
