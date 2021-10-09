package com.starfish.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * CommonUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-21
 */
public class CommonUtilTest {

    @Test
    public void selectList() {
        List<UserModel> list = new ArrayList<>();
        UserModel model = new UserModel();
        model.setUsername("tom");
        model.setPassword("jack");
        model.setAge(20);
        list.add(model);

        List<UserModel> r1 = CollectionUtil.select(list, "username", "tom");
        assertEquals(r1.get(0).getUsername(), "tom");

        List<UserModel> r2 = CollectionUtil.select(list, "username", "tomson");
        assertEquals(r2.size(), 0);

        List<UserModel> r3 = CollectionUtil.remove(list, "username", "tom");
        assertEquals(r3.size(), 0);

        List<UserModel> r4 = CollectionUtil.select(list, "age", 20);
        assertEquals(r4.get(0).getUsername(), "tom");
    }

    @Test
    public void testExistClass() {
        assertTrue( CommonUtil.existClass("com.starfish.util.CommonUtil"));

        assertFalse(CommonUtil.existClass("com.starfish.util.CommonUtil2"));
    }

    @Test
    public void checkWordsTrue() {
        String word = "考前答案";
        boolean result = BusinessUtil.contains(word);
        assertTrue(result);
    }

    @Test
    public void checkWordsFalse() {
        String word2 = "没有";
        boolean result2 = BusinessUtil.contains(word2);
        assertFalse(result2);
    }

    /**
     * 用户模型
     */
    static class UserModel {

        private String username;
        private String password;
        private Integer age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}
