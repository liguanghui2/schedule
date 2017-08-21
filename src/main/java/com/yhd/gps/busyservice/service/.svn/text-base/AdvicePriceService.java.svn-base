package com.yhd.gps.busyservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yihaodian.backend.price.base.ResultDto;
import com.yihaodian.backend.price.cityPick.vo.ConfirmedPriceVo;
import com.yihaodian.backend.price.cityPick.vo.SensitivePageVo;
import com.yihaodian.pis.service.api.vo.OpponSiteBasePrice;

/**
 * ---- 请加注释 ------
 * @Author  shengxu1
 * @CreateTime  2017-4-28 下午04:34:29
 */
public interface AdvicePriceService{
    
    /**
     * 获取档期商品数据
     */
    List<ConfirmedPriceVo> getScheduleConfirmedPrice(int shardingIndex, Integer pageSize);
    
    /**
     * 处理计算建议价
     */
    ResultDto<Boolean> handleConfirmedPrice(List<ConfirmedPriceVo> confirmedPriceVos);
    
    /**
     * 分页查询敏感品
     * @param shardingIndex
     * @param pageSize
     * @param currentPage
     * @return
     */
    List<SensitivePageVo> getPmSensitiveInfoVoByCondition(int shardingIndex, Integer pageSize, Integer currentPage);

    /**
     * 获取PIS价格
     * @param pmIds
     * @param siteId
     * @return
     */
    Map<Long, OpponSiteBasePrice> queryOpponBasePriceByPmIds(List<Long> pmIds, Integer siteId);

    /**
     * 获取Pis价格
     * @param pmIds
     * @return
     */
    Map<Long, BigDecimal> queryAiPriceByPmIds(List<Long> pmIds);

    /**
     *update sensitive price
     */
    void updateSensitivePrice(List<SensitivePageVo> sensitivePageVos);
}

