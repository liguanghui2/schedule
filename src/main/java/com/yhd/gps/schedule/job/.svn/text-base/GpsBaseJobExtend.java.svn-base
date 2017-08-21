package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 扩展baseJob，支持入参
 * @Author lipengcheng
 * @CreateTime 2017-3-2 下午02:27:18
 */

public abstract class GpsBaseJobExtend extends GpsBaseJob {

    private static final Log logger = LogFactory.getLog(GpsBaseJobExtend.class);

    public abstract void doJobTask(JSONObject jsonObject);

    @Override
    public void doJobTask() {
    }

    public void execute(JSONObject jsonObject) {
        // 初始化Job
        init();
        logger.debug("---###---[GPS]---" + jobName + "初始化成功,开始执行");
        try {
            doJobTask(jsonObject);
        } catch (Exception e) {
            logger.debug("---###---[GPS]---" + jobName + "执行异常：" + e.getMessage(), e);
        }
        logger.debug("---###---[GPS]---" + jobName + "结束");
    }

}
