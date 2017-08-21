package com.yhd.gps.schedule.common;

import java.util.Map;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public interface BusyCacheProxy {
    /**
     * 存入对象, 缓存时间取默认值
     *
     * @param key
     * @param value
     */
    boolean put(String key, Object value);

    boolean put(String key, Object value, boolean enableLocal);

    /**
     * 存入对象
     *
     * @param key
     * @param value
     * @param expirMins 过期时间
     */
    boolean put(String key, Object value, int expirMins);

    boolean put(String key, Object value, int expirMins, boolean enableLocal);

    /**
     * 获取对象
     *
     * @param key
     * @return
     */
    Object get(String key);

    Object get(String key, boolean enableLocal);

    /**
     * 获取多个键值
     *
     * @param keys
     * @return
     */
    Map<String, Object> getMulti(String[] keys);

    Map<String, Object> getMulti(String[] keys, boolean enableLocal);

    /**
     * 从缓存中移除对象
     *
     * @param key
     */
    void remove(String key);

    void remove(String key, boolean enableLocal);

    /**
     * 存入字符串
     *
     * @param key
     * @param value
     */
    void putString(String key, String value);

    /**
     * 存入字符串
     *
     * @param key
     * @param value
     * @param expirMins
     */
    void putString(String key, String value, int expirMins);

    /**
     * 获取字符串
     *
     * @param key
     * @return
     */
    String getString(String key);
    
    /**
     * 多IDC缓存失效
     * @param key
     * @return
     */
    boolean invalid(String key);
}
