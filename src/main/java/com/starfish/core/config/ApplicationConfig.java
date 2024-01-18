package com.starfish.core.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationConfig
 * 如果需要使用静态方法获取Bean，请使用SpringPlus类
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-11-16
 */
@Data
//@RefreshScope
@Component("applicationConfig")
public class ApplicationConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${spring.application.name}")
    private String name;

    @Value("${server.port}")
    private String port;

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
