package com.starfish.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        Assert.assertEquals(r1.get(0).getUsername(), "tom");

        List<UserModel> r2 = CollectionUtil.select(list, "username", "tomson");
        Assert.assertEquals(r2.size(), 0);

        List<UserModel> r3 = CollectionUtil.remove(list, "username", "tom");
        Assert.assertEquals(r3.size(), 0);

        List<UserModel> r4 = CollectionUtil.select(list, "age", 20);
        Assert.assertEquals(r4.get(0).getUsername(), "tom");
    }

    @Test
    public void testExistClass() {
        Assert.assertTrue(CommonUtil.existClass("com.starfish.util.CommonUtil"));

        Assert.assertFalse(CommonUtil.existClass("com.starfish.util.CommonUtil2"));
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
