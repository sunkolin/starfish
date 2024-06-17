package com.starfish.trial;

import cn.hutool.core.collection.BoundedPriorityQueue;

import java.util.*;

/**
 * RankList
 * 排行榜
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-12-30
 */
public class RankList<E> extends BoundedPriorityQueue<E> {

    public RankList(int capacity) {
        super(capacity);
    }

    public RankList(int capacity, Comparator<? super E> comparator) {
        super(capacity, comparator);
    }

    @Override
    public boolean add(E e) {
        return super.offer(e);
    }

    public ArrayList<E> getList() {
        return super.toList();
    }

}
