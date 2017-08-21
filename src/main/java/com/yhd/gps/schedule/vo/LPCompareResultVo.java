package com.yhd.gps.schedule.vo;

/**
 * landingPage比对结果
 * @Author lipengcheng
 * @CreateTime 2016-7-1 上午10:02:40
 */
public class LPCompareResultVo {

    private static final long serialVersionUID = -1;

    /**
     * 促销id
     */
    private Long promotionId;

    /**
     * 商品id
     */
    private Long pmId;

    /**
     * 错误码
     */
    private String errorCode;

    public LPCompareResultVo() {
    }

    public LPCompareResultVo(Long promotionId, Long pmId) {
        this.promotionId = promotionId;
        this.pmId = pmId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getPmId() {
        return pmId;
    }

    public void setPmId(Long pmId) {
        this.pmId = pmId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "LPCompareResultVo [promotionId=" + promotionId + ", pmId=" + pmId + ", errorCode=" + errorCode + "]";
    }

}
