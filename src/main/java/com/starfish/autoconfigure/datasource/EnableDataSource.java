package com.starfish.autoconfigure.datasource;

import com.starfish.autoconfigure.datasource.DataSourceImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableDataSource
 * 原理
 * 通过EnableDataSource Import DataSourceImportSelector，在其中获取EnableDataSource注解上配置的value属性值
 * 根据value值设置上线文属性，在spring.factories中配置了
 * org.springframework.boot.autoconfigure.AutoConfigurationImportFilter=
 * com.starfish.autoconfigure.datasource.DataSourceAutoConfigurationImportFilter
 * 在此过滤器中获取到设置的上下文属性，根据上线文属性判断是否导入数据源配置类DataSourceAutoConfiguration和
 * HibernateJpaAutoConfiguration
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DataSourceImportSelector.class)
public @interface EnableDataSource {

    boolean value() default true;

}
