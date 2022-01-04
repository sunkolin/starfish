package com.starfish.trial;

import com.google.common.collect.*;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * PriorityQueue
 *
 * @author neacle
 * @version 1.0.0
 * @since 2021-12-30
 */
public class SortedSet<E> implements Set<E> {

    // Comparator.comparingLong(Model::getScore)

    private final TreeSet<Model<E>> sortedSet = new TreeSet<>(new Comparator<Model<E>>() {
        @Override
        public int compare(Model<E> o1, Model<E> o2) {
            if (o1.equals(o2)){
                return 0;
            }else{
                return -1;
            }
//            return Comparator.comparing;
        }
    });

    public static void main(String[] args) {
//        SortedSet<String> sortedSet = new SortedSet<>();
//        sortedSet.add("tom", 100L);
//        sortedSet.add("jack", 3L);
//        sortedSet.add("kite", 200L);
//        sortedSet.add("kite", 201L);
//        for (String s : sortedSet) {
//            System.out.println(s);
//        }

        TreeMultiset<String> sortedSet = TreeMultiset.create();
        sortedSet.add("tom", 100);
        sortedSet.add("jack", 3);
        sortedSet.add("kite", 200);
        sortedSet.add("kite", 201);
//        sortedSet.setCount()
//        SortedMultiset<String> s1 =  sortedSet.headMultiset("mayun", BoundType.OPEN);

//        for(final SortedMultiset.Entry<String> entry : SortedMultiset.getEntriesSortedByFrequency(ms, true)){
//            System.out.println(MessageFormat.format("{0} ({1})",
//                    entry.getElement(), entry.getCount()));
//        }
        ImmutableMultiset<String> i =  Multisets.copyHighestCountFirst(sortedSet);

        for (String s : i) {
            System.out.println(s);
        }

//        sortedSet.
    }

    public void add(E e, Long score) {
        if (this.contains(new Model<>(e))) {
            Model<E> model = getModel(e);
            if (model != null) {
                model.setScore(score);
            }
        } else {
            Model<E> model = new Model<>();
            model.setE(e);
            model.setScore(score);
            sortedSet.add(model);
        }
    }

    public void increase(E e, Long score) {
        if (this.contains(new Model<>(e))) {
            Model<E> model = getModel(e);
            if (model != null) {
                model.setScore(model.getScore() + score);
            }
        } else {
            Model<E> model = new Model<>();
            model.setE(e);
            model.setScore(score);
            sortedSet.add(model);
        }
    }

    public E get() {
        return getFirst();
    }

    public E getFirst() {
        Model<E> model = sortedSet.pollFirst();
        if (model == null) {
            return null;
        } else {
            return model.getE();
        }
    }

    public E getLast() {
        Model<E> model = sortedSet.pollLast();
        if (model == null) {
            return null;
        } else {
            return model.getE();
        }
    }

    private Model<E> getModel(E e) {
        for (Model<E> model : sortedSet) {
            if (model.getE().equals(e)) {
                return model;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return sortedSet.size();
    }

    @Override
    public boolean isEmpty() {
        return sortedSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return sortedSet.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return sortedSet.stream().map(Model::getE).collect(Collectors.toList()).iterator();
    }

    @Override
    public Object[] toArray() {
        throw new RuntimeException("can not use this method.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new RuntimeException("can not use this method.");
    }

    @Override
    public boolean add(E e) {
        throw new RuntimeException("can not use this method.");
    }

    @Override
    public boolean remove(Object o) {
        return sortedSet.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return sortedSet.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new RuntimeException("can not use this method.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("can not use this method.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("can not use this method.");
    }

    @Override
    public void clear() {
        sortedSet.clear();
    }

    public static class Model<E> implements Serializable {

        private E e;

        private Long score;

        public Model() {
        }

        public Model(E e) {
            this.e = e;
        }

        public E getE() {
            return e;
        }

        public void setE(E e) {
            this.e = e;
        }

        public Long getScore() {
            return score;
        }

        public void setScore(Long score) {
            this.score = score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Model<?> model = (Model<?>) o;
            return Objects.equals(e, model.e);
        }

        @Override
        public int hashCode() {
            return e.hashCode();
        }

    }

}
