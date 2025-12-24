package com.starfish.experiment.datasource;

import com.google.common.collect.Sets;
import com.starfish.core.context.PropertiesContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;

import java.util.Set;

/**
 * DataSourceAutoConfigurationImportFilter
 * 移除默认的数据源配置
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-03
 */
public class DataSourceAutoConfigurationImportFilter implements AutoConfigurationImportFilter, BeanFactoryAware, BeanClassLoaderAware {

    private BeanFactory beanFactory;

    private ClassLoader beanClassLoader;

    private static final Set<String> EXCLUDE_CLASSES = Sets.newHashSet(
            "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
            "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration");

    @Override
    public boolean[] match(String[] classNames, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            String className = classNames[i];
            // 默认移除数据源相关的类
            String dataSourceEnabled = PropertiesContext.get("starfish.dataSource.enabled");
            // 未配置 EnableDataSource 注解或value设置为true都通过
            if (dataSourceEnabled == null || "true".equalsIgnoreCase(dataSourceEnabled)) {
                matches[i] = true;
            } else {
                matches[i] = !EXCLUDE_CLASSES.contains(className);
            }
        }
        return matches;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected final BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    protected final ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

}
