package com.yhd.gps.schedule.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class SpringBeanUtil implements BeanFactoryAware {
    private static BeanFactory ctx;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        setBeanFactoryStatic(beanFactory);
    }

    private static void setBeanFactoryStatic(BeanFactory beanFactory) {
        ctx = beanFactory;
    }

    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    public static BeanFactory getBeanFactory() {
        return ctx;
    }

}
