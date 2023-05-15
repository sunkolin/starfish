package com.starfish.core.model;

/**
 * IntegerRange
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2023-05-12
 */
public class IntegerRange extends Range<Integer> {

    private Integer start;

    private Integer end;

    @Override
    public Integer getStart() {
        return start;
    }

    @Override
    public void setStart(Integer start) {
        this.start = start;
    }

    @Override
    public Integer getEnd() {
        return end;
    }

    @Override
    public void setEnd(Integer end) {
        this.end = end;
    }

}
