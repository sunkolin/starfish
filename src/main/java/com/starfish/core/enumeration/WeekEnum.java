package com.starfish.core.enumeration;

/**
 * WeekEnum
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2014-07-07
 */
@SuppressWarnings("unused")
public enum WeekEnum {

    /**
     * 星期一
     */
    MONDAY(1, "monday", "星期一"),

    /**
     * 星期二
     */
    TUESDAY(2, "tuesday",  "星期二"),

    /**
     * 星期三
     */
    WEDNESDAY(3, "wednesday",  "星期三"),

    /**
     * 星期四
     */
    THURSDAY(4, "thursday",  "星期四"),

    /**
     * 星期五
     */
    FRIDAY(5, "friday",  "星期五"),

    /**
     * 星期六
     */
    SATURDAY(6, "saturday",  "星期六"),

    /**
     * 星期日
     */
    SUNDAY(7, "sunday", "星期日"),

    ;

    /**
     * 编码
     */
    private final Integer code;

    /**
     * 名称
     */
    private final String name;

    /**
     * 描述
     */
    private final String message;

    /**
     * WeekEnum
     *
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
     * get the code of the enum
     *
     * @return code
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * get the name of the enum
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the message of the enum
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
     * verify the code exist or not exist
     *
     * @param code code
     * @return result
     */
    public static boolean exist(Integer code) {
        return get(code) != null;
    }

    /**
     * verify the name exist or not exist
     *
     * @param name name
     * @return result
     */
    public static boolean exist(String name) {
        return get(name) != null;
    }

}
