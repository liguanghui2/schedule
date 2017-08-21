package com.yhd.gps.busy.mail;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public interface BusyMailService {
    public void sendMail(String subject, String content, boolean force);

    public void sendHtmlMail(String[] toUserMail, String title, String content, boolean enableCc);

    public void sendHtmlMail(String title, String content);

    public boolean isMailEnabled();

    public void switchMailDisabled();

    public void switchMailEnabled();

    public Long getProcessTimeout();

    public void setProcessTimeout(Long processTimeout);

    public String[] getAdminMailNameList();

}
