package com.starfish;

import com.google.protobuf.Api;
import com.starfish.model.ApiResult;

/**
 * Test
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
public class Test {

    public static void main(String[] args) {
        ApiResult apiResult = new ApiResult();
        Object o = apiResult;

        if (o instanceof ApiResult){
            System.out.println(true);
        }else{
            System.out.println(false);
        }

    }

}
