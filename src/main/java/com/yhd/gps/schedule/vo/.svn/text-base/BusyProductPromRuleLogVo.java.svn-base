package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 后台销售价格管理设置的抢购价规则
 *
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyProductPromRuleLogVo extends BaseLogVo implements Serializable {
    private static final long serialVersionUID = -4544114264337985547L;
    /**
     * 主键Id
     */
    private Long id;
    /**
     * 记录创建时间
     */
    private Date createTime;
    /**
     * 操作类型
     */
    private String opType;
    /**
     * rule记录主键Id
     */
    private Long ruleId;
    /**
     * 产品Id
     */
    private Long productId;
    /**
     * 商家Id
     */
    private Long merchantId;
    /**
     * 促销类型
     */
    private Integer promoteType = 0;
    /**
     * 促销一号店价
     */
    private BigDecimal promNonMemberPrice = BigDecimal.ZERO;
    /**
     * 每人特价限制数量
     */
    private Integer userPriceLimitNumber;
    /**
     * 特价限制总数量
     */
    private Integer specialPriceLimitNumber;
    /**
     * 促销开始时间
     */
    private Date promStartTime;
    /**
     * 促销结束时间
     */
    private Date promEndTime;
    /**
     * 促销名称
     */
    private String promName;
    /**
     * 后台操作员Id
     */
    private Long backendOperatorId;
    /**
     * 创建时间
     */
    private Date ruleCreateTime;

    /**
     * 更新时间
     */
    private Date ruleUpdateTime;

    /**
     * 当前这条促销已经售出数量
     */
    private Integer soldNum;

    /**
     * 闪购购物袋已经售出的数量
     */
    private Integer shoppingBagSoldNum;
    /**
     * 父级促销Id(如虚码促销规则)
     */
    private Long parentRuleId = 0L;

    /**
     * 是否是虚码主系列商品 1：是 0：否
     */
    private Integer visualSerialType = 0;
    /**
     * 促销规则类型 0：普通 1：清仓
     */
    private Integer ruleType;
    /**
     * 价格促销锁定状态（默认不锁定） 0：不锁定（允许人工手动或程序自动修改） 1：半锁定 （只允许人工手动修改） 2：全锁定 （程序自动和人工手动都不能修改）
     */
    private Integer lockStatus = 0;
    /**
     * 价格促销规则状态（默认生效） 0：不生效 1:生效
     */
    private Integer ruleStatus = 1;
    /**
     * 是否与活动促销互斥  0：不互斥  1：互斥
     */
    private Integer mutexPromotion;
    /**
     * 清仓折扣
     */
    private BigDecimal discount;
    /**
     * 客户端pool名称
     */
    private String clientPoolName;
    /**
     * 客户端版本号
     */
    private String clientVersion;
    /**
     * 客户端IP
     */
    private String clientIP;
    /**
     * 服务端IP
     */
    private String serverIP;
    /**
     * 状态 1:自动下架
     */
    private Integer status;

    /**
     * 表pm_info主键Id
     */
    private Long pmId;
    /**
     * 平均售价
     */
    private BigDecimal avgPrice;

    /**
     * N件起购，最少购买数量
     * 0,null表示无限制
     */
    private Long minCount;

    /**
     * 渠道ID
     */
    private Long channelId;

    /**
     * 已支付数量
     */
    private Integer payNum;

    /**
     * 价格锁定 0：价格不锁定，允许促销时间内卖光跳正价(默认), 1：价格锁定,促销时间内卖光不允许跳正价
     */
    private Integer priceLockType;
    /**
     * 价格变化提醒 0：价格变不需提醒用户（默认）, 1：价格变了需要提醒用户
     */
    private Integer priceChangeRemind;

    /**
     * 外部活动ID
     * 如：团购ID(groupon_id)，landingPage ID(promotion_id)
     */
    private Long activityId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPromoteType() {
        return promoteType;
    }

    public void setPromoteType(Integer promoteType) {
        this.promoteType = promoteType;
    }

    public BigDecimal getPromNonMemberPrice() {
        return promNonMemberPrice;
    }

    public void setPromNonMemberPrice(BigDecimal promNonMemberPrice) {
        this.promNonMemberPrice = promNonMemberPrice;
    }

    public Integer getUserPriceLimitNumber() {
        return userPriceLimitNumber;
    }

    public void setUserPriceLimitNumber(Integer userPriceLimitNumber) {
        this.userPriceLimitNumber = userPriceLimitNumber;
    }

    public Integer getSpecialPriceLimitNumber() {
        return specialPriceLimitNumber;
    }

    public void setSpecialPriceLimitNumber(Integer specialPriceLimitNumber) {
        this.specialPriceLimitNumber = specialPriceLimitNumber;
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

    public String getPromName() {
        return promName;
    }

    public void setPromName(String promName) {
        this.promName = promName;
    }

    public Long getBackendOperatorId() {
        return backendOperatorId;
    }

    public void setBackendOperatorId(Long backendOperatorId) {
        this.backendOperatorId = backendOperatorId;
    }

    public Date getRuleCreateTime() {
        return ruleCreateTime;
    }

    public void setRuleCreateTime(Date ruleCreateTime) {
        this.ruleCreateTime = ruleCreateTime;
    }

    public Date getRuleUpdateTime() {
        return ruleUpdateTime;
    }

    public void setRuleUpdateTime(Date ruleUpdateTime) {
        this.ruleUpdateTime = ruleUpdateTime;
    }

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public Long getParentRuleId() {
        return parentRuleId;
    }

    public void setParentRuleId(Long parentRuleId) {
        this.parentRuleId = parentRuleId;
    }

    public Integer getVisualSerialType() {
        return visualSerialType;
    }

    public void setVisualSerialType(Integer visualSerialType) {
        this.visualSerialType = visualSerialType;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getMutexPromotion() {
        return mutexPromotion;
    }

    public void setMutexPromotion(Integer mutexPromotion) {
        this.mutexPromotion = mutexPromotion;
    }

    public String getClientPoolName() {
        return clientPoolName;
    }

    public void setClientPoolName(String clientPoolName) {
        this.clientPoolName = clientPoolName;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPmId() {
        return pmId;
    }

    public void setPmId(Long pmId) {
        this.pmId = pmId;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Integer getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(Integer ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public Long getMinCount() {
        return minCount;
    }

    public void setMinCount(Long minCount) {
        this.minCount = minCount;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public Integer getPriceLockType() {
        return priceLockType;
    }

    public void setPriceLockType(Integer priceLockType) {
        this.priceLockType = priceLockType;
    }

    public Integer getPriceChangeRemind() {
        return priceChangeRemind;
    }

    public void setPriceChangeRemind(Integer priceChangeRemind) {
        this.priceChangeRemind = priceChangeRemind;
    }

    public void setShoppingBagSoldNum(Integer shoppingBagSoldNum) {
        this.shoppingBagSoldNum = shoppingBagSoldNum;
    }

    public Integer getShoppingBagSoldNum() {
        return shoppingBagSoldNum;
    }

    public void setActivityId (Long activityId)
    {
        this.activityId = activityId;
    }

    public Long getActivityId ()
    {
        return activityId;
    }
    @Override
    public String toString() {
        return "BusyProductPromRuleLogVo{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", opType='" + opType + '\'' +
                ", ruleId=" + ruleId +
                ", productId=" + productId +
                ", merchantId=" + merchantId +
                ", promoteType=" + promoteType +
                ", promNonMemberPrice=" + promNonMemberPrice +
                ", userPriceLimitNumber=" + userPriceLimitNumber +
                ", specialPriceLimitNumber=" + specialPriceLimitNumber +
                ", promStartTime=" + promStartTime +
                ", promEndTime=" + promEndTime +
                ", promName='" + promName + '\'' +
                ", backendOperatorId=" + backendOperatorId +
                ", ruleCreateTime=" + ruleCreateTime +
                ", ruleUpdateTime=" + ruleUpdateTime +
                ", soldNum=" + soldNum +
                ", parentRuleId=" + parentRuleId +
                ", visualSerialType=" + visualSerialType +
                ", ruleType=" + ruleType +
                ", lockStatus=" + lockStatus +
                ", ruleStatus=" + ruleStatus +
                ", mutexPromotion=" + mutexPromotion +
                ", discount=" + discount +
                ", clientPoolName='" + clientPoolName + '\'' +
                ", clientVersion='" + clientVersion + '\'' +
                ", clientIP='" + clientIP + '\'' +
                ", serverIP='" + serverIP + '\'' +
                ", status=" + status +
                ", pmId=" + pmId +
                ", avgPrice=" + avgPrice +
                ", minCount=" + minCount +
                ", channelId=" + channelId +
                ", payNum=" + payNum +
                ", priceLockType=" + priceLockType +
                ", priceChangeRemind=" + priceChangeRemind +
                ", activityId=" + activityId +
                '}';
    }

}
