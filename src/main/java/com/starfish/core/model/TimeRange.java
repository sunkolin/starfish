package com.starfish.core.model;

import java.util.Date;

/**
 * TimeRange
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2023-05-12
 */
public class TimeRange extends Range<Date> {

    private Date start;

    private Date end;

    public TimeRange() {
    }

    public TimeRange(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

}
