package com.starfish.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * CustomConfig
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-11-16
 */
@Data
//@RefreshScope
@Component("customConfig")
public class Config implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Value("${spring.application.name}")
    private String name;

    @Value("${server.port}")
    private String port;

    /**
     * 获取配置
     *
     * @return 结果
     */
    public static Config getConfig() {
        return applicationContext.getBean(Config.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Config.applicationContext = applicationContext;
    }

}
