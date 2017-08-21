package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ---- 请加注释 ------
 * @Author lipengcheng
 * @CreateTime 2017-3-3 下午02:48:16
 */
public class GpsScheduleWarningRuleVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 规则用户名
     */
    private String schema1;
    /**
     * 规则sql
     */
    private String sql1;
    /**
     * 报警用户名
     */
    private String schema2;
    /**
     * 报警sql
     */
    private String sql2;
    /**
     * 阈值
     */
    private int threshold;
    /**
     * 规则类型：1：执行sql
     */
    private int ruleType;
    /**
     * 规则是否有效
     */
    private boolean isValid;
    /**
     * 切片
     */
    private int shardingIndex;
    /**
     * 报警收件人
     */
    private String email;
    /**
     * 报警内容
     */
    private String warningContent;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 操作类型： 1:大于，2:等于，3:小于，4:大于等于，5:小于等于，6:不等于。用于与阈值比较大小
     */
    private int opType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchema1() {
        return schema1;
    }

    public void setSchema1(String schema1) {
        this.schema1 = schema1;
    }

    public String getSql1() {
        return sql1;
    }

    public void setSql1(String sql1) {
        this.sql1 = sql1;
    }

    public String getSchema2() {
        return schema2;
    }

    public void setSchema2(String schema2) {
        this.schema2 = schema2;
    }

    public String getSql2() {
        return sql2;
    }

    public void setSql2(String sql2) {
        this.sql2 = sql2;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public int getShardingIndex() {
        return shardingIndex;
    }

    public void setShardingIndex(int shardingIndex) {
        this.shardingIndex = shardingIndex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWarningContent() {
        return warningContent;
    }

    public void setWarningContent(String warningContent) {
        this.warningContent = warningContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    @Override
    public String toString() {
        return "GpsScheduleWarningRuleVo [id=" + id + ", schema1=" + schema1 + ", sql1=" + sql1 + ", schema2="
               + schema2 + ", sql2=" + sql2 + ", threshold=" + threshold + ", ruleType=" + ruleType + ", isValid="
               + isValid + ", shardingIndex=" + shardingIndex + ", email=" + email + ", warningContent="
               + warningContent + ", remark=" + remark + ", createTime=" + createTime + ", updateTime=" + updateTime
               + ", opType=" + opType + "]";
    }

}
