package com.yhd.gps.schedule.common;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.yihaodian.configcentre.client.utils.YccGlobalPropertyConfigurer;

public class KafkaProducerUtils {

    // kafka配置文件名称
    private final static String KAFKA_CONFIG_FILE_NAME = "kafka.properties";
    //线上kafka消息topic名称
    public final static String GPS_PM_PRICE_PMIDS = "gps_pm_price_pmIds";

    private static Producer<String, String> producer;

    private KafkaProducerUtils() {

    }

    @SuppressWarnings("deprecation")
    public static Producer<String, String> getProducerInstance() {
        if(null == producer) {
            synchronized(KafkaProducerUtils.class) {
                if(null == producer) {
                    Properties props = new Properties();
                    props.put("serializer.class", "kafka.serializer.StringEncoder");
                    props.put("metadata.broker.list", YccGlobalPropertyConfigurer.getPropertyByKey(KAFKA_CONFIG_FILE_NAME, "metadata.broker.list"));
                    ProducerConfig config = new ProducerConfig(props);
                    producer = new Producer<String, String>(config);
                }
            }
        }
        return producer;
    }

    public static void sendMessage(String topic, String message) {
        getProducerInstance().send(new KeyedMessage<String, String>(topic, message));
    }

}
