package com.yhd.gps.schedule.database;

import com.yihaodian.ydd.util.DynamicDbContext;

/**
 * @author:liguanghui1
 * @date ：2016-9-19 下午04:04:51
 */
public class BusyDbContext {
    public static boolean switchToMasterDB() {
        DynamicDbContext.switchToMasterDB();
        return true;
    }

    public static boolean switchToSlaveDB() {
        DynamicDbContext.switchToSlaveDB();
        return true;
    }

    public static boolean isMasterDB() {
        return DynamicDbContext.isMasterDB();
    }

    public static void reset() {
        DynamicDbContext.reset();
    }
}
