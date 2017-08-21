package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ---- 请加注释 ------
 * @Author zuozhen
 * @CreateTime 2015-6-29 下午04:46:03
 */
public class DistintcPriceChangCache implements Serializable {

    private static final long serialVersionUID = 557344060570254439L;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 历史价格增加字段 当前活动id（GPS内部ID）
     */
    private Long promRuleId;

    public DistintcPriceChangCache() {
        super();
    }

    public DistintcPriceChangCache(BigDecimal price, Long promRuleId) {
        this();
        this.price = price;
        this.promRuleId = promRuleId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getPromRuleId() {
        return promRuleId;
    }

    public void setPromRuleId(Long promRuleId) {
        this.promRuleId = promRuleId;
    }

}
