package com.yhd.gps.schedule.vo;

import java.util.Date;

/**
 * 数据同步错误日志
 * @author wangxiaowu
 * 
 */
public class SyncDataLogVo {
    /**
     * 表主键ID
     */
    private Long id;

    /**
     * 源表主键ID
     */
    private Long keyId;

    /**
     * 记录创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public Long getKeyId() {
        return keyId;
    }

}
