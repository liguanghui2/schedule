package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 促销的lp信息
 * @Author lipengcheng
 * @CreateTime 2016-7-1 下午04:59:34
 */
public class BusyLandingProductVo implements Serializable {
    private static final long serialVersionUID = -3462995947892209784L;
    /**
     * 表pm_info主键Id
     */
    private Long pmId;
    /**
     * 促销Id
     */
    private Long promotionId;
    /**
     * 产品Id
     */
    private Long productId;
    /**
     * 商家Id
     */
    private Long merchantId;
    /**
     * 售出限制数量类型 单日 or 总量
     */
    private Integer totalQuantityLimitType;
    /**
     * 促销类型
     */
    private Integer isPeriod;
    /**
     * 售出限制数量
     */
    private Integer totalQuantityLimit;
    /**
     * 当前已售出数量
     */
    private Integer soldNum;
    /**
     * 促销价
     */
    private BigDecimal activityPrice;
    /**
     * 活动积分
     */
    private BigDecimal activityPoint;
    /**
     * 促销开始时间
     */
    private Date startTime;
    /**
     * 促销结束时间
     */
    private Date endTime;

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getTotalQuantityLimitType() {
        return totalQuantityLimitType;
    }

    public void setTotalQuantityLimitType(Integer totalQuantityLimitType) {
        this.totalQuantityLimitType = totalQuantityLimitType;
    }

    public Integer getTotalQuantityLimit() {
        return totalQuantityLimit;
    }

    public void setTotalQuantityLimit(Integer totalQuantityLimit) {
        this.totalQuantityLimit = totalQuantityLimit;
    }

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public BigDecimal getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(BigDecimal activityPrice) {
        this.activityPrice = activityPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getActivityPoint() {
        return activityPoint;
    }

    public void setActivityPoint(BigDecimal activityPoint) {
        this.activityPoint = activityPoint;
    }

    public Long getPmId() {
        return pmId;
    }

    public void setPmId(Long pmId) {
        this.pmId = pmId;
    }

    public Integer getIsPeriod() {
        return isPeriod;
    }

    public void setIsPeriod(Integer isPeriod) {
        this.isPeriod = isPeriod;
    }

    @Override
    public String toString() {
        return "BusyLandingProductVo [pmId=" + pmId + ", promotionId=" + promotionId + ", productId=" + productId
               + ", merchantId=" + merchantId + ", totalQuantityLimitType=" + totalQuantityLimitType + ", isPeriod="
               + isPeriod + ", totalQuantityLimit=" + totalQuantityLimit + ", soldNum=" + soldNum + ", activityPrice="
               + activityPrice + ", activityPoint=" + activityPoint + ", startTime=" + startTime + ", endTime="
               + endTime + "]";
    }

}
