package com.yhd.gps.schedule.database;

import com.yhd.gps.busy.mail.BusyMailUtil;
import com.yihaodian.ydd.DynamicRoutingDataSource;

/**
 * @author:liguanghui1
 * @date ：2016-9-19 下午03:03:24
 */
public class BusyRoutingDataSource extends DynamicRoutingDataSource {
    @Override
    protected boolean isForceMasterDB() {
        return false;
        // return BusyConfigUtil.isItemEnabled(BusyConfigKeyUtil.BS_FORCE_MASTER_DB_ENABLE_KEY);
    }

    @Override
    protected void sendDBErrorMsg(String tag, String title, Throwable t) {
        if(logger.isDebugEnabled()) {
            logger.debug("[" + tag + "]" + title);
        }
        BusyMailUtil.sendMail("[" + tag + "]" + title, t);
    }

    @Override
    protected void sendDBErrorMsg(String tag, String title, String content) {
        if(logger.isDebugEnabled()) {
            logger.debug("[" + tag + "]" + title);
        }
        BusyMailUtil.sendMail("[" + tag + "]" + title, content);
    }
}
