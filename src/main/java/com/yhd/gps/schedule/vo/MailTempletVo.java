package com.yhd.gps.schedule.vo;

import java.util.Map;

/**
 * 邮件模板参数VO
 * @author:liguanghui1
 * @date ：2016-8-30 下午05:44:59 
 */
public class MailTempletVo {
    /**
     * 邮件标题
     */
    private String title;
    /**
     * freemaker模板名称
     */
    private String templateName;
    /**
     * 提示信息
     */
    private String content;
    
    private Map<String, Object> map;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "MailTempletVo [title=" + title + ", templateName=" + templateName + ", content=" + content + ", map="
               + map + "]";
    }
}
