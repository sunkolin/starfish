package com.starfish.model;

import com.starfish.constant.Constant;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * MultiResultTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-12-13
 */
public class MultiResultTest {

    @Test
    public void getVersionTest() {
        MultiResult result = getMultiResult();
        System.out.println(result);

        String first = result.getFirst();
        System.out.println(first);

        String second = result.getSecond();
        System.out.println(second);

//        Long fourth =  result.getFourth();
//        System.out.println(fourth);

        List<String> fifth = result.getFifth();
        System.out.println(fifth);
    }

    public MultiResult getMultiResult() {
        MultiResult result = new MultiResult();
        result.setSecond("123");
        result.setFourth(10000000);
        result.setFifth(Lists.newArrayList("1", "2", "5"));
        return result;
    }

}
