package com.yhd.gps.schedule.vo;

import java.util.Date;

/**
 * 
 * 价格促销精简vo
 * @Author lipengcheng
 * @CreateTime 2016-7-12 下午04:09:05
 */
public class ProductPromRule4ResetSoldNumVo implements java.io.Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long pmId;

    /**
     * 促销类型
     */
    private int promoteType;

    /**
     * 活动状态
     */
    private int ruleStatus;

    /**
     * 促销开始时间
     */
    private Date promStartTime;

    /**
     * 促销结束时间
     */
    private Date promEndTime;

    /**
     * 当前这条促销已经售出数量
     */
    private Integer soldNum;

    /**
     * 商品售出时间
     */
    private Date soldDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPmId() {
        return pmId;
    }

    public void setPmId(Long pmId) {
        this.pmId = pmId;
    }

    public int getPromoteType() {
        return promoteType;
    }

    public void setPromoteType(int promoteType) {
        this.promoteType = promoteType;
    }

    public int getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(int ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public Date getPromStartTime() {
        return promStartTime;
    }

    public void setPromStartTime(Date promStartTime) {
        this.promStartTime = promStartTime;
    }

    public Date getPromEndTime() {
        return promEndTime;
    }

    public void setPromEndTime(Date promEndTime) {
        this.promEndTime = promEndTime;
    }

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    @Override
    public String toString() {
        return "ProductPromRule4ResetSoldNumVo [id=" + id + ", pmId=" + pmId + ", promoteType=" + promoteType
               + ", ruleStatus=" + ruleStatus + ", promStartTime=" + promStartTime + ", promEndTime=" + promEndTime
               + ", soldNum=" + soldNum + ", soldDate=" + soldDate + "]";
    }

}