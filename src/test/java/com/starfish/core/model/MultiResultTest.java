package com.starfish.core.model;

import com.google.common.collect.Lists;
import com.starfish.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * MultiResultTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-12-13
 */
@Slf4j
class MultiResultTest {

    @Test
    void multiResultTest() {
        MultiResult result = new MultiResult();
        result.setFirst("123");
        result.setSecond(10000000);
        result.setThird(Lists.newArrayList("1", "2", "5"));
        log.info(JsonUtil.toJson(result));

        String first = result.getFirst();
        log.info(first);

        int second = result.getSecond();
        log.info("second={}",second);

        List<String> fifth = result.getThird();
        log.info(JsonUtil.toJson(fifth));

        int expectedValue = 10000000;
        int actualValue = result.getSecond();
        Assertions.assertEquals(expectedValue, actualValue);
    }

}
