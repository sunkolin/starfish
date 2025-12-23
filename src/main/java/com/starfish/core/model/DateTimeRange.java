package com.starfish.core.model;

import java.util.Date;

/**
 * DateTimeRange
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2023-05-12
 */
public class DateTimeRange extends Range<Date> {

    public DateTimeRange() {

    }

    public DateTimeRange(Date start, Date end) {
        super(start, end);
    }

}
