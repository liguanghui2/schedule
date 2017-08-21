package com.yhd.gps.schedule.vo;

import java.util.Date;

/**
 * 数据处理扫描Vo
 * @Author lipengcheng
 * @CreateTime 2016-7-11 下午02:22:53
 */
public class DataProcessScannerVo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 业务类型 1:价格促销重置soldNum; 2:lp限每日普通促销重置soldNum; 3:lp分时段促销更新活动时间为当天
     */
    private Integer businessType;

    /**
     * 待处理数据主键ID
     */
    private Long refId;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 是否已处理 0未处理 1已处理
     */
    private int isDeal;

    /**
     * 下次处理时间
     */
    private Date nextProcessTime;

    /**
     * 切片
     */
    private int shardingIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(int isDeal) {
        this.isDeal = isDeal;
    }

    public Date getNextProcessTime() {
        return nextProcessTime;
    }

    public void setNextProcessTime(Date nextProcessTime) {
        this.nextProcessTime = nextProcessTime;
    }

    public int getShardingIndex() {
        return shardingIndex;
    }

    public void setShardingIndex(int shardingIndex) {
        this.shardingIndex = shardingIndex;
    }

    @Override
    public String toString() {
        return "DataProcessScannerVo [id=" + id + ", businessType=" + businessType + ", refId=" + refId
               + ", startTime=" + startTime + ", endTime=" + endTime + ", isDeal=" + isDeal + ", nextProcessTime="
               + nextProcessTime + ", shardingIndex=" + shardingIndex + "]";
    }

}
