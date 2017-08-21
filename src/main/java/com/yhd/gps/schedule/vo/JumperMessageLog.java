package com.yhd.gps.schedule.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2015-6-12 下午03:12:17
 */
public class JumperMessageLog implements Serializable {

    private static final long serialVersionUID = -5386256386331382778L;

    private Long pmInfoId;
    private String serverIp;
    private String topic;
    private String messageType;
    private String content;
    private Date sendTime;
    private Date createTime;

    public JumperMessageLog() {
        super();
    }

    public JumperMessageLog(Long pmInfoId, String serverIp, String topic, String messageType, String content,
                            Date sendTime) {
        this();
        this.pmInfoId = pmInfoId;
        this.serverIp = serverIp;
        this.topic = topic;
        this.messageType = messageType;
        this.content = content;
        this.sendTime = sendTime;
    }

    public Long getPmInfoId() {
        return pmInfoId;
    }

    public void setPmInfoId(Long pmInfoId) {
        this.pmInfoId = pmInfoId;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return "JumperMessageLog [pmInfoId=" + pmInfoId + ", serverIp=" + serverIp + ", topic=" + topic
               + ", messageType=" + messageType + ", content=" + content + ", sendTime="
               + (null == sendTime ? null : sdf.format(sendTime)) + ", createTime="
               + (null == createTime ? null : sdf.format(createTime)) + "]";
    }

}
