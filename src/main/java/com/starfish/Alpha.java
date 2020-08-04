package com.starfish;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Alpha
 *
 * @author sunny
 * @version 1.0.0
 * @since 2019-12-18
 */
@Slf4j
public class Alpha {

    public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList<>();

        for (String s : list){
            System.out.println(s);
            System.out.println("abc");
        }
        System.out.println(1234);
    }

}
