package com.yhd.gps.schedule.vo;

import java.io.Serializable;

/**
 * @author sunfei
 * @CreateTime 2016-12-2 上午10:19:13
 */
public class JDBookMessageVo implements Serializable {

    private static final long serialVersionUID = 7554830013112079751L;

    /**
     * 表pm_info主键Id
     */
    private Long pmId;
    /**
     * 产品Id
     */
    private Long productId;
    /**
     * 外部产品id
     */
    private String outerId;

    public Long getPmId() {
        return pmId;
    }

    public void setPmId(Long pmId) {
        this.pmId = pmId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    @Override
    public String toString() {
        return "JDBookMessageVo [pmId=" + pmId + ", productId=" + productId + ", outerId=" + outerId + "]";
    }
    
}
