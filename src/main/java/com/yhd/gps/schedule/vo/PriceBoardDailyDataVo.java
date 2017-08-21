package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunfei
 * @CreateTime 2017-6-30 下午01:42:09
 */
public class PriceBoardDailyDataVo implements Serializable {
    private static final long serialVersionUID = -239183185911471012L;

    private Long id;

    private Long pmInfoId;

    private Long productId;

    private Long merchantId;

    /**
     * 统计当天数据的日期(2017-06-30),无时分秒
     */
    private Date statisticsDate;

    /**
     * 记录的生成时间
     */
    private Date createTime;
    /**
     * 最低价
     */
    private BigDecimal minPrice;
    /**
     * 最低价类型(-1基准价 0：普通促销价 2：团购 7：闪购 8：定金预售)
     */
    private Integer minPriceRuleType;
    /**
     * 最低价持续的时间(小时数)
     */
    private BigDecimal minPriceDuration;
    /**
     * 最低价频次
     */
    private Integer minPriceFrequency;
    /**
     * 最高价
     */
    private BigDecimal maxPrice;
    /**
     * 最高价类型(-1基准价 0：普通促销价 2：团购 7：闪购 8：定金预售)
     */
    private Integer maxPriceRuleType;
    /**
     * 最高价持续的时间(小时数)
     */
    private BigDecimal maxPriceDuration;
    /**
     * 最高价频次
     */
    private Integer maxPriceFrequency;
    /**
     * 众数价
     */
    private BigDecimal modePrice;
    /**
     * 众数价类型(-1基准价 0：普通促销价 2：团购 7：闪购 8：定金预售)
     */
    private Integer modePriceRuleType;
    /**
     * 众数价持续的时间(小时数)
     */
    private BigDecimal modePriceDuration;
    /**
     * 众数价频次
     */
    private Integer modePriceFrequency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPmInfoId() {
        return pmInfoId;
    }

    public void setPmInfoId(Long pmInfoId) {
        this.pmInfoId = pmInfoId;
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

    public Date getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMinPriceRuleType() {
        return minPriceRuleType;
    }

    public void setMinPriceRuleType(Integer minPriceRuleType) {
        this.minPriceRuleType = minPriceRuleType;
    }

    public BigDecimal getMinPriceDuration() {
        return minPriceDuration;
    }

    public void setMinPriceDuration(BigDecimal minPriceDuration) {
        this.minPriceDuration = minPriceDuration;
    }

    public Integer getMinPriceFrequency() {
        return minPriceFrequency;
    }

    public void setMinPriceFrequency(Integer minPriceFrequency) {
        this.minPriceFrequency = minPriceFrequency;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMaxPriceRuleType() {
        return maxPriceRuleType;
    }

    public void setMaxPriceRuleType(Integer maxPriceRuleType) {
        this.maxPriceRuleType = maxPriceRuleType;
    }

    public BigDecimal getMaxPriceDuration() {
        return maxPriceDuration;
    }

    public void setMaxPriceDuration(BigDecimal maxPriceDuration) {
        this.maxPriceDuration = maxPriceDuration;
    }

    public Integer getMaxPriceFrequency() {
        return maxPriceFrequency;
    }

    public void setMaxPriceFrequency(Integer maxPriceFrequency) {
        this.maxPriceFrequency = maxPriceFrequency;
    }

    public BigDecimal getModePrice() {
        return modePrice;
    }

    public void setModePrice(BigDecimal modePrice) {
        this.modePrice = modePrice;
    }

    public Integer getModePriceRuleType() {
        return modePriceRuleType;
    }

    public void setModePriceRuleType(Integer modePriceRuleType) {
        this.modePriceRuleType = modePriceRuleType;
    }

    public BigDecimal getModePriceDuration() {
        return modePriceDuration;
    }

    public void setModePriceDuration(BigDecimal modePriceDuration) {
        this.modePriceDuration = modePriceDuration;
    }

    public Integer getModePriceFrequency() {
        return modePriceFrequency;
    }

    public void setModePriceFrequency(Integer modePriceFrequency) {
        this.modePriceFrequency = modePriceFrequency;
    }

    @Override
    public String toString() {
        return "PriceBoardDailyDataVo [id=" + id + ", pmInfoId=" + pmInfoId + ", productId=" + productId
               + ", merchantId=" + merchantId + ", statisticsDate=" + statisticsDate + ", createTime=" + createTime
               + ", minPrice=" + minPrice + ", minPriceRuleType=" + minPriceRuleType + ", minPriceDuration="
               + minPriceDuration + ", minPriceFrequency=" + minPriceFrequency + ", maxPrice=" + maxPrice
               + ", maxPriceRuleType=" + maxPriceRuleType + ", maxPriceDuration=" + maxPriceDuration
               + ", maxPriceFrequency=" + maxPriceFrequency + ", modePrice=" + modePrice + ", modePriceRuleType="
               + modePriceRuleType + ", modePriceDuration=" + modePriceDuration + ", modePriceFrequency="
               + modePriceFrequency + "]";
    }

}
