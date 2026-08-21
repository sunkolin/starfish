package com.starfish.core;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;

/**
 * 配置解密监听器
 * 配置解密，可以解密配置中心和配置文件等加密配置项
 * ApplicationPreparedEvent：Bean 定义全部加载完毕，Bean 还没有实例化
 * 到这里 Environment 里面已经完整包含配置中心远程配置**（可以`env.getProperty()`拿到远程值），但 Bean 还没 new 出来
 *
 */
@Slf4j
public class ConfigDecryptListener implements ApplicationListener<ApplicationPreparedEvent> {

    /**
     * knife4j基本密码配置项是否启用
     */
    public static final List<String> KNIFE4J_BASIC_PASSWORD_ENABLED_KEYS = List.of("knife4j.enable", "knife4j.basic.enable");

    /**
     * 加密密码配置项
     */
    public static final String KNIFE4J_BASIC_PASSWORD_KEY = "knife4j.basic.password";

    /**
     * 加密类型配置项
     */
    public static final String KNIFE4J_BASIC_PASSWORD_TYPE_KEY = "knife4j.basic.password-encrypt-type";

    /**
     * 加密密钥配置项
     */
    public static final String KNIFE4J_BASIC_PASSWORD_SECRET_KEY = "knife4j.basic.password-encrypt-secret";

    /**
     * 数据库密码配置项
     */
    public static final String SPRING_DATA_SOURCE_PASSWORD_KEY = "spring.datasource.password";

    @Override
    public void onApplicationEvent(@NonNull ApplicationPreparedEvent event) {
        log.info("ConfigDecryptListener start.");

        try {
            ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
            // 解密并应用配置
            DecryptConfigUtil.decrypt(KNIFE4J_BASIC_PASSWORD_ENABLED_KEYS, KNIFE4J_BASIC_PASSWORD_KEY, KNIFE4J_BASIC_PASSWORD_TYPE_KEY, KNIFE4J_BASIC_PASSWORD_SECRET_KEY, environment);
        } catch (Exception e) {
            System.out.println("配置解密失败，key是" + KNIFE4J_BASIC_PASSWORD_ENABLED_KEYS + "异常是" + e.getMessage());
        }

        try {
            ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
            // 解密并应用配置
            DecryptConfigUtil.decrypt(SPRING_DATA_SOURCE_PASSWORD_KEY, environment);
        } catch (Exception e) {
            System.out.println("配置解密失败，key是" + SPRING_DATA_SOURCE_PASSWORD_KEY + "异常是" + e.getMessage());
        }

        log.info("ConfigDecryptListener end.");
    }

}
