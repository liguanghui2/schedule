package com.yhd.gps.schedule.service;

import java.util.List;

import com.yhd.gps.pricechange.vo.PriceBoardInfoVo;
import com.yhd.gps.schedule.vo.DateIntervalVo;

/**
 * @author sunfei
 * @CreateTime 2017-6-30 上午10:41:55
 */
public interface PriceBoardDataRecordService {

    /**
     * 批量插入价格看板数据
     * @param priceBoardInfoVos
     * @param businessType
     * @param dateIntervalVo
     * @return
     */
    public int batchInsertPriceBoardData(List<PriceBoardInfoVo> priceBoardInfoVos, String businessType ,DateIntervalVo dateIntervalVo);

}
