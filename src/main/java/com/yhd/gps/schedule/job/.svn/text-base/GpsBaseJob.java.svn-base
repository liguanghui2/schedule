package com.yhd.gps.schedule.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ---- 请加注释 ------
 * 
 * @Author chenmao
 * @CreateTime 2015-11-10 下午03:46:07
 */
public abstract class GpsBaseJob {

    private static final Log logger = LogFactory.getLog(GpsBaseJob.class);

    protected String jobName;
    protected String jobDesc;

    public abstract void init();

    public abstract void doJobTask();

    public void execute() {
        // 初始化Job
        init();
        logger.debug("---###---[GPS]---" + jobName + "初始化成功,开始执行");
        try {
            doJobTask();
        } catch (Exception e) {
            logger.debug("---###---[GPS]---" + jobName + "执行异常：" + e.getMessage(), e);
        }
        logger.debug("---###---[GPS]---" + jobName + "结束");
    }

}
