package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.util.Date;

public class GoldCoinPriceChangeMsg implements Serializable {

    private static final long serialVersionUID = -2324018448465279857L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private Long pmInfoId;

    /**
     * gold_coin_prom_rule表主键Id
     */
    private Long ruleId;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息发送状态
     */
    private Integer msgStatus;

    /**
     * 消息任务预计发送机器IP
     */
    private String msgTaskIp;

    /**
     * 消息任务预计发送时间
     */

    private Date msgTaskTime;

    /**
     * 消息真实发送机器IP
     */
    private String actualSendIp;

    /**
     * 消息真实发送时间
     */
    private Date actualSendTime;

    /**
     * 记录创建机器IP
     */
    private String createIp;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 记录更新机器IP
     */
    private String updatecreateIp;

    /**
     * 记录更新时间
     */
    private Date updateTime;

    /**
     * 切片的ID
     */
    private int shardingIndex;

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

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Integer getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(Integer msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getMsgTaskIp() {
        return msgTaskIp;
    }

    public void setMsgTaskIp(String msgTaskIp) {
        this.msgTaskIp = msgTaskIp;
    }

    public Date getMsgTaskTime() {
        return msgTaskTime;
    }

    public void setMsgTaskTime(Date msgTaskTime) {
        this.msgTaskTime = msgTaskTime;
    }

    public String getActualSendIp() {
        return actualSendIp;
    }

    public void setActualSendIp(String actualSendIp) {
        this.actualSendIp = actualSendIp;
    }

    public Date getActualSendTime() {
        return actualSendTime;
    }

    public void setActualSendTime(Date actualSendTime) {
        this.actualSendTime = actualSendTime;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatecreateIp() {
        return updatecreateIp;
    }

    public void setUpdatecreateIp(String updatecreateIp) {
        this.updatecreateIp = updatecreateIp;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getShardingIndex() {
        return shardingIndex;
    }

    public void setShardingIndex(int shardingIndex) {
        this.shardingIndex = shardingIndex;
    }

    @Override
    public String toString() {
        return "GoldCoinPriceChangeMsg [id=" + id + ", pmInfoId=" + pmInfoId + ", ruleId=" + ruleId + ", msgContent="
               + msgContent + ", msgStatus=" + msgStatus + ", msgTaskIp=" + msgTaskIp + ", msgTaskTime=" + msgTaskTime
               + ", actualSendIp=" + actualSendIp + ", actualSendTime=" + actualSendTime + ", createIp=" + createIp
               + ", createTime=" + createTime + ", updatecreateIp=" + updatecreateIp + ", updateTime=" + updateTime
               + ", shardingIndex=" + shardingIndex + "]";
    }
}
