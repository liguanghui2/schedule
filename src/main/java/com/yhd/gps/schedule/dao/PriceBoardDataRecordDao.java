package com.yhd.gps.schedule.dao;

import java.util.List;

import com.yhd.gps.schedule.vo.PriceBoardDailyDataVo;
import com.yhd.gps.schedule.vo.PriceBoardMonthlyDataVo;
import com.yhd.gps.schedule.vo.PriceBoardWeeklyDataVo;

/**
 * @author sunfei
 * @CreateTime 2017-6-30 下午01:37:34
 */
public interface PriceBoardDataRecordDao {

    /**
     * 批量插入每日售价看板数据
     * @param priceBoardDailyDataVo
     * @return
     */
    public int batchInsertPriceBoardDailyData(List<PriceBoardDailyDataVo> priceBoardDailyDataVos);

    /**
     * 批量插入每周售价看板数据
     * @param priceBoardWeeklyDataVos
     * @return
     */
    public int batchInsertPriceBoardWeeklyData(List<PriceBoardWeeklyDataVo> priceBoardWeeklyDataVos);

    /**
     * 批量插入月售价看板数据
     * @param priceBoardMonthlyDataVos
     * @return
     */
    public int batchInsertPriceBoardMonthlyData(List<PriceBoardMonthlyDataVo> priceBoardMonthlyDataVos);
    
    /**
     * 根据周次删除周历史价格数据
     */
    public int deleteWeekPriceBoardByWeekNum(Integer weekNum);

}
