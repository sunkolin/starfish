package com.starfish.core.model;

/**
 * Range
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2023-05-12
 */
public class Range<T> {

    private T start;

    private T end;

    public Range() {

    }

    public Range(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public void setStart(T start) {
        this.start = start;
    }

    public T getEnd() {
        return end;
    }

    public void setEnd(T end) {
        this.end = end;
    }

}
