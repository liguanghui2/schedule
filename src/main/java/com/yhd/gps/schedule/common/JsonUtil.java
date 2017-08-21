package com.yhd.gps.schedule.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class JsonUtil {

    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // 输出空置字段
                                                         // SerializerFeature.WriteNullListAsEmpty,
                                                         // // list字段如果为null，输出为[]，而不是null
                                                         // SerializerFeature.WriteNullNumberAsZero,
                                                         // // 数值字段如果为null，输出为0，而不是null
                                                         // SerializerFeature.WriteNullBooleanAsFalse,
                                                         // // Boolean字段如果为null，输出为false，而不是null
                                                         // SerializerFeature.WriteNullStringAsEmpty,
                                                         // // 字符类型字段如果为null，输出为""，而不是null
                                                         SerializerFeature.WriteDateUseDateFormat // 日期格式化yyyy-MM-dd
                                                                                                  // HH:mm:ss
    };

    public static String toJson(Object object) {
        return JSON.toJSONString(object, features);
    }
}
