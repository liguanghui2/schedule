package com.yhd.gps.schedule.vo;

import java.util.Date;

/**
 * 
 * lp分时段促销活动时间
 * @Author lipengcheng
 * @CreateTime 2016-8-5 下午01:10:04
 */
public class PromotionPeriodTimeVo {
    /**
     * 主键Id
     */
    private Long id;
    /**
     * 促销id
     */
    private Long promotionId;
    /**
     * 活动开始时间
     */
    private Date startDate;
    /**
     * 活动截止时间
     */
    private Date endDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "PromotionPeriodTimeVo [id=" + id + ", promotionId=" + promotionId + ", startDate=" + startDate
               + ", endDate=" + endDate + "]";
    }

}
