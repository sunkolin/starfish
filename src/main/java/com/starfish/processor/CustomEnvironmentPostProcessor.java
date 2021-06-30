package com.starfish.processor;

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
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-30
 */
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();

    private static final PropertiesPropertySourceLoader propertiesLoader = new PropertiesPropertySourceLoader();

    @SneakyThrows
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 加载配置文件
        Resource resource = new ClassPathResource("/application.properties");
        List<PropertySource<?>> propertySourceList = propertiesLoader.load("starfish", resource);
        PropertySource<?> propertySource = propertySourceList.get(0);
        environment.getPropertySources().addLast(propertySource);
    }

}
