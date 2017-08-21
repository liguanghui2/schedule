package com.yhd.gps.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.schedule.common.BusyDataCache;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BusyDataCacheTest extends BaseSpringTest {

    @Autowired
    private BusyDataCache busyDataCache;
    
    @Test
    public void put() {
        if(busyDataCache == null){
            return;
        }
        busyDataCache.put("test_key", "test");
        busyDataCache.put("test_key", "test",false);
        busyDataCache.put("test_key", "test",true);
        busyDataCache.put("test_key", "test",10);
        busyDataCache.put("test_key", "test",10,true);
        busyDataCache.put("test_key", "test",10,false);
        
        busyDataCache.get("test_key");
        busyDataCache.getMulti(new String[]{"test_key"});
        
        busyDataCache.remove("test_key");
    }
}
