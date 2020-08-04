package com.sunny.extension.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * application start listener
 *
 * @author sunny
 * @version 1.0.0
 * @since 2014-12-08
 */
@Component
@Slf4j
@SuppressWarnings(value = "serial,unused")
public abstract class AbstractApplicationListener implements org.springframework.context.ApplicationListener<ApplicationEvent>, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    /**
     * execute after bean initialized
     */
    public abstract void init();

    /**
     * execute after spring start complete
     *
     * @param event event
     */
    public abstract void execute(ContextRefreshedEvent event);

    public void setStartTime() {
        if (applicationContext instanceof WebApplicationContext) {
            ServletContext context = ((WebApplicationContext) applicationContext).getServletContext();
            String startTime = String.valueOf(System.currentTimeMillis());
            context.setAttribute("startTime", startTime);
            log.info("ApplicationListener setStartTime={}", startTime);
        }
    }

    /**
     * spring container event
     * 在web项目中（spring mvc），系统会存在两个容器，一个是root application context ,另一个就是我们自己的 projectName-servlet context
     * （作为root application context的子容器）,这种情况下，就会造成onApplicationEvent方法被执行两次。为了避免上面提到的问题，
     * 我们可以只在root application context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理，修改后代码，可以用event判断容器
     *
     * @param event event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextStartedEvent) {
            if (((ContextStartedEvent) event).getApplicationContext().getParent() == null) {
                log.info("root container start");
            } else {
                log.info("web container start");
            }
        } else if (event instanceof ContextRefreshedEvent) {
            if (((ContextRefreshedEvent) event).getApplicationContext().getParent() == null) {
                log.info("root container start complete");
            } else {
                log.info("web container start complete");
                //somethings you want to do after spring start complete
                execute((ContextRefreshedEvent) event);
            }
        } else if (event instanceof ContextStoppedEvent) {
            if (((ContextStoppedEvent) event).getApplicationContext().getParent() == null) {
                log.info("root container pause");
            } else {
                log.info("web container pause");
            }
        } else if (event instanceof ContextClosedEvent) {
            if (((ContextClosedEvent) event).getApplicationContext().getParent() == null) {
                log.info("root container close");
            } else {
                log.info("web container close");
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
