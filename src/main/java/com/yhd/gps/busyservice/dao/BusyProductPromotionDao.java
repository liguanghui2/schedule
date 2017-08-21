package com.yhd.gps.busyservice.dao;

import java.util.Date;
import java.util.List;

import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.PromotionPeriodTimeVo;

/**
 * 
 * 获取langdingpage商品价格信息
 * @Author lipengcheng
 * @CreateTime 2016-6-30 下午04:49:32
 */
public interface BusyProductPromotionDao {

    /**
     * 根据开始时间截止时间查询promotionId列表
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Long> batchGetLandingProductPromotionIdListByDate(Date startDate, Date endDate);

    /**
     * 获取langdingpage商品价格信息, 关联PROMOTIONV2、PROMOTIONV2_LANDING_PRODUCT
     * @param startDate
     * @param endDate
     * @param promotionIds
     * @param startRow
     * @param endRow
     * @return
     */
    public List<BusyLandingProductVo> batchGetLandingProductVoListByDate(Date startDate, Date endDate,
                                                                         List<Long> promotionIds, Integer startRow,
                                                                         Integer endRow);

    /**
     * 获取langdingpage商品价格信息(1号商城),
     * 关联PROMOTIONV2、PROMOTIONV2_LANDING_PRODUCT、PROMOTIONV2_MERCHANT、PM_INFO
     * @param startDate
     * @param endDate
     * @param promotionIds
     * @param productIds
     * @return
     */
    public List<BusyLandingProductVo> batchGetLandingProductVoListByDate41Mall(Date startDate, Date endDate,
                                                                               List<Long> promotionIds,
                                                                               List<Long> productIds);

    /**
     * 根据promotionIds获取lp分时段促销起止时间
     * @param promotionIds
     * @return
     */
    public List<PromotionPeriodTimeVo> batchGetPromotionPeriodTimeVoList(List<Long> promotionIds);
}
