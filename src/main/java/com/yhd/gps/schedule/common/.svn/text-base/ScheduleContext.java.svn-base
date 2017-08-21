package com.yhd.gps.schedule.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 计算上下文
 * 
 * @author Hikin Yao
 * @version 1.0
 */
public class ScheduleContext {
    private static final ThreadLocal<Map<Object, Object>> context = new ThreadLocal<Map<Object, Object>>() {
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<Object, Object>();
        }
    };

    public static Object getValue(Object key) {
        if(context.get() == null) {
            return null;
        }
        return context.get().get(key);
    }

    public static Object setValue(Object key, Object value) {
        Map<Object, Object> cacheMap = context.get();
        if(cacheMap == null) {
            cacheMap = new HashMap<Object, Object>();
            context.set(cacheMap);
        }
        return cacheMap.put(key, value);
    }

    public static void removeValue(Object key) {
        Map<Object, Object> cacheMap = context.get();
        if(cacheMap != null) {
            cacheMap.remove(key);
        }
    }

    public static void reset() {
        if(context.get() != null) {
            context.get().clear();
        }
    }
}
