package com.starfish.util;

import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DateTimeUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-03-20
 */
@SuppressWarnings("unused")
@Slf4j
public class DateTimeUtil {

    /**
     * 根据任意日期格式转换成日期对象
     *
     * @param datetime 日期时间字符串
     * @return 日期时间
     */
    public static Date parse(String datetime) {
        Date result = parse(datetime, "yyyy-MM-dd HH:mm:ss");

        if (result == null) {
            result = parse(datetime, "yyyy-MM-dd");
        }
        if (result == null) {
            result = parse(datetime, "yyyyMMdd");
        }

        return result;
    }

    /**
     * 根据指定格式解析日期时间字符串
     *
     * @param datetime 日期时间字符串
     * @param format   格式
     * @return 日期时间
     */
    public static Date parse(String datetime, String format) {
        Date result = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            result = simpleDateFormat.parse(datetime);
        } catch (ParseException e) {
            log.warn("日期格式不是{}", format);
        }
        return result;
    }

    /**
     * 毫秒值转日期对象
     *
     * @param milliseconds 毫秒值
     * @return 日期
     */
    public static Date parse(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 两个日期之间的日期列表，包括这两个日期
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 结果
     */
    public static List<String> dateBetween(String start, String end) {
        List<String> result = new ArrayList<>();
        try {
            List<Date> dates = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date tmpDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);
            while (!tmpDate.after(endDate)) {
                Date tmp = tmpDate;
                dates.add(tmp);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tmp);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                tmpDate = calendar.getTime();
            }
            for (Date tmp : dates) {
                result.add(dateFormat.format(tmp));
            }
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 两个日期之间的日期列表，包括这两个日期
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 结果
     */
    public static List<Date> dateBetween(Date start, Date end) {
        List<Date> result = new ArrayList<>();
        try {
            Date tmpDate = start;
            while (!tmpDate.after(end)) {
                Date tmp = tmpDate;
                result.add(tmp);
                Calendar calendar = getCalendar(tmp);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                tmpDate = calendar.getTime();
            }
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 获取一个月的第一天
     *
     * @param date 日期
     * @return 结果
     */
    public static Date getMonthStart(Date date) {
        return DateUtil.beginOfMonth(date);
    }

    /**
     * 获取一个月的最后一天
     *
     * @param date 日期
     * @return 结果
     */
    public static Date getMonthEnd(Date date) {
        return DateUtil.endOfMonth(date);
    }

    /**
     * 获取一周的第一天，即周一
     *
     * @param date 日期
     * @return 结果
     */
    public static Date getWeekStart(Date date) {
        Calendar calendar = getCalendar(date);

        //老外那边把周日当成第一天
        calendar.add(Calendar.DATE, -1);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一周的最后一天，即周日
     *
     * @param date 日期
     * @return 结果
     */
    public static Date getWeekEnd(Date date) {
        Calendar calendar = getCalendar(date);

        //老外那边把周日当成第一天
        calendar.add(Calendar.DATE, -1);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间的月份 包括当前的月份
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 结果
     */
    public static List<Date> monthBetween(Date start, Date end) {
        Calendar s = Calendar.getInstance();
        s.setTime(start);
        Calendar e = Calendar.getInstance();
        e.setTime(end);
        List<Date> result = new ArrayList<>();
        while (s.before(e) || s.equals(e)) {
            s.set(Calendar.DAY_OF_MONTH, 1);
            s.set(Calendar.HOUR_OF_DAY, 0);
            s.set(Calendar.MINUTE, 0);
            s.set(Calendar.SECOND, 0);
            result.add(s.getTime());
            s.add(Calendar.MONTH, 1);
        }
        return result;
    }

    /**
     * 获取一天的最小值，第一秒
     *
     * @param date 时间
     * @return 结果
     */
    public static Date min(Date date) {
        return DateUtil.beginOfDay(date);
    }

    /**
     * 获取一天的最大值，最后一秒
     *
     * @param date 时间
     * @return 结果
     */
    public static Date max(Date date) {
        return DateUtil.endOfDay(date);
    }

    /**
     * 获取昨天最小值
     *
     * @return 结果
     */
    public static Date yesterdayMin() {
        return DateUtil.beginOfDay(DateUtil.yesterday());
    }

    /**
     * 获取昨天最大值
     *
     * @return 结果
     */
    public static Date yesterdayMax() {
        return DateUtil.endOfDay(DateUtil.yesterday());
    }

    /**
     * 判断指定日期是否是周一
     *
     * @param date 日期
     * @return true是，false不是
     */
    public static Boolean isMonday(Date date) {
        return getDayOfWeek(date) == 1;
    }

    /**
     * 判断指定日期是否是周二
     *
     * @param date 日期
     * @return true是，false不是
     */
    public static Boolean isTuesday(Date date) {
        return getDayOfWeek(date) == 2;
    }

    /**
     * 判断指定日期是否是周三
     *
     * @param date 日期
     * @return true是，false不是
     */
    public static Boolean isWednesday(Date date) {
        return getDayOfWeek(date) == 3;
    }

    /**
     * 判断指定日期是否是周四
     *
     * @param date 日期
     * @return true是，false不是
     */
    public static Boolean isThursday(Date date) {
        return getDayOfWeek(date) == 4;
    }

    /**
     * 判断指定日期是否是周五
     *
     * @param date 日期
     * @return true是，false不是
     */
    public static Boolean isFriday(Date date) {
        return getDayOfWeek(date) == 5;
    }

    /**
     * 判断指定日期是否是周六
     *
     * @param date 日期
     * @return true是，false不是
     */
    public static Boolean isSaturday(Date date) {
        return getDayOfWeek(date) == 6;
    }

    /**
     * 判断指定日期是否是周日
     *
     * @param date 日期
     * @return true是，false不是
     */
    public static Boolean isSunday(Date date) {
        return getDayOfWeek(date) == 0;
    }

    /**
     * 获取给定日期是周几
     *
     * @param date 日期
     * @return true是，false不是
     */
    private static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * Date转Calendar
     *
     * @param date 日期
     * @return 结果
     */
    private static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 判断是否是一个月的第一天
     *
     * @param date 日期
     * @return 结果
     */
    public static Boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth == 1;
    }

}
