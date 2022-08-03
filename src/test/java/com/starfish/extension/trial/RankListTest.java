package com.starfish.extension.trial;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 * RankListTest
 * 排行榜
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-12-30
 */
 class RankListTest {

    @Test
     void test() {
        RankList<Student> rankList = new RankList<>(3, (o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

        Student student1 = new Student("tom", 98);
        Student student2 = new Student("jack", 80);
        Student student3 = new Student("lily", 59);
        Student student4 = new Student("kite", 88);
        Student student5 = new Student("john", 100);

        rankList.add(student1);
        rankList.add(student2);
        rankList.add(student3);
        rankList.add(student4);
        rankList.add(student5);

        Assertions.assertEquals(3, rankList.size());

        for (Student student : rankList) {
            System.out.println(student);
        }
    }


    @Data
    @AllArgsConstructor
    static class Student implements Serializable {

        String name;

        int score;

    }

}
