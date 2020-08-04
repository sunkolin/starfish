package com.starfish.configuration;

import com.starfish.enums.ResultEnum;
import com.starfish.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * PropertiesConfiguration
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-09-06
 */
@Deprecated
@Configuration
public class PropertiesConfiguration {

    private static Logger logger = LoggerFactory.getLogger(PropertiesConfiguration.class);

    /**
     * 此处不需要加classpath前缀，也不需要加反斜杠
     */
    public static final String propertyPath = "application.properties";

    @Bean(name = "defaultProperties")
    public Properties setDefaultProperties() {
        try {
            Properties defaultProperties = PropertiesLoaderUtils.loadAllProperties(propertyPath);
            logger.info("configure properties success.properties size is " + defaultProperties.size());
            return defaultProperties;
        } catch (IOException e) {
            logger.error("configure properties fail.can not find application.properties file");
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "configure properties fail.can not find application.properties file");
        }
    }

}
