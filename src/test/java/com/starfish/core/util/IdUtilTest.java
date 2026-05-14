package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * IdUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-05-14
 */
@Slf4j
class IdUtilTest {

    @Test
    void getIdTest() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            if (i % 10 == 0){
                Thread.sleep(1000);
            }
            Long id = IdUtil.getId();
            System.out.println(id);
        }
    }

}
