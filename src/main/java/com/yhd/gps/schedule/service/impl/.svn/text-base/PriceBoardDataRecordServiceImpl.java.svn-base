package com.yhd.gps.schedule.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.yhd.gps.pricechange.vo.PriceBoardInfoVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.dao.PriceBoardDataRecordDao;
import com.yhd.gps.schedule.service.PriceBoardDataRecordService;
import com.yhd.gps.schedule.vo.DateIntervalVo;
import com.yhd.gps.schedule.vo.PriceBoardDailyDataVo;
import com.yhd.gps.schedule.vo.PriceBoardMonthlyDataVo;
import com.yhd.gps.schedule.vo.PriceBoardWeeklyDataVo;

/**
 * @author sunfei
 * @CreateTime 2017-6-30 上午10:53:31
 */
public class PriceBoardDataRecordServiceImpl implements PriceBoardDataRecordService {
    private PriceBoardDataRecordDao priceBoardDataRecordDao;
    
    public void setPriceBoardDataRecordDao(PriceBoardDataRecordDao priceBoardDataRecordDao) {
        this.priceBoardDataRecordDao = priceBoardDataRecordDao;
    }

    @Override
    public int batchInsertPriceBoardData(List<PriceBoardInfoVo> priceBoardInfoVos, String businessType,
                                         DateIntervalVo dateIntervalVo) {
        int result = 0;
        if(CollectionUtils.isEmpty(priceBoardInfoVos) || businessType == null) {
            return result;
        }
        // 日售价看板定时任务
        if(ScheduleConstants.PRICE_BOARD_DAILY.equals(businessType)) {
            List<PriceBoardDailyDataVo> priceBoardDailyDataVos = new ArrayList<PriceBoardDailyDataVo>();
            for(PriceBoardInfoVo priceBoardInfoVo : priceBoardInfoVos) {
                PriceBoardDailyDataVo priceBoardDailyDataVo = buildPriceBoardDailyData(priceBoardInfoVo, dateIntervalVo);
                if(priceBoardDailyDataVo != null) {
                    priceBoardDailyDataVos.add(priceBoardDailyDataVo);
                }
            }
            result = priceBoardDataRecordDao.batchInsertPriceBoardDailyData(priceBoardDailyDataVos);
        }
        // 周售价看板定时任务
        if(ScheduleConstants.PRICE_BOARD_WEEKLY.equals(businessType)) {
            List<PriceBoardWeeklyDataVo> priceBoardWeeklyDataVos = new ArrayList<PriceBoardWeeklyDataVo>();
            for(PriceBoardInfoVo priceBoardInfoVo : priceBoardInfoVos) {
                PriceBoardWeeklyDataVo priceBoardWeeklyDataVo = buildPriceBoardWeeklyData(priceBoardInfoVo,
                        dateIntervalVo);
                if(priceBoardWeeklyDataVo != null) {
                    priceBoardWeeklyDataVos.add(priceBoardWeeklyDataVo);
                }
            }
            result = priceBoardDataRecordDao.batchInsertPriceBoardWeeklyData(priceBoardWeeklyDataVos);
        }
        // 月售价看板定时任务
        if(ScheduleConstants.PRICE_BOARD_MONTHLY.equals(businessType)
           || ScheduleConstants.PRICE_BOARD_QUARTERLY.equals(businessType)
           || ScheduleConstants.PRICE_BOARD_SEMESTERTLY.equals(businessType)) {
            List<PriceBoardMonthlyDataVo> priceBoardMonthlyDataVos = new ArrayList<PriceBoardMonthlyDataVo>();
            for(PriceBoardInfoVo priceBoardInfoVo : priceBoardInfoVos) {
                PriceBoardMonthlyDataVo priceBoardMonthlyDataVo = buildPriceBoardMonthlyData(priceBoardInfoVo,
                        businessType, dateIntervalVo);
                if(priceBoardMonthlyDataVo != null) {
                    priceBoardMonthlyDataVos.add(priceBoardMonthlyDataVo);
                }
            }
            result = priceBoardDataRecordDao.batchInsertPriceBoardMonthlyData(priceBoardMonthlyDataVos);
        }
        return result;
    }

    private PriceBoardDailyDataVo buildPriceBoardDailyData(PriceBoardInfoVo priceBoardInfoVo,
                                                           DateIntervalVo dateIntervalVo) {
        if(priceBoardInfoVo == null || dateIntervalVo == null) {
            return null;
        }
        Date date = new Date();
        PriceBoardDailyDataVo priceBoardDailyDataVo = new PriceBoardDailyDataVo();
        BeanUtils.copyProperties(priceBoardInfoVo, priceBoardDailyDataVo);
        priceBoardDailyDataVo.setCreateTime(date);
        priceBoardDailyDataVo.setStatisticsDate(ScheduleDateUtils.parseDate(dateIntervalVo.getStartDate()));
        return priceBoardDailyDataVo;
    }

    private PriceBoardWeeklyDataVo buildPriceBoardWeeklyData(PriceBoardInfoVo priceBoardInfoVo,
                                                             DateIntervalVo dateIntervalVo) {
        if(priceBoardInfoVo == null || dateIntervalVo == null) {
            return null;
        }
        Date date = new Date();
        Date weekStartDate = dateIntervalVo.getStartDate();
        Date weekEndDate = dateIntervalVo.getEndDate();
        PriceBoardWeeklyDataVo priceBoardWeeklyDataVo = new PriceBoardWeeklyDataVo();
        BeanUtils.copyProperties(priceBoardInfoVo, priceBoardWeeklyDataVo);
        priceBoardWeeklyDataVo.setCreateTime(date);
        priceBoardWeeklyDataVo.setYear(ScheduleDateUtils.getYear(weekStartDate));
        priceBoardWeeklyDataVo.setWeekNum(ScheduleDateUtils.getWeekNum(weekStartDate));
        priceBoardWeeklyDataVo.setWeekStartDate(weekStartDate);
        priceBoardWeeklyDataVo.setWeekEndDate(weekEndDate);
        return priceBoardWeeklyDataVo;
    }

    private PriceBoardMonthlyDataVo buildPriceBoardMonthlyData(PriceBoardInfoVo priceBoardInfoVo, String businessType,
                                                               DateIntervalVo dateIntervalVo) {
        if(priceBoardInfoVo == null || businessType == null || dateIntervalVo == null) {
            return null;
        }
        Date date = new Date();
        Date startDate = dateIntervalVo.getStartDate();
        Date endDate = dateIntervalVo.getEndDate();
        PriceBoardMonthlyDataVo priceBoardMonthlyDataVo = new PriceBoardMonthlyDataVo();
        BeanUtils.copyProperties(priceBoardInfoVo, priceBoardMonthlyDataVo);
        priceBoardMonthlyDataVo.setCreateTime(date);
        priceBoardMonthlyDataVo.setYear(ScheduleDateUtils.getYear(startDate));
        priceBoardMonthlyDataVo.setMonthStartDate(startDate);
        priceBoardMonthlyDataVo.setMonthEndDate(endDate);
        if(ScheduleConstants.PRICE_BOARD_MONTHLY.equals(businessType)) {
            priceBoardMonthlyDataVo.setStatisticsType(ScheduleConstants.PRICE_BOARD_STATISTICS_TYPE_MONTH);
            priceBoardMonthlyDataVo.setMonthNum(ScheduleDateUtils.getMonth(startDate));
        }
        if(ScheduleConstants.PRICE_BOARD_QUARTERLY.equals(businessType)) {
            priceBoardMonthlyDataVo.setStatisticsType(ScheduleConstants.PRICE_BOARD_STATISTICS_TYPE_QUARTERLY);
            String startMonth = ScheduleDateUtils.getMonth(startDate);
            String endMonth = ScheduleDateUtils.getMonth(endDate);
            priceBoardMonthlyDataVo.setMonthNum(startMonth + "-" + endMonth);
        }
        if(ScheduleConstants.PRICE_BOARD_SEMESTERTLY.equals(businessType)) {
            priceBoardMonthlyDataVo.setStatisticsType(ScheduleConstants.PRICE_BOARD_STATISTICS_TYPE_SEMESTERTLY);
            String startMonth = ScheduleDateUtils.getMonth(startDate);
            String endMonth = ScheduleDateUtils.getMonth(endDate);
            priceBoardMonthlyDataVo.setMonthNum(startMonth + "-" + endMonth);
        }
        return priceBoardMonthlyDataVo;
    }

}
