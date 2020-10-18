package com.starfish.enums;

/**
 * 星期枚举
 *
 * @author sunny
 * @version 1.0.0
 * @since 2014-07-07
 */
@SuppressWarnings(value = "unused")
public enum WeekEnum {

    /**
     * 星期枚举
     */
    MONDAY(1, "monday", "周一"),

    TUESDAY(2, "tuesday", "周二"),

    WEDNESDAY(3, "wednesday", "周三"),

    THURSDAY(4, "thursday", "周四"),

    FRIDAY(5, "friday", "周五"),

    SATURDAY(6, "saturday", "周六"),

    SUNDAY(7, "sunday", "周日"),

    ;

    private final Integer code;
    private final String name;
    private final String message;

    /**
     * @param code    code
     * @param name    name
     * @param message message
     */
    WeekEnum(Integer code, String name, String message) {
        this.code = code;
        this.name = name;
        this.message = message;
    }

    /**
     * get the enum code
     *
     * @return code
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * get the enum name
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the message
     *
     * @return message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * get the enum by code
     *
     * @param code code
     * @return the enum
     */
    public static WeekEnum get(Integer code) {
        WeekEnum[] values = WeekEnum.values();
        WeekEnum v = null;
        for (WeekEnum value : values) {
            if (value.getCode().equals(code)) {
                v = value;
                break;
            }
        }
        return v;
    }

    /**
     * get the enum by name
     *
     * @param name name
     * @return the enum
     */
    public static WeekEnum get(String name) {
        WeekEnum[] values = WeekEnum.values();
        WeekEnum v = null;
        for (WeekEnum value : values) {
            if (value.getName().equalsIgnoreCase(name)) {
                v = value;
                break;
            }
        }
        return v;
    }

    /**
     * 判断此编码的枚举是否存在
     *
     * @param code 编码
     * @return 结果
     */
    public static boolean exist(Integer code) {
        return get(code) != null;
    }

    /**
     * 判断此名称枚举是否存在
     *
     * @param name 名称
     * @return 结果
     */
    public static boolean exist(String name) {
        return get(name) != null;
    }

}
