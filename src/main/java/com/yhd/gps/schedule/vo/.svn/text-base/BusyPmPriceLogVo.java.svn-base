package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lipengfei
 *
 */
public class BusyPmPriceLogVo extends BaseLogVo implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * Id
     */
    private Long id;
    /**
     * 记录创建时间
     */
    private Date createTime;
    /**
     * 操作类型 -1:删除 0：新增 1：更新
     */
    private String opType;
    /**
     * 价格记录主键Id
     */
    private Long priceId;
    /**
     * 产品Id
     */
    private Long productId;
    /**
     * 商家Id
     */
    private Long merchantId;
    /**
     * 市场价
     */
    private BigDecimal marketPrice = BigDecimal.ZERO;
    /**
     * 定金
     */
    private BigDecimal earnest = BigDecimal.ZERO;
    /**
     * 是否是vip产品
     */
    private boolean canVipDiscount;
    /**
     * 促销类型
     */
    private Integer promoteType = 0;
    /**
     * 一号店价
     */
    private BigDecimal nonMemberPrice = BigDecimal.ZERO;
    /**
     * 促销一号店价
     */
    private BigDecimal promNonMemberPrice = BigDecimal.ZERO;

    /**
     * 进价(冗余默认供应商产品的进价)
     */
    private BigDecimal inPrice = BigDecimal.ZERO;

    /**
     * 供应商建议最低零售价
     */
    private BigDecimal minimumSellingPrice;

    /**
     * 促销开始时间
     */
    private Date promStartTime;
    /**
     * 促销结束时间
     */
    private Date promEndTime;
    /**
     * 每人特价限制数量
     */
    private int userPriceLimitNumber;
    /**
     * 特价限制总数量
     */
    private int specialPriceLimitNumber;

    /**
     * 价格更新时间
     */
    private Date priceUpdateTime;
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
     * 共享成本
     */
    private BigDecimal shareCost;

    /**
     * 该商品拥有扩展价格类型 1：有自定义会员级别定价 2：有自定义区域特殊定价 3：同时拥有级别与区域定价
     */
    private Integer hasExtPriceType;

    /**
     * 商品税率
     */
    private BigDecimal postTaxRate;

    /**
     * 商品消费税率
     */
    private BigDecimal exciseTax;

    /**
     * 商品增值税率
     */
    private BigDecimal vaTax;

    /**
     * 是否整包
     */
    private Integer isBindPackage;

    /**
     * 每包数量
     */
    private Integer perPackNum;

    /**
     * 子商品单位
     */
    private String subPmInfoUnit;

    /**
     * 是否锁价
     */
    private Integer isLockPrice;

    /**
     * 成本价
     */
    private Double costPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
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

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getEarnest() {
        return earnest;
    }

    public void setEarnest(BigDecimal earnest) {
        this.earnest = earnest;
    }

    public boolean isCanVipDiscount() {
        return canVipDiscount;
    }

    public void setCanVipDiscount(boolean canVipDiscount) {
        this.canVipDiscount = canVipDiscount;
    }

    public Integer getPromoteType() {
        return promoteType;
    }

    public void setPromoteType(Integer promoteType) {
        this.promoteType = promoteType;
    }

    public BigDecimal getNonMemberPrice() {
        return nonMemberPrice;
    }

    public void setNonMemberPrice(BigDecimal nonMemberPrice) {
        this.nonMemberPrice = nonMemberPrice;
    }

    public BigDecimal getPromNonMemberPrice() {
        return promNonMemberPrice;
    }

    public void setPromNonMemberPrice(BigDecimal promNonMemberPrice) {
        this.promNonMemberPrice = promNonMemberPrice;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
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

    public int getUserPriceLimitNumber() {
        return userPriceLimitNumber;
    }

    public void setUserPriceLimitNumber(int userPriceLimitNumber) {
        this.userPriceLimitNumber = userPriceLimitNumber;
    }

    public int getSpecialPriceLimitNumber() {
        return specialPriceLimitNumber;
    }

    public void setSpecialPriceLimitNumber(int specialPriceLimitNumber) {
        this.specialPriceLimitNumber = specialPriceLimitNumber;
    }

    public Date getPriceUpdateTime() {
        return priceUpdateTime;
    }

    public void setPriceUpdateTime(Date priceUpdateTime) {
        this.priceUpdateTime = priceUpdateTime;
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

    public BigDecimal getShareCost() {
        return shareCost;
    }

    public void setShareCost(BigDecimal shareCost) {
        this.shareCost = shareCost;
    }

    public BigDecimal getMinimumSellingPrice() {
        return minimumSellingPrice;
    }

    public void setMinimumSellingPrice(BigDecimal minimumSellingPrice) {
        this.minimumSellingPrice = minimumSellingPrice;
    }

    public void setHasExtPriceType(Integer hasExtPriceType) {
        this.hasExtPriceType = hasExtPriceType;
    }

    public Integer getHasExtPriceType() {
        return hasExtPriceType;
    }

    public void setPostTaxRate(BigDecimal postTaxRate) {
        this.postTaxRate = postTaxRate;
    }

    public BigDecimal getPostTaxRate() {
        return postTaxRate;
    }

    public Integer getIsBindPackage() {
        return isBindPackage;
    }

    public void setIsBindPackage(Integer isBindPackage) {
        this.isBindPackage = isBindPackage;
    }

    public Integer getPerPackNum() {
        return perPackNum;
    }

    public void setPerPackNum(Integer perPackNum) {
        this.perPackNum = perPackNum;
    }

    public String getSubPmInfoUnit() {
        return subPmInfoUnit;
    }

    public void setSubPmInfoUnit(String subPmInfoUnit) {
        this.subPmInfoUnit = subPmInfoUnit;
    }

    public Integer getIsLockPrice() {
        return isLockPrice;
    }

    public void setIsLockPrice(Integer isLockPrice) {
        this.isLockPrice = isLockPrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getExciseTax() {
        return exciseTax;
    }

    public void setExciseTax(BigDecimal exciseTax) {
        this.exciseTax = exciseTax;
    }

    public BigDecimal getVaTax() {
        return vaTax;
    }

    public void setVaTax(BigDecimal vaTax) {
        this.vaTax = vaTax;
    }

    @Override
    public String toString() {
        return "BusyPmPriceLogVo [id=" + id + ", createTime=" + createTime + ", opType=" + opType + ", priceId="
               + priceId + ", productId=" + productId + ", merchantId=" + merchantId + ", marketPrice=" + marketPrice
               + ", earnest=" + earnest + ", canVipDiscount=" + canVipDiscount + ", promoteType=" + promoteType
               + ", nonMemberPrice=" + nonMemberPrice + ", promNonMemberPrice=" + promNonMemberPrice + ", inPrice="
               + inPrice + ", minimumSellingPrice=" + minimumSellingPrice + ", promStartTime=" + promStartTime
               + ", promEndTime=" + promEndTime + ", userPriceLimitNumber=" + userPriceLimitNumber
               + ", specialPriceLimitNumber=" + specialPriceLimitNumber + ", priceUpdateTime=" + priceUpdateTime
               + ", clientPoolName=" + clientPoolName + ", clientVersion=" + clientVersion + ", clientIP=" + clientIP
               + ", serverIP=" + serverIP + ", status=" + status + ", pmId=" + pmId + ", avgPrice=" + avgPrice
               + ", shareCost=" + shareCost + ", hasExtPriceType=" + hasExtPriceType + ", postTaxRate=" + postTaxRate
               + ", exciseTax=" + exciseTax + ", vaTax=" + vaTax + ", isBindPackage=" + isBindPackage + ", perPackNum="
               + perPackNum + ", subPmInfoUnit=" + subPmInfoUnit + ", isLockPrice=" + isLockPrice + ", costPrice="
               + costPrice + "]";
    }
}
