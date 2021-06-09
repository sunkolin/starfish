package com.starfish.plus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * CustomApplicationRunner
 * springboot项目启动成功后执行一段代码有两种方式
 * 一种是实现ApplicationRunner
 * 另外一种是实现CommandLineRunner
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-09
 */
@Slf4j
public class CustomApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationRunner run.");
    }

}
