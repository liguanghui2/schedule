package com.yhd.gps.schedule.common;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yihaodian.common.idc.IDCMemcacheProxy;
import com.yihaodian.common.ycache.CacheProxy;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyDataCache implements BusyCacheProxy {
    private static final Log log = LogFactory.getLog(BusyDataCache.class);
    private CacheProxy dataCache;
    private IDCMemcacheProxy idcCacheProxy;
    private static boolean cacheEnabled = true;// 方便单元测试控制 增加的开关

    public void setDataCache(CacheProxy dataCache) {
        this.dataCache = dataCache;
    }

    public void setIdcCacheProxy(IDCMemcacheProxy idcCacheProxy) {
        this.idcCacheProxy = idcCacheProxy;
    }

    @Override
    public boolean put(String key, Object value) {
        return put(key, value, false);
    }

    @Override
    public boolean put(String key, Object value, boolean enableLocal) {
        boolean result = false;
        if(cacheEnabled) {
            try {
                result = dataCache.put(key, value);
            } catch (Exception e) {
                log.error("Memecache发生异常key=" + key, e);
            }
        }
        return result;
    }

    @Override
    public boolean put(String key, Object value, int expirMins) {
        if(!put(key, value, expirMins, false)) {
            log.warn("memcache put false");
            return false;
        }
        return true;
    }

    @Override
    public boolean put(final String key, final Object value, final int expirMins, final boolean enableLocal) {
        boolean result = false;
        if(cacheEnabled) {
            try {
                result = dataCache.put(key, value, expirMins);
            } catch (Exception e) {
                log.error("Memecache发生异常key=" + key, e);
            }
        }
        return result;
    }

    @Override
    public Object get(String key) {
        return get(key, false);
    }

    @Override
    public Object get(String key, boolean enableLocal) {
        Object obj = null;
        if(cacheEnabled) {
            try {
                obj = dataCache.get(key);
            } catch (Exception e) {
                log.error("Memecache发生异常key=" + key, e);
            }
        }
        return obj;
    }

    @Override
    public Map<String, Object> getMulti(String[] keys) {
        return getMulti(keys, false);
    }

    @Override
    public Map<String, Object> getMulti(String[] keys, boolean enableLocal) {
        Map<String, Object> result = null;
        if(cacheEnabled) {
            try {
                result = dataCache.getMulti(keys);
            } catch (Exception e) {
                log.error("Memecache发生异常keys=" + ArrayUtils.toString(keys), e);
            }
        }
        return result;
    }

    @Override
    public void remove(String key) {
        remove(key, false);
    }

    @Override
    public void remove(String key, boolean enableLocal) {
        if(cacheEnabled) {
            try {
                if(!dataCache.remove(key)) {
                    log.warn("memcache remove error");
                }
                // 多IDC memcache 失效
                invalid(key);
            } catch (Exception e) {
                log.error("Memecache发生异常key=" + key, e);
            }
        }
    }

    @Override
    public void putString(String key, String value) {
        if(cacheEnabled) {
            try {
                dataCache.putString(key, value);
            } catch (Exception e) {
                log.error("Memecache发生异常key=" + key, e);
            }
        }
    }

    @Override
    public void putString(String key, String value, int expirMins) {
        if(cacheEnabled) {
            try {
                dataCache.putString(key, value, expirMins);
            } catch (Exception e) {
                log.error("Memecache发生异常key=" + key, e);
            }
        }
    }

    @Override
    public String getString(String key) {
        String result = null;
        if(cacheEnabled) {
            try {
                result = dataCache.getString(key);
            } catch (Exception e) {
                log.error("Memecache发生异常key=" + key, e);
            }
        }
        return result;
    }

    @Override
    public boolean invalid(String key) {
        try {
            if(idcCacheProxy != null) {
                if(idcCacheProxy.invalid(key)) {
                    return true;
                } else {
                    log.warn("########## IDC Cache Invalid Failed #############");
                }
            } else {
                log.error("########## IDC Cache Invalid Error idcCacheProxy==null #############");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;

    }
}
