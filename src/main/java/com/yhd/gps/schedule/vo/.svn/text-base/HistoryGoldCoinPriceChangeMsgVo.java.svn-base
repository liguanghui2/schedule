package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * -- 金币促销商品历史价信息Vo --
 * 
 * @author sunfei
 * @CreateTime 2017-6-15 下午05:09:38
 */
public class HistoryGoldCoinPriceChangeMsgVo implements Serializable {
    private static final long serialVersionUID = 3178894832145749525L;

    private Long id;

    private Long pmInfoId;

    private Long productId;

    private Long merchantId;

    /**
     * 渠道ID：1-1号店（默认，金币价不区分无线和PC）
     */
    private Long channelId;

    /**
     * gold_coin_prom_rule表主键ID
     */
    private Long ruleId;

    /**
     * 限购类型 1-无数量限制促销；2-限制每个订单购买数量；3-限制每个会员购买数量（暂不适用）；4-限制商品总共购买数量（限总量+限用户）
     */
    private Integer isPromotion;

    /**
     * 金币价（总价）：现金+金币折算现金部分，例如90+100金币，即金币价为91
     */
    private BigDecimal promTotalPrice;

    /**
     * 金币现金价：现金+金币折算现金部分，即90元
     */
    private BigDecimal promCashPrice;

    /**
     * 金币金额：金币部分折算的人民币金额，即1元
     */
    private BigDecimal goldCoinPrice;

    /**
     * 金币数：现金+金币的金币部分，即100个
     */
    private Integer goldCoinNum;

    /**
     * 金币促销开始时间
     */
    private Date promStartDate;

    /**
     * 金币促销结束时间
     */
    private Date promEndDate;

    /**
     * 分片ID
     */
    private int shardingIndex;

    /**
     * 0未处理 1已处理
     */
    private int isDeal;
    /**
     * 操作人ID
     */
    private Long backOperatorId;

    /**
     * 消息创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date promUpdateTime;

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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(Integer isPromotion) {
        this.isPromotion = isPromotion;
    }

    public BigDecimal getPromTotalPrice() {
        return promTotalPrice;
    }

    public void setPromTotalPrice(BigDecimal promTotalPrice) {
        this.promTotalPrice = promTotalPrice;
    }

    public BigDecimal getPromCashPrice() {
        return promCashPrice;
    }

    public void setPromCashPrice(BigDecimal promCashPrice) {
        this.promCashPrice = promCashPrice;
    }

    public BigDecimal getGoldCoinPrice() {
        return goldCoinPrice;
    }

    public void setGoldCoinPrice(BigDecimal goldCoinPrice) {
        this.goldCoinPrice = goldCoinPrice;
    }

    public Integer getGoldCoinNum() {
        return goldCoinNum;
    }

    public void setGoldCoinNum(Integer goldCoinNum) {
        this.goldCoinNum = goldCoinNum;
    }

    public Date getPromStartDate() {
        return promStartDate;
    }

    public void setPromStartDate(Date promStartDate) {
        this.promStartDate = promStartDate;
    }

    public Date getPromEndDate() {
        return promEndDate;
    }

    public void setPromEndDate(Date promEndDate) {
        this.promEndDate = promEndDate;
    }

    public int getShardingIndex() {
        return shardingIndex;
    }

    public void setShardingIndex(int shardingIndex) {
        this.shardingIndex = shardingIndex;
    }

    public int getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(int isDeal) {
        this.isDeal = isDeal;
    }

    public Long getBackOperatorId() {
        return backOperatorId;
    }

    public void setBackOperatorId(Long backOperatorId) {
        this.backOperatorId = backOperatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPromUpdateTime() {
        return promUpdateTime;
    }

    public void setPromUpdateTime(Date promUpdateTime) {
        this.promUpdateTime = promUpdateTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "HistoryGoldCoinPriceChangeMsgVo [id=" + id + ",pmInfoId=" + pmInfoId + ", productId=" + productId
               + ", merchantId=" + merchantId + ", channelId=" + channelId + ", ruleId=" + ruleId + ", isPromotion="
               + isPromotion + ", promTotalPrice=" + promTotalPrice + ", promCashPrice=" + promCashPrice
               + ", goldCoinPrice=" + goldCoinPrice + ", goldCoinNum=" + goldCoinNum + ", promStartDate="
               + (null == promStartDate ? null : sdf.format(promStartDate)) + ", promEndDate="
               + (null == promEndDate ? null : sdf.format(promEndDate)) + ", shardingIndex=" + shardingIndex
               + ", isDeal=" + (isDeal == 1 ? "processed" : "no process") + ", backOperatorId=" + backOperatorId
               + ", createTime=" + (null == createTime ? null : sdf.format(createTime)) + ", promUpdateTime="
               + (null == promUpdateTime ? null : sdf.format(promUpdateTime)) + "]";
    }

}
