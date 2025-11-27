package com.starfish.core.constant;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * SpringConstant
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-08-01
 */
public class SpringConstant {

    /**
     * 另外一种方式可以使用注解自动注入ApplicationContext
     * 自动注入的ApplicationContext，无法定义为static的
     */
    public static ApplicationContext APPLICATION_CONTEXT;

    public static Environment ENVIRONMENT;

    private SpringConstant(){

    }

}
