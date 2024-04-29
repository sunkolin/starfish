package com.starfish.core;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * SpringPlus
 * 使用时无需注入ApplicationContext对象
 * 可以直接调用静态方法SpringPlus.getApplicationContext();这样可以实现在工具类中使用
 * 参考：cn.hutool.extra.spring.SpringUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2019-07-04
 */
@Data
@Lazy(false)
public class SpringPlus implements ApplicationContextAware, EnvironmentAware  {

    /**
     * 通过name获取Bean
     *
     * @param <T>  Bean类型
     * @param name Bean名称
     * @return Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) SpringConstant.APPLICATION_CONTEXT.getBean(name);
    }

    /**
     * 通过class获取Bean
     *
     * @param <T>   Bean类型
     * @param clazz Bean类
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return SpringConstant.APPLICATION_CONTEXT.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param <T>   bean类型
     * @param name  Bean名称
     * @param clazz bean类型
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return SpringConstant.APPLICATION_CONTEXT.getBean(name, clazz);
    }

    /**
     * 获取指定类型对应的所有Bean，包括子类
     *
     * @param <T>  Bean类型
     * @param type 类、接口，null表示获取所有bean
     * @return 类型对应的bean，key是bean注册的name，value是Bean
     * @since 5.3.3
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return SpringConstant.APPLICATION_CONTEXT.getBeansOfType(type);
    }

    /**
     * 获取配置文件配置项的值
     *
     * @param key 配置项key
     * @return 属性值
     * @since 5.3.3
     */
    public static String getProperty(String key) {
        return SpringConstant.APPLICATION_CONTEXT.getEnvironment().getProperty(key);
    }

    /**
     * 获取服务名称
     *
     * @return 服务名称
     */
    public static String getApplicationName() {
        return SpringConstant.ENVIRONMENT.getProperty("spring.application.name");
    }

    /**
     * 获取端口
     *
     * @return 端口
     */
    public static String getPort() {
        return SpringConstant.ENVIRONMENT.getProperty("server.port");
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     * @since 5.3.3
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return activeProfiles.length != 0 ? activeProfiles[0] : null;
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     * @since 5.3.3
     */
    public static String[] getActiveProfiles() {
        return SpringConstant.APPLICATION_CONTEXT.getEnvironment().getActiveProfiles();
    }

    /**
     * 动态向Spring注册Bean
     * 由{@link org.springframework.beans.factory.BeanFactory} 实现，通过工具开放API
     *
     * @param <T>      Bean类型
     * @param beanName 名称
     * @param bean     bean
     * @author shadow
     * @since 5.4.2
     */
    public static <T> void registerBean(String beanName, T bean) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) SpringConstant.APPLICATION_CONTEXT;
        context.getBeanFactory().registerSingleton(beanName, bean);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringConstant.APPLICATION_CONTEXT = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringConstant.APPLICATION_CONTEXT;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public  void setEnvironment(Environment environment) {
        SpringConstant.ENVIRONMENT = environment;
    }

}
