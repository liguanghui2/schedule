package com.yhd.gps.busy.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.freemaker.TemplateFactory;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.MailTempletVo;
import com.yihaodian.busy.common.GPSUtils;

/**
 * 用邮件模板发送邮件工具类
 * @author:liguanghui1
 * @date ：2016-8-29 下午02:56:20
 */
public class BusyMailSendWithTempletUtil {

    private static final Log log = LogFactory.getLog(BusyMailSendWithTempletUtil.class);

    public static void sendEmailWithTemplet(MailTempletVo mailTemplet) {
        String title = mailTemplet.getTitle();
        String templateName = mailTemplet.getTemplateName();
        Map<String, Object> map = mailTemplet.getMap();
        String html = null;
        try {
            html = TemplateFactory.generateHtmlFromFtl(templateName, map);
        } catch (Exception e) {
            String errorContent = "取freemaker模板异常!templateName=" + templateName == null ? "" : templateName
                                                                                               + e.getMessage();
            log.error(errorContent, e);
            BusyMailUtil.sendMail(title, errorContent);
        }
        BusyMailUtil.sendHtmlMail(title, html);
    }

    /**
     * 批量发邮件
     * @param <T>
     * @param mailTemplet
     * @param threshold 发邮件数据条数阈值
     * @param dataList
     */
    public static <T> void sendEmailWithTemplet(MailTempletVo mailTemplet, int threshold, List<T> dataList) {
        if(null == mailTemplet || CollectionUtils.isEmpty(dataList)) {
            return;
        }
        String templateName = mailTemplet.getTemplateName();
        // 无模板则不发送邮件
        if(StringUtils.isEmpty(templateName)) {
            return;
        }
        // 设置阈值默认值
        if(threshold <= 0) {
            threshold = ScheduleConstants.SEND_MAIL_THRESHOLD;
        }
        // 分批发送邮件
        List<List<T>> lists = GPSUtils.split(dataList, threshold);
        for(List<T> resultList : lists) {
            MailTempletVo subMailTemplet = new MailTempletVo();
            subMailTemplet.setTitle(mailTemplet.getTitle());
            subMailTemplet.setTemplateName(templateName);
            subMailTemplet.setContent(mailTemplet.getContent());
            Map<String, Object> map = new HashMap<String, Object>(1, 1F);
            map.put("resultList", resultList);
            subMailTemplet.setMap(map);
            // 发送邮件
            sendEmailWithTemplet(subMailTemplet);
        }
    }
}
