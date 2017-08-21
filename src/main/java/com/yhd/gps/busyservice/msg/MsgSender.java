package com.yhd.gps.busyservice.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.busyservice.service.BusyLogService;
import com.yhd.gps.schedule.common.Command;
import com.yhd.gps.schedule.common.ExecutorManager;
import com.yhd.gps.schedule.vo.JumperMessageLog;
import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;

/**
 * 消息发送器基类
 * @Author xuyong
 * @CreateTime 2015-7-21 上午11:19:36
 */
public abstract class MsgSender<T> {

    private static final Log LOGGER = LogFactory.getLog(MsgSender.class);

    protected BusyLogService busyLogService;
    protected HistoryPriceChangeMsgDao historyPriceChangeMsgDao;

    public void setBusyLogService(BusyLogService busyLogService) {
        this.busyLogService = busyLogService;
    }

    public void setHistoryPriceChangeMsgDao(HistoryPriceChangeMsgDao historyPriceChangeMsgDao) {
        this.historyPriceChangeMsgDao = historyPriceChangeMsgDao;
    }

    public void saveJumperMessageLog(final String topic, final List<T> messages, final String messageType) {
        ExecutorManager.executeOnly(new Command<Void>() {
            @Override
            public Void action() {
                try {
                    List<JumperMessageLog> messageLogs = buildJumperMessageLog(messages, topic, messageType, new Date());
                    busyLogService.batchInsertMessageLog(messageLogs);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                return null;
            }
        });
    }

    public List<JumperMessageLog> buildJumperMessageLog(List<T> messages, String topic, String messageType,
                                                        Date sendTime) {
        if(CollectionUtils.isEmpty(messages) || null == sendTime) {
            return new ArrayList<JumperMessageLog>(0);
        }
        List<JumperMessageLog> result = new ArrayList<JumperMessageLog>(messages.size());
        for(T message : messages) {
            if(null == message) {
                continue;
            }
            String content = JSON.toJSONString(message);
            if(content.length() > 1000) {
                content = content.substring(0, 1300);
            }

            JumperMessageLog log = createJumperMessageLog(message, topic, messageType, content, sendTime);
            result.add(log);
        }
        return result;
    }

    protected void recordHistoryPriceChangeMsgs(List<T> messages) {
        if(CollectionUtils.isEmpty(messages)) {
            return;
        }
        List<HistoryPriceChangeMsgVo> historyPriceChangeMsgs = new ArrayList<HistoryPriceChangeMsgVo>(messages.size());
        for(T message : messages) {
            if(null == message) {
                continue;
            }
            historyPriceChangeMsgs.add(buildHistoryPriceChangeMsg(message));
        }
        historyPriceChangeMsgDao.batchInsertHistoryPriceChangeMsg(historyPriceChangeMsgs);
    }

    public abstract HistoryPriceChangeMsgVo buildHistoryPriceChangeMsg(T message);

    public abstract JumperMessageLog createJumperMessageLog(T message, String topic, String messageType,
                                                            String content, Date sendTime);

}
