package com.starfish.experiment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ApplicationListenerExample
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2014-12-08
 */
@Slf4j
@SuppressWarnings("unused")
@Component
public class ApplicationListenerExample implements ApplicationListener<ApplicationEvent>, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    /**
     * spring container event
     * 在web项目中（spring mvc），系统会存在两个容器，一个是root application context,
     * 另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）,
     * 这种情况下，就会造成onApplicationEvent方法被执行两次。为了避免上面提到的问题，
     * 我们可以只在root application context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理，
     * 修改后代码，可以用event判断容器
     *
     * @param event event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartedEvent) {
            onApplicationEvent((ApplicationStartedEvent) event);
        }
        if (event instanceof ApplicationReadyEvent) {
            onApplicationEvent((ApplicationReadyEvent) event);
        }
        if (event instanceof ApplicationFailedEvent) {
            onApplicationEvent((ApplicationFailedEvent) event);
        }
        if (event instanceof ContextClosedEvent) {
            onApplicationEvent((ContextClosedEvent) event);
        }
    }

    /**
     * ApplicationStartedEvent
     *
     * @param event ApplicationStartedEvent
     */
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("Application Started");
    }

    /**
     * ApplicationReadyEvent
     *
     * @param event ApplicationReadyEvent
     */
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Application Ready");
        onReady(event);
    }

    /**
     * ApplicationFailedEvent
     *
     * @param event ApplicationFailedEvent
     */
    public void onApplicationEvent(ApplicationFailedEvent event) {
        log.error("Application Failed");
    }

    /**
     * ContextClosedEvent
     * 这两个事件看起来都是等于容器要关闭，其实不然，close是spring容器真正销毁了才会触发，
     * 而stop事件只是容器把实现了Lifecycle的bean给stop了，还可以使用start将其重新启动。
     *
     * @param event ContextClosedEvent
     */
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("Application Closed");
    }

    /**
     * execute after spring start complete
     *
     * @param event event
     */
    public void onReady(ApplicationReadyEvent event) {
        // 打印启动时间
        long startupDate = applicationContext.getStartupDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String applicationStartupTime = format.format(new Date(startupDate));
        log.info("application startup time={}", applicationStartupTime);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // init

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
