package com.yhd.gps.busy.mail;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.SpringBeanUtil;
import com.yihaodian.busy.common.GPSUtils;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyMailUtil {
    private static BusyMailService busyMailService;

    static {
        busyMailService = (BusyMailService) SpringBeanUtil.getBean("busyMailService");
    }

    public static boolean isMailEnabled() {
        return busyMailService.isMailEnabled();
    }

    public static void switchMailDisabled() {
        busyMailService.switchMailDisabled();
    }

    public static void switchMailEnabled() {
        busyMailService.switchMailEnabled();
    }

    public static void sendMail(Throwable t) {
        String message = ExceptionUtils.getFullStackTrace(t);
        busyMailService.sendMail(null, message, false);
    }

    public static void sendMail(String title, Throwable t) {
        String message = ExceptionUtils.getFullStackTrace(t);
        busyMailService.sendMail(title, message, false);
    }

    public static void sendMail(String title, String content) {
        busyMailService.sendMail(title, content, false);
    }

    public static void sendAdminMail(String title, Throwable t) {
        String[] adminMailNames = busyMailService.getAdminMailNameList();
        if(adminMailNames != null && adminMailNames.length > 0) {
            String message = ExceptionUtils.getFullStackTrace(t);
            busyMailService.sendHtmlMail(adminMailNames, title, message, false);
        }
    }

    public static void sendAdminMail(String title, String content) {
        String[] adminMailNames = busyMailService.getAdminMailNameList();
        if(adminMailNames != null && adminMailNames.length > 0) {
            busyMailService.sendHtmlMail(adminMailNames, title, content, false);
        }
    }

    /**
     * 发送邮件
     * 
     * @param title 标题
     * @param content 内容
     * @param force 是否强制发送true是,false 否
     */
    public static void sendMail(String title, String content, boolean force) {
        busyMailService.sendMail(title, content, force);
    }

    public static void sendHtmlMail(String[] toUserMail, String title, String content) {
        busyMailService.sendHtmlMail(toUserMail, title, content, true);
    }

    public static void sendHtmlMail(String[] toUserMail, String title, String content, boolean enableCc) {
        busyMailService.sendHtmlMail(toUserMail, title, content, enableCc);
    }

    public static void sendHtmlMail(String title, String content) {
        busyMailService.sendHtmlMail(title, content);
    }

    public static Long getProcessTimeout() {
        return busyMailService.getProcessTimeout();
    }

    /**
     * 批量发邮件
     */
    public static <T> void sendHtmlMail(String title, String content, int threshold, List<T> dataList,
                                        String[] toUserMail) {
        // 设置阈值默认值
        if(threshold <= 0) {
            threshold = ScheduleConstants.SEND_MAIL_THRESHOLD;
        }
        if(null == content) {
            content = "";
        }

        if(CollectionUtils.isNotEmpty(dataList)) {
            // 按阈值分批发送邮件
            List<List<T>> datas = GPSUtils.split(dataList, threshold);
            for(List<T> data : datas) {
                String realContent = content + "<br/>" + data;
                busyMailService.sendHtmlMail(toUserMail, title, realContent, false);
            }
        }
    }
}
