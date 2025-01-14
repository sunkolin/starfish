package com.starfish.example;

import com.starfish.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Comparator;

/**
 * RankListTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-12-30
 */
@Slf4j
class RankListTest {

    @Test
    void test() {
        Comparator<Student> comparator = Comparator.comparingInt(Student::getScore);
        RankList<Student> rankList = new RankList<>(3, comparator.reversed());

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

        // 验证数量
        Assertions.assertEquals(3, rankList.size());

        // 验证排序
        Assertions.assertEquals(rankList.getList().get(0), student5);

        for (Student student : rankList) {
            log.info(JsonUtil.toJson(student));
        }
    }


    @Data
    @AllArgsConstructor
    static class Student implements Serializable {

        private String name;

        private int score;

    }

}
