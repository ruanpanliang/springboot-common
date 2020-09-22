package com.lc.springboot.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * spring获取bean工具类
 *
 * @author liangchao
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    /**
     * 根据class对象获取bean对象信息
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cla) {
        return applicationContext.getBean(cla);
    }

    /**
     * 根据bean的名称和class对象获取bean对象信息
     * @param name
     * @param cal
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> cal) {
        return applicationContext.getBean(name, cal);
    }

    /**
     * 根据系统属性名称获取属性值
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return applicationContext.getBean(Environment.class).getProperty(key);
    }
}
