package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyPmInfoVo implements Serializable {

    private static final long serialVersionUID = -8727509704875631807L;

    /**
     * 表pm_info主键Id
     */
    private Long pmId;
    /**
     * 产品Id
     */
    private Long productId;
    /**
     * 商家Id
     */
    private Long merchantId;
    /**
     * 销售类型
     */
    private Integer saleType;
    /**
     * 是否可销
     */
    private Integer canSale;
    /**
     * 是否可见
     */
    private Integer canShow;
    /**
     * 当前售价
     */
    private BigDecimal currentPrice;
    /**
     * 当前库存
     */
    private Long currentStockNum;

    /**
     * 是否是指定默认子品(默认值不是默认子品)
     */
    private Integer isDefault = 0;

    /**
     * 整个集团商家是否共享库存,0:否，1：是
     */
    private Integer isStockSupportShare = 0;

    /**
     * 集团商品ID
     */
    private Long pgmId;

    /**
     * 商品更新时间
     */
    private Date updateTime;

    /**
     * 商品类型 0或null：普通商品 1：主系列商品 2：子系列商品 3：组合商品 4：实物礼品卡 5: 虚拟商品 6:增值服务 7:电子礼品卡
     */
    private Integer pmInfoType;

    /**
     * 销售站点：1：YHD 3:SAM
     */
    private Long siteId;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public Integer getCanSale() {
        return canSale;
    }

    public void setCanSale(Integer canSale) {
        this.canSale = canSale;
    }

    public Integer getCanShow() {
        return canShow;
    }

    public void setCanShow(Integer canShow) {
        this.canShow = canShow;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getCurrentStockNum() {
        return currentStockNum;
    }

    public void setCurrentStockNum(Long currentStockNum) {
        this.currentStockNum = currentStockNum;
    }

    public Long getPmId() {
        return pmId;
    }

    public void setPmId(Long pmId) {
        this.pmId = pmId;
    }

    public Integer getIsDefault() {
        if(isDefault == null) {
            this.isDefault = 0;
        }
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public void setIsStockSupportShare(Integer isStockSupportShare) {
        this.isStockSupportShare = isStockSupportShare;
    }

    public Integer getIsStockSupportShare() {
        return isStockSupportShare;
    }

    public void setPgmId(Long pgmId) {
        this.pgmId = pgmId;
    }

    public Long getPgmId() {
        return pgmId;
    }

    public void setPmInfoType(Integer pmInfoType) {
        this.pmInfoType = pmInfoType;
    }

    public Integer getPmInfoType() {
        return pmInfoType;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return "BusyPmInfoVo [pmId=" + pmId + ", productId=" + productId + ", merchantId=" + merchantId + ", saleType="
               + saleType + ", canSale=" + canSale + ", canShow=" + canShow + ", currentPrice=" + currentPrice
               + ", currentStockNum=" + currentStockNum + ", isDefault=" + isDefault + ", isStockSupportShare="
               + isStockSupportShare + ", pgmId=" + pgmId + ", updateTime=" + updateTime + ", pmInfoType=" + pmInfoType
               + ", siteId=" + siteId + "]";
    }

}
