package com.starfish.core;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * ConfigurationLoadListener
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-09-01
 */
@Slf4j
public class ConfigurationLoadListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(@NonNull ApplicationPreparedEvent event) {
        log.info("ConfigurationLoadListener start.");

        // 获取是否打印配置
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        Boolean enabledPrint = environment.getProperty("starfish.configuration.enabled-print", Boolean.class, false);
        if (!enabledPrint) {
            log.info("======= 打印配置未开启 =======");
            return;
        } else {
            log.info("======= 打印配置已开启 =======");
        }

        // 打印配置
        log.info("======= 开始打印配置 =======");
        String keys = environment.getProperty("starfish.configuration.print-keys", String.class, "");
        if (keys.trim().isEmpty()) {
            return;
        }
        String[] keysArray = keys.split(",");
        for (String key : keysArray) {
            log.info("{}={}", key, environment.getProperty(key));
        }
        log.info("======= 配置打印完成 =======");

        log.info("ConfigurationLoadListener end.");
    }

}
