package com.starfish.core.model;

import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.exception.CustomException;
import com.starfish.core.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * ResultTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-12-15
 */
class ResultTest {

    @Test
    void test() {
        Result<Object> result = Result.fail(ResultEnum.CAN_NOT_FIND_METHOD);
        System.out.println(JsonUtil.toJson(result));
        Assertions.assertEquals(result.getCode(), ResultEnum.CAN_NOT_FIND_METHOD.getCode());

        result = Result.fail(new CustomException(800, "参数错误"));
        System.out.println(JsonUtil.toJson(result));
        Assertions.assertEquals(result.getCode(), Integer.valueOf(800));

        result = Result.success(new ArrayList<>());
        System.out.println(JsonUtil.toJson(result));
        Assertions.assertEquals(result.getCode(), Integer.valueOf(0));

        result = Result.success("你好");
        System.out.println(JsonUtil.toJson(result));
        Assertions.assertEquals(result.getCode(), Integer.valueOf(0));
    }

}
