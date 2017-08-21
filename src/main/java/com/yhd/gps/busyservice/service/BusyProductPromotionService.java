package com.yhd.gps.busyservice.service;

import java.util.Date;
import java.util.List;

import com.yhd.gps.schedule.vo.BusyLandingProductVo;

/**
 * 
 * landingPage促销信息service， 该service仅查询promotion_v2表
 * @Author lipengcheng
 * @CreateTime 2016-7-5 下午03:19:05
 */
public interface BusyProductPromotionService {
    /**
     * 获取langdingpage 商品价格信息
     * @param startDate
     * @param endDate
     * @param promotionIds
     * @param startRow
     * @param endRow
     * @return
     */
    public List<BusyLandingProductVo> getLandingProductVoListByDate(Date startDate, Date endDate,
                                                                    List<Long> promotionIds, Integer startRow,
                                                                    Integer endRow);

    /**
     * 比对gps_promotion与促销promotion
     * @param startDate
     * @param endDate
     * @param pageSize
     */
    public void compareLPPromotion(Date startDate, Date endDate, Integer pageSize);

}