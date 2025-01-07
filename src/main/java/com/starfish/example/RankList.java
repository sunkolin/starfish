package com.starfish.example;

import java.util.Comparator;
import java.util.List;

/**
 * RankList 排行榜
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-12-30
 */
public class RankList<E> extends cn.hutool.core.collection.BoundedPriorityQueue<E> {

    /**
     * 默认从大到小排序
     *
     * @param capacity 条数
     */
    public RankList(int capacity) {
        //noinspection unchecked
        super(capacity, ((Comparator<E>) (o1, o2) -> ((Comparable<E>) o1).compareTo(o2)).reversed());
    }

    public RankList(int capacity, Comparator<? super E> comparator) {
        super(capacity, comparator);
    }

    @Override
    public boolean add(E e) {
        return super.offer(e);
    }

    public List<E> getList() {
        return super.toList();
    }

}
