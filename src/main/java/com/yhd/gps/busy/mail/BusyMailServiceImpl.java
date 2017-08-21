package com.yhd.gps.busy.mail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yihaodian.architecture.hedwig.common.util.HedwigContextUtil;
import com.yihaodian.front.busystock.client.BusyPoolUtil;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyMailServiceImpl implements BusyMailService, InitializingBean {
    private static final Log log = LogFactory.getLog(BusyMailServiceImpl.class);
    private MailSender mailSender;
    private SimpleMailMessage mailMessage;
    private BusyMailFlowControl busyMailFlowControl;// 发送邮件的流量控制
    private ThreadPoolTaskExecutor busyMailSendPool;// 发送邮件线程池
    private String whiteIPList;// 白名单,ip在其中的默认邮件功能开启
    private volatile boolean mailEnabled;// 是否开启邮件功能
    private Long processTimeout;// 接口处理超时时间,超过这个时间就邮件报警
    private Long autoEnabledDelayTime;// 邮件关闭后自动开启时间延迟时间 单位分钟
    private final ReentrantLock lock = new ReentrantLock();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private BusyMiscConfigDao busyMiscConfigDao;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setMailMessage(SimpleMailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    public void setBusyMailFlowControl(BusyMailFlowControl busyMailFlowControl) {
        this.busyMailFlowControl = busyMailFlowControl;
    }

    public void setBusyMailSendPool(ThreadPoolTaskExecutor busyMailSendPool) {
        this.busyMailSendPool = busyMailSendPool;
    }

    public void setBusyMiscConfigDao(BusyMiscConfigDao busyMiscConfigDao) {
        this.busyMiscConfigDao = busyMiscConfigDao;
    }

    @Override
    public boolean isMailEnabled() {
        return this.mailEnabled;
    }

    @Override
    public void switchMailDisabled() {
        this.mailEnabled = false;
    }

    @Override
    public void switchMailEnabled() {
        this.mailEnabled = true;
        // 重置并清除流量控制
        busyMailFlowControl.resetFlowControl();
    }

    @Override
    public void sendMail(final String title, final String content, final boolean force) {
        try {
            if(this.mailEnabled) {
                String requestId = (String) HedwigContextUtil.getRequestId();
                final String buf = "requestId is " + requestId + "\n" + content;
                Runnable task = new Runnable() {
                    public void run() {
                        doSendMailTask(title, buf, force);
                    }
                };
                busyMailSendPool.execute(task);
            }
        } catch (Exception e) {
            log.error(" ", e);
        }
    }

    @Override
    public void sendHtmlMail(final String[] toUserMail, final String title, final String content, final boolean enableCc) {
        if(StringUtils.isNotEmpty(content)) {
            ((JavaMailSenderImpl) mailSender).send(new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    message.setFrom(mailMessage.getFrom());
                    String[] adminUserMails = getUserMailNameList();
                    if(toUserMail != null && toUserMail.length > 0) {
                        message.setTo(toUserMail);
                        if(enableCc) {
                            message.setCc(adminUserMails);
                        }
                    } else {
                        message.setTo(adminUserMails);
                    }
                    String IP = BusyPoolUtil.getPoolIP();
                    String subject = "";
                    // String idcName = BusyPoolUtil.getIdcName();
                    if(StringUtils.isNotBlank(title)) {
                        // subject = subject + "   " + title + "  IP=" + IP + "[" + idcName + "] ";
                        subject = subject + "   " + title + "  IP=" + IP;
                    }
                    message.setSubject(subject);
                    message.setText(content, true);
                }
            });
        }
    }

    @Override
    public void sendHtmlMail(String title, String content) {
        sendHtmlMail(null, title, content, true);
    }

    private void doSendMailTask(String title, String content, boolean force) {
        lock.lock();
        try {
            if(this.mailEnabled == true || force == true) {// 通过某个状态值控制高并发时,一定要并发入口再次检查其状态值
                Long invokeTime = System.currentTimeMillis();
                boolean flowControlOverLoaded = busyMailFlowControl.flowControlOverLoaded(invokeTime);
                // 检查流量控制是否达到最大值 未超载
                if(flowControlOverLoaded == false || force == true) {
                    doSendMail(title, content, force);
                } else {
                    // 流量达到最大值时,关闭邮件发送功能,同时发封邮件提醒用户
                    // String ip = BusyPoolUtil.getPoolIP();
                    doSendMail(
                            "---!!![重要]GPS-SCHEDULE邮件报警功能自动关闭!!!---",
                            "\n由于当前发送邮件频率过高,GPS-SCHEDULE邮件报警功能已自动关闭,"
                                    + "请检查GPS-SCHEDULE系统状态,是否异常过多,确认无问题后并在GPS-SCHEDULE管理界面手动开启邮件报警功能,\n若您长时间没有处理动作,邮件报警功能会在"
                                    + autoEnabledDelayTime + "分钟后自动开启,给您带来不必要的困扰,十分抱歉!\n", false);
                    // TODO lipengcheng 手工开启邮件功能待开发
                    // + "\n开启GPS-SCHEDULE邮件报警:http://" + ip
                    // + ":8080/admin/cache?mail=enabled&callback=?\n" + "\n查看BS邮件报警状态:http://" + ip
                    // + ":8080/admin/admin.jsp\n"
                    this.mailEnabled = false;
                    // 延迟一段时间自动开启邮件报警功能
                    Callable callable = new Callable<String>() {
                        public String call() {
                            switchMailEnabled();
                            String systemTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar
                                    .getInstance().getTime());
                            doSendMail("---!!![重要]GPS-SCHEDULE邮件报警功能已自动开启,请知晓!!!---", "\nGPS-SCHEDULE邮件报警功能已自动开启,请知晓! "
                                                                                      + systemTime, true);
                            return "";
                        }
                    };
                    FutureTask futureTask = new FutureTask(callable);
                    scheduler.schedule(futureTask, autoEnabledDelayTime, TimeUnit.MINUTES);
                }
            }
        } catch (Exception e) {
            log.error(" ", e);
        } finally {
            lock.unlock();
        }
    }

    private void doSendMail(String title, String content, boolean force) {
        if(this.mailEnabled == true || force == true) {
            String[] toUserMails = getUserMailNameList();
            SimpleMailMessage message = new SimpleMailMessage(mailMessage);
            if(toUserMails != null) {
                message.setTo(toUserMails);
            }
            // 设置email内容,
            String IP = BusyPoolUtil.getPoolIP();
            // String idcName = BusyPoolUtil.getIdcName();
            // String subject = message.getSubject() + "IP=" + IP + "[" + idcName + "]";
            String subject = message.getSubject() + "IP=" + IP;
            if(StringUtils.isNotBlank(title)) {
                subject = subject + "   " + title;
            }
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        }
    }

    private String[] getUserMailNameList() {
        String[] resultList = mailMessage.getTo();
        // 如果是stg环境直接返回管理员邮件组
        // if(BusyPoolUtil.isStg()) {
        // resultList = getAdminMailNameList();
        // return resultList;
        // }
        BusyMiscConfigVo configVo = busyMiscConfigDao
                .getBusyMiscConfigVoByKey(ScheduleConstants.BS_USER_MAIL_LIST_KEY_4_SCHEDULE);
        if(configVo != null && configVo.getItemValue() != null && configVo.getItemEnabled()) {
            String mailValue = configVo.getItemValue();
            mailValue = mailValue.trim();
            String[] mailNames = mailValue.split(",");
            if(mailNames != null && mailNames.length > 0) {
                resultList = mailNames;
            }
        }
        return resultList;
    }

    public String[] getAdminMailNameList() {
        String[] resultList = null;
        BusyMiscConfigVo configVo = busyMiscConfigDao
                .getBusyMiscConfigVoByKey(ScheduleConstants.BS_ADMIN_MAIL_LIST_KEY);
        if(configVo != null && configVo.getItemValue() != null && configVo.getItemEnabled()) {
            String mailValue = configVo.getItemValue();
            mailValue = mailValue.trim();
            String[] mailNames = mailValue.split(",");
            if(mailNames != null && mailNames.length > 0) {
                resultList = mailNames;
            }
        }
        return resultList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 如果当前host ip在白名单里面，就默认邮件报警功能开启
        try {
            if(whiteIPList != null && whiteIPList.trim().length() > 0) {
                String serverIP = BusyPoolUtil.getPoolIP();
                String[] hostIps = whiteIPList.split(",");
                if(serverIP != null && serverIP.trim().length() > 0 && hostIps != null && hostIps.length > 0) {
                    for(String hostIp : hostIps) {
                        if(serverIP.equals(hostIp)) {
                            this.mailEnabled = true;
                            break;
                        }
                    }
                }
            }
            // String systemTime = new
            // SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            // String codeVersion = BusyPoolUtil.getCodeVersion();

            // doSendMail("----[!!Tomcat启动通知!!]Tomcat startup---", "Tomcat startup " + systemTime
            // + ", SVN.committedRevision=" + codeVersion, true);
        } catch (Exception e) {
            log.error(" ", e);
        }
    }

    public void setWhiteIPList(String whiteIPList) {
        this.whiteIPList = whiteIPList;
    }

    public void setMailEnabled(boolean mailEnabled) {
        this.mailEnabled = mailEnabled;
    }

    public Long getProcessTimeout() {
        return processTimeout;
    }

    public void setProcessTimeout(Long processTimeout) {
        this.processTimeout = processTimeout;
    }

    public void setAutoEnabledDelayTime(Long autoEnabledDelayTime) {
        this.autoEnabledDelayTime = autoEnabledDelayTime;
    }
}
