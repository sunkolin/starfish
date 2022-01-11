package com.starfish.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.starfish.enumeration.ResultEnum;
import com.starfish.enumeration.SortEnum;
import com.starfish.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * CollectionUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2016-05-17
 */
@SuppressWarnings("unused")
@Slf4j
public class CollectionUtil {

    /**
     * 数组排序
     *
     * @param array 数组
     * @param order 升序或者降序
     * @param <T>   T
     * @return 排序后的数组
     */
    public static <T extends Comparable<T>> T[] sort(T[] array, final String order) {
        //排序
        Arrays.sort(array);

        //反转
        if (SortEnum.DESC.getEnglishCode().equalsIgnoreCase(order)) {
            List<T> list = Arrays.asList(array);
            Collections.reverse(list);
            list.toArray(array);
        }

        //返回
        return array;
    }

    /**
     * 数组排序
     *
     * @param array      数组
     * @param order      升序或者降序
     * @param comparator 比较器
     * @param <T>        T
     * @return 排序后的数组
     */
    public static <T> T[] sort(T[] array, final String order, Comparator<? super T> comparator) {
        //排序
        Arrays.sort(array, comparator);

        //反转，转数组，反转
        if (SortEnum.DESC.getEnglishCode().equalsIgnoreCase(order)) {
            List<T> list = Arrays.asList(array);
            Collections.reverse(list);
            list.toArray(array);
        }

        //返回
        return array;
    }

    /**
     * 判断List是否为空
     *
     * @param list 列表
     * @param <T>  T
     * @return 结果
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 列表中查询出指定属性值的对象，原列表保持不变
     *
     * @param list     列表
     * @param property 属性
     * @param value    值
     * @param <T>      T
     * @return 列表
     */
    public static <T> List<T> select(List<T> list, String property, Object value) {
        List<T> result = new ArrayList<>();
        try {
            if (list == null) {
                throw new IllegalArgumentException("list can not be null");
            }
            for (T obj : list) {
                Class<?> cls = obj.getClass();
                Method method = cls.getMethod("get" + StringUtil.firstCharToUpperCase(property));
                method.setAccessible(true);
                Object v = method.invoke(obj);
                if (CommonUtil.equals(v, value)) {
                    result.add(obj);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new CustomException(ResultEnum.CAN_NOT_FIND_METHOD);
        }
        return result;
    }

    /**
     * 反转
     *
     * @param list 列表
     * @param <T>  T
     * @return 结果
     */
    public static <T> List<T> reverse(List<T> list) {
        Collections.reverse(list);
        return list;
    }

    /**
     * 删除指定对象，原列表保持不变
     *
     * @param list     列表
     * @param property 属性
     * @param value    值
     * @param <T>      T
     * @return 删除指定属性为指定值的对象后的列表
     */
    public static <T> List<T> remove(List<T> list, String property, Object value) {
        ArrayList<T> result = new ArrayList<>(list);
        List<T> tmp = select(result, property, value);
        result.removeAll(tmp);
        return result;
    }

    /**
     * 列表排序
     *
     * @param list  列表
     * @param order 升序或者降序
     * @param <T>   T
     * @return 排序后的列表
     */
    public static <T extends Comparable<? super T>> List<T> sort(List<T> list, String order) {
        Collections.sort(list);
        if (SortEnum.DESC.getEnglishCode().equalsIgnoreCase(order)) {
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * 列表排序
     *
     * @param list       列表
     * @param order      升序或者降序
     * @param comparator 比较器
     * @param <T>        T
     * @return 排序后的列表
     */
    public static <T> List<T> sort(List<T> list, String order, Comparator<? super T> comparator) {
        list.sort(comparator);
        if (SortEnum.DESC.getEnglishCode().equalsIgnoreCase(order)) {
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * 去重
     *
     * @param list 列表
     * @param <T>  类型
     * @return 去重后的新列表
     */
    public static <T> List<T> distinct(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (!result.contains(t)) {
                result.add(t);
            }
        }

        return result;
    }

    /**
     * 分割
     *
     * @param list 列表
     * @param size 长度
     * @param <T>  T
     * @return 结果
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        return Lists.partition(list, size);
    }

    /**
     * map排序
     *
     * @param map   map
     * @param order 升序或者降序
     * @param <K>   K
     * @param <V>   V
     * @return 结果
     */
    public static <K extends Comparable<? super K>, V> LinkedHashMap<K, V> sort(Map<K, V> map, String order) {
        LinkedHashMap<K, V> result = new LinkedHashMap<>();

        //排序
        ArrayList<K> list = new ArrayList<>(map.keySet());
        Collections.sort(list);

        //反转
        if (SortEnum.DESC.getEnglishCode().equalsIgnoreCase(order)) {
            Collections.reverse(list);
        }

        //设值
        for (K key : list) {
            result.put(key, map.get(key));
        }

        return result;
    }

    /**
     * 按比较器的顺序排map 可以实现按值大小排序
     *
     * @param map        map
     * @param order      升序或者降序
     * @param comparator 比较器
     * @param <K>        K
     * @param <V>        V
     * @return 结果
     */
    public static <K, V> LinkedHashMap<K, V> sort(Map<K, V> map, String order, Comparator<? super Map.Entry<K, V>> comparator) {
        LinkedHashMap<K, V> result = new LinkedHashMap<>();

        //自定义排序
        ArrayList<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(comparator);

        //反转
        if (SortEnum.DESC.getEnglishCode().equalsIgnoreCase(order)) {
            Collections.reverse(list);
        }

        //设值
        for (Map.Entry<K, V> tmp : list) {
            result.put(tmp.getKey(), tmp.getValue());
        }

        return result;
    }

    /**
     * 删除键包含key字符串的值
     *
     * @param map map
     * @param key key
     */
    public static void remove(Map<String, ?> map, String key) {
        Validator.validateNotNull(map, -1, "map must not be null");
        map.keySet().removeIf(k -> k != null && k.contains(key));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Class<T> componentType, List<T> list) {
        T[] array = (T[]) Array.newInstance(componentType, list.size());
        return list.toArray(array);
    }

    /**
     * 获取列表中连续数据
     *
     * @param list 列表
     * @param num  连续个数
     * @return 结果
     */
    public static List<Long> continuousList(List<Long> list, Integer num) {
        List<Long> result = new ArrayList<>();

        List<Long> tmpList = new ArrayList<>();

        for (int index = 0; index < list.size(); index++) {
            Long i = list.get(index);

            // 如果列表为空，直接加入第一个
            if (CollectionUtils.isEmpty(tmpList)) {
                tmpList.add(i);
                continue;
            }

            // 如果当前的数字和列表是连续的，加入列表
            Long last = tmpList.get(tmpList.size() - 1);
            if (last + 1 == i) {
                tmpList.add(i);

                if (index == list.size() - 1) {
                    if (tmpList.size() >= num) {
                        result.addAll(tmpList);
                    }
                }

            } else {
                if (tmpList.size() >= num) {
                    result.addAll(tmpList);
                }
                tmpList.clear();
                tmpList.add(i);
            }
        }

        log.info("continuousList,param={},num={},result={}", JSON.toJSONString(list), num, JSON.toJSONString(result));
        return result;
    }

}
