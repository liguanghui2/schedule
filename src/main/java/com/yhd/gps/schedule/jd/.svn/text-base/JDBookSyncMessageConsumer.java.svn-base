package com.yhd.gps.schedule.jd;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.yhd.gps.busyservice.service.JDBookSyncService;
import com.yhd.gps.schedule.common.PoolUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.SpringBeanUtil;
import com.yhd.gps.schedule.vo.JDBookMessageVo;
import com.yihaodian.architecture.jumper.common.message.Message;
import com.yihaodian.architecture.jumper.consumer.BackoutMessageException;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.front.databus.DatabusConstants;
import com.yihaodian.front.databus.DatabusListener;
import com.yihaodian.front.databus.config.DatabusConsumerConfig;
import com.yihaodian.front.databus.jumper.DatabusConsumerJumper;

/**
 * 
 * 京东图书同步
 * @Author lipengcheng
 * @CreateTime 2016-12-1 下午02:53:17
 */
public class JDBookSyncMessageConsumer implements InitializingBean {
    private static final Log logger = LogFactory.getLog(JDBookSyncMessageConsumer.class);

    /**
     * 单次处理的最大消息数量，open-api要求10个
     */
    public static final int MAX_MSG_SIZE = 10;

    /**
     * 消费者线程数
     */
    private static final int THREAD_POOL_SIZE = 2;

    /**
     * open-api接口返回正常
     */
    public static final int OPEN_API_SUCCESS = 1;

    /**
     * 京东接口返回正常
     */
    public static final String JD_SUCCESS = "0";

    /**
     * 当前IP地址
     */
    public static String localIp = PoolUtils.computePoolIp();

    private static JDBookSyncService jdBookSyncService = (JDBookSyncService) SpringBeanUtil
            .getBean("jdBookSyncService");

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("京东图书同步消息监听器已开启------！！！！！！");
        init();
    }

    /**
     * 
     * 京东图书同步消息监听器
     * @Author lipengcheng
     * @CreateTime 2016-12-1 下午02:54:46
     */
    private static class JDBookSyncMessageListener extends DatabusListener<Long> {

        @Override
        public void onMessage(Message msg) throws BackoutMessageException {
            @SuppressWarnings("unchecked")
            List<JDBookMessageVo> messageVos = msg.transferContentToBean(List.class);
            if(CollectionUtils.isEmpty(messageVos)) {
                return;
            }
            try {
                List<List<JDBookMessageVo>> splitJDBookMessageVos = GPSUtils.split(messageVos, MAX_MSG_SIZE);
                for(List<JDBookMessageVo> jdBookMessageVos : splitJDBookMessageVos) {
                    jdBookSyncService.syncJDBookPrice(jdBookMessageVos, null);
                }
            } catch (Exception e) {
                logger.error("同步京东图书价格异常：" + e.getMessage());
                throw new BackoutMessageException(e.getMessage());
            }
        }

        @Override
        public void onDatabusMessage(List<Long> items) {

        }
    }

    public static void init() {
        DatabusConsumerConfig consumerConfig = new DatabusConsumerConfig();
        // 设置消费者线程数
        consumerConfig.setThreadPoolSize(THREAD_POOL_SIZE);
        DatabusConsumerJumper<Long> databusConsumer = new DatabusConsumerJumper<Long>();
        // 设置消费者id，自定义
        consumerConfig.setConsumerId(ScheduleConstants.GPS_SCHEDULE_JUMPER_CONSUMER_ID);
        databusConsumer.setConsumerConfig(consumerConfig);
        // 处理京东图书同步消息
        databusConsumer.consumeMessage(DatabusConstants.JD_BOOK_SYNC_TOPIC, new JDBookSyncMessageListener());
    }

}
