package com.starfish.module.extension.spring;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Applications
 *
 * @author sunny
 * @version 1.0.0
 * @since 2019-07-04
 */
@Data
@Component
public class SuperApplication {

    @Value("${spring.application.name}")
    private String name;

    @Value("${server.port}")
    private String port;

    @Value("${spring.profiles.active}")
    private String profile;

    @Resource
    private ApplicationContext applicationContext;

}
