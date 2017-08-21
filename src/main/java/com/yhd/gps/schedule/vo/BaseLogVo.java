package com.yhd.gps.schedule.vo;

import java.util.Date;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BaseLogVo {
    /**
     * 主键Id
     */
    private Long id;
    /**
     * 记录创建时间
     */
    private Date createTime;
    /**
     * 操作类型 -1:删除 0：新增 1：更新
     */
    private String opType;
    /**
     * 客户端pool名称
     */
    private String clientPoolName;
    /**
     * 客户端版本号
     */
    private String clientVersion;
    /**
     * 客户端IP
     */
    private String clientIP;
    /**
     * 服务端IP
     */
    private String serverIP;

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

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getClientPoolName() {
        return clientPoolName;
    }

    public void setClientPoolName(String clientPoolName) {
        this.clientPoolName = clientPoolName;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
}
