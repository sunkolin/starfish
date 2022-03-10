package com.starfish.model;

import com.alibaba.fastjson.JSON;
import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * ResultTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-12-15
 */
public class ResultTest {

    @Test
    public void test() {
        Result<Object> result = new Result<>(ResultEnum.CAN_NOT_FIND_METHOD);
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getCode(), ResultEnum.CAN_NOT_FIND_METHOD.getCode());

        result = new Result<>(new CustomException(800, "参数错误"));
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getCode(), Integer.valueOf(800));

        result = new Result<>(new ArrayList<>());
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getCode(), Integer.valueOf(200));

        result = new Result<>("你好");
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getCode(), Integer.valueOf(200));
    }

    @Test(expected = NullPointerException.class)
    public void nullTest() {
        new Result<>(null);
    }

}
