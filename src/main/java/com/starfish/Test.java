package com.starfish;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.protobuf.Api;
import com.starfish.model.ApiResult;
import com.starfish.plus.CachePlus;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Test
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
public class Test {

    public static void main(String[] args) {
        Cache<Object, Object> caffeineCache = Caffeine.newBuilder().expireAfterWrite(3600, TimeUnit.SECONDS).build();

        CachePlus cachePlus = new CachePlus(caffeineCache);
//        Object o =  caffeineCache.getIfPresent("abc");
//        System.out.println(o);


        boolean b =  cachePlus.exist("abc");
        System.out.println(b);
        Object a = null;

        List c = (List) a;
    }

}
