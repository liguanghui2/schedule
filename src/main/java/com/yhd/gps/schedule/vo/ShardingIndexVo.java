package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据分片表
 * @Author liuxiangrong
 * @CreateTime 2016-1-29 下午02:04:13
 */
public class ShardingIndexVo implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * id
     */
    private Long id;

    /**
     * 分片表名
     */
    private String tableName;

    /**
     * 分片索引
     */
    private int shardingIndex;

    /**
     * 分片是否有效（是否被处理状态）：0为有效，可以获取；1为无效，不能获取
     */
    private int isValid;

    /**
     * 处理分片的IP地址
     */
    private String ip;

    /**
     * 分片处理的更新时间（如果isValid为1，则为获取时间，为0则为最后被处理的时间）
     */
    private Date updateTime;

    public ShardingIndexVo() {
    }

    public ShardingIndexVo(String tableName, int shardingIndex, int isValid, String ip) {
        this.tableName = tableName;
        this.shardingIndex = shardingIndex;
        this.isValid = isValid;
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getShardingIndex() {
        return shardingIndex;
    }

    public void setShardingIndex(int shardingIndex) {
        this.shardingIndex = shardingIndex;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int status) {
        this.isValid = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ShardingIndexVo [id=" + id + ", tableName=" + tableName + ", shardingIndex=" + shardingIndex
               + ", isValid=" + isValid + ", ip=" + ip + ", updateTime=" + updateTime + "]";
    }

}
