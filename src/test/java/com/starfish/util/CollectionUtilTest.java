package com.starfish.util;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * SortUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2014-11-27
 */
public class CollectionUtilTest {

    @Test
    public void sortStringTest() {
        String[] array = new String[]{"1", "3", "2", "a", "4"};
        array = CollectionUtil.sort(array, "desc");
        System.out.println(JSON.toJSONString(array));
        Assert.assertArrayEquals(new String[]{"a", "4", "3", "2", "1"}, array);
    }

    @Test
    public void sortLongTest() {
        List<Long> list = new ArrayList<Long>();
        list.add(99L);
        list.add(48L);
        list.add(75L);
        list.add(12L);
        list.add(63L);
        list.add(36L);
        list.add(40L);
        list = CollectionUtil.sort(list, "desc", null);
        System.out.println(JSON.toJSONString(list));
        Assert.assertEquals(12L, list.get(list.size() - 1).longValue());
    }

    @Test
    public void sortMapTest() {
        HashMap<Date, String> map = new HashMap<Date, String>();
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
        HashMap<String, TempModel> map = new HashMap<String, TempModel>();
        map.put("Beethoven", new TempModel(1L, "Beethoven"));
        map.put("Bush", new TempModel(3L, "Bush"));
        map.put("Clinton", new TempModel(83L, "Clinton"));
        map.put("Washington", new TempModel(35L, "Washington"));
        map.put("Lincoln", new TempModel(58L, "Lincoln"));
        LinkedHashMap<String, TempModel> result = CollectionUtil.sort(map, "desc", new Comparator<Map.Entry<String, TempModel>>() {
            @Override
            public int compare(Map.Entry<String, TempModel> o1, Map.Entry<String, TempModel> o2) {
                Long comp = o1.getValue().getId() - o2.getValue().getId();
                if (comp > 0) {
                    return 1;
                } else if (comp == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        Set<String> keySet = result.keySet();
        String last = null;
        for (String key : keySet) {
            last = key;
        }
        Assert.assertEquals("Beethoven", result.get(last).getUsername());
    }

    class TempModel {
        private Long id;

        private String username;

        public TempModel(Long id, String username) {
            this.id = id;
            this.username = username;
        }

        public Long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

    }

}
