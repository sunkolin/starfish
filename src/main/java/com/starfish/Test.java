package com.starfish;

import com.starfish.util.FileUtil;

/**
 * Test
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
public class Test {

    public static void main(String[] args) {
        String s =  FileUtil.randomName(".jpg");
        System.out.println(s);
    }

}
