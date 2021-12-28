package com.starfish.extension.processor;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * CustomEnvironmentPostProcessor
 * 属性加载，加载在jar中的属性文件
 *
 * @author neacle
 * @version 1.0.0
 * @since 2021-06-30
 */
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final YamlPropertySourceLoader YAML_LOADER = new YamlPropertySourceLoader();

    private static final PropertiesPropertySourceLoader PROPERTIES_LOADER = new PropertiesPropertySourceLoader();

    @SneakyThrows
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 加载配置文件
        Resource resource = new ClassPathResource("/application.properties");
        List<PropertySource<?>> propertySourceList = PROPERTIES_LOADER.load("starfish", resource);
        PropertySource<?> propertySource = propertySourceList.get(0);
        environment.getPropertySources().addLast(propertySource);
    }

}
