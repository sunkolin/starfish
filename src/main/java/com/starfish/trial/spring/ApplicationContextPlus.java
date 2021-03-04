package com.starfish.trial.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextPlus
 * 使用时无需注入ApplicationContextPlus对象，可以直接调用静态方法
 * ApplicationContextPlus.getApplicationContext();
 *
 * @author sunny
 * @version 1.0.0
 * @since 2016-02-24
 */
@Component
@SuppressWarnings(value = "unused")
public class ApplicationContextPlus implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @SuppressWarnings("NullableProblems")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextPlus.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
