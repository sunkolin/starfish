package com.starfish.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * ExecuteOnStartup
 * springboot项目启动成功后执行一段代码有两种方式
 * 一种是实现ApplicationRunner
 * 另外一种是实现CommandLineRunner
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-09
 */
@Slf4j
public class ExecuteOnStartup implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ExecuteOnStartup run.");
    }

}
