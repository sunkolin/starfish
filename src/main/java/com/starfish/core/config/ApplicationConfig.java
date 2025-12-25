package com.starfish.core.config;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * ApplicationConfig
 * 如果需要使用静态方法获取Bean，请使用Springs类
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-11-16
 */
@Data
@Component("applicationConfig")
public class ApplicationConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private Environment environment;

    public String getName() {
        return environment.getProperty("spring.application.name");
    }

    public String getPort() {
        return environment.getProperty("server.port");
    }

    public String getProfile() {
        return environment.getProperty("spring.profiles.active");
    }

    public String getEnvironment() {
        return environment.getProperty("spring.profiles.active");
    }

    /**
     * 获取配置
     *
     * @return 结果
     */
    public ApplicationConfig getConfig() {
        return applicationContext.getBean(ApplicationConfig.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
