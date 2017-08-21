package com.yhd.gps.schedule.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.yhd.gps.busyservice.service.BusyLogService;

/**
 * gps schedule 的基础服务类，建议所有的定时器service类继承此类
 * @Author liuxiangrong
 * @CreateTime 2016-1-30 下午10:35:39
 */
public class ScheduleBaseService implements BeanFactoryAware {
    protected BusyLogService busyLogService;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // cacheProxy = (BusyCacheProxy)
        // beanFactory.getBean(BusyCacheConstants.BUSY_DATA_CACHE_BEAN_NAME);
        busyLogService = (BusyLogService) beanFactory.getBean("busyLogService");
    }

}