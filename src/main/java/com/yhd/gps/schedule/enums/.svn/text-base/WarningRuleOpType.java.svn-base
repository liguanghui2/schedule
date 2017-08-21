package com.yhd.gps.schedule.enums;

/**
 * ---- 请加注释 ------
 * @Author lipengcheng
 * @CreateTime 2017-3-8 下午06:49:10
 */
public enum WarningRuleOpType {

    /**
     * 报警规则操作类型 1:大于，2:等于，3:小于，4:大于等于，5:小于等于，6:不等于。用于与阈值比较大小
     */
    WARNING_RULE_OP_TYPE_GREATER_THAN(1), 
    WARNING_RULE_OP_TYPE_EQUAL(2), 
    WARNING_RULE_OP_TYPE_LESS_THAN(3), 
    WARNING_RULE_OP_TYPE_GREATER_THAN_OR_EQUAL(4), 
    WARNING_RULE_OP_TYPE_LESS_THAN_OR_EQUAL(5), 
    WARNING_RULE_OP_TYPE_NOT_EQUAL(6);
    
    private int value;

    private WarningRuleOpType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WarningRuleOpType getByValue(int value) {
        for(WarningRuleOpType warningRuleOpType : WarningRuleOpType.values()) {
            if(warningRuleOpType.getValue() == value) {
                return warningRuleOpType;
            }
        }
        return null;
    }
}
