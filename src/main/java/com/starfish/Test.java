package com.starfish;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.starfish.util.FileUtil;
import com.starfish.util.JsonUtil;
import lombok.Data;

import java.util.List;

/**
 * Test
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2020-10-28
 */
public class Test {

    public static void main(String[] args) throws JsonProcessingException {
        List<String> list = FileUtil.readLines("classpath:music.txt");
        Multiset<String> multiset = HashMultiset.create();
        multiset.addAll(list);
        multiset.remove("", Integer.MAX_VALUE);
        for (String item : multiset) {
            int count = multiset.count(item);
            if (count > 1) {
                System.out.println(item);
            }
        }
    }

}
