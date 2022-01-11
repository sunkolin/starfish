package com.starfish.util;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

/**
 * CollectionUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2014-11-27
 */
public class CollectionUtilTest {

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

    @Test
    public void sortStringTest() {
        String[] array = new String[]{"1", "3", "2", "a", "4"};
        CollectionUtil.sort(array, "desc");
        System.out.println(JSON.toJSONString(array));
        Assert.assertArrayEquals(new String[]{"a", "4", "3", "2", "1"}, array);
    }

    @Test
    public void sortLongTest() {
        List<Long> list = new ArrayList<>();
        list.add(99L);
        list.add(48L);
        list.add(75L);
        list.add(12L);
        list.add(63L);
        list.add(36L);
        list.add(40L);
        CollectionUtil.sort(list, "desc", null);
        System.out.println(JSON.toJSONString(list));
        Assert.assertEquals(12L, list.get(list.size() - 1).longValue());
    }

    @Test
    public void sortMapTest() {
        HashMap<Date, String> map = new HashMap<>();
        map.put(new Date(), "123");
        map.put(new Date(), "456");
        map.put(new Date(), "789");
        map.put(new Date(), "873");
        LinkedHashMap<Date, String> result = CollectionUtil.sort(map, "desc");
        Set<Date> keySet = result.keySet();
        Date last = null;
        for (Date key : keySet) {
            last = key;
        }
        Assert.assertEquals("873", result.get(last));
    }

    @Test
    public void sortMapValueTest() {
        HashMap<String, UserModel> map = new HashMap<>();
        map.put("Beethoven", new UserModel(1L, "Beethoven", "", 100));
        map.put("Bush", new UserModel(3L, "Bush", "", 100));
        map.put("Clinton", new UserModel(83L, "Clinton", "", 100));
        map.put("Washington", new UserModel(35L, "Washington", "", 100));
        map.put("Lincoln", new UserModel(58L, "Lincoln", "", 100));
        LinkedHashMap<String, UserModel> result = CollectionUtil.sort(map, "desc", (o1, o2) -> {
            long comp = o1.getValue().getId() - o2.getValue().getId();
            if (comp > 0) {
                return 1;
            } else if (comp == 0) {
                return 0;
            } else {
                return -1;
            }
        });
        Set<String> keySet = result.keySet();
        String last = null;
        for (String key : keySet) {
            last = key;
        }
        Assert.assertEquals("Beethoven", result.get(last).getUsername());
    }

    /**
     * 用户模型
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class UserModel implements Serializable {

        private Long id;

        private String username;

        private String password;

        private Integer age;

    }

}
