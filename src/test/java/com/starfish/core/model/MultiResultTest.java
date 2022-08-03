package com.starfish.core.model;

import org.assertj.core.util.Lists;
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
class MultiResultTest {

    @Test
    void multiResultTest() {
        MultiResult result = new MultiResult();
        result.setFirst("123");
        result.setSecond(10000000);
        result.setThird(Lists.newArrayList("1", "2", "5"));
        System.out.println(result);

        String first = result.getFirst();
        System.out.println(first);

        int second = result.getSecond();
        System.out.println(second);

        List<String> fifth = result.getThird();
        System.out.println(fifth);

        int expectedValue = 10000000;
        int actualValue = result.getSecond();
        Assertions.assertEquals(expectedValue, actualValue);
    }

}
