package com.starfish.enumeration;

/**
 * 星期枚举
 *
 * @author sunny
 * @version 1.0.0
 * @since 2014-07-07
 */
@SuppressWarnings("unused")
public enum WeekEnum {

    /**
     * 周一
     */
    MONDAY(1, "monday", "周一", "周一"),

    TUESDAY(2, "tuesday", "周二", "周二"),

    WEDNESDAY(3, "wednesday", "周三", "周三"),

    THURSDAY(4, "thursday", "周四", "周四"),

    FRIDAY(5, "friday", "周五", "周五"),

    SATURDAY(6, "saturday", "周六", "周六"),

    SUNDAY(7, "sunday", "周日", "周日"),

    ;

    /**
     * 数字编码
     */
    private final Integer code;

    /**
     * 英文编码
     */
    private final String englishCode;

    /**
     * 中文名称
     */
    private final String name;

    /**
     * 中文描述
     */
    private final String message;

    /**
     * WeekEnum
     *
     * @param code        code
     * @param englishCode englishCode
     * @param name        name
     * @param message     message
     */
    WeekEnum(Integer code, String englishCode, String name, String message) {
        this.code = code;
        this.englishCode = englishCode;
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
     * get english code
     *
     * @return englishCode
     */
    public String getEnglishCode() {
        return this.englishCode;
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
     * @param englishCode englishCode
     * @return the enum
     */
    public static WeekEnum get(String englishCode) {
        WeekEnum[] values = WeekEnum.values();
        WeekEnum v = null;
        for (WeekEnum value : values) {
            if (value.getEnglishCode().equalsIgnoreCase(englishCode)) {
                v = value;
                break;
            }
        }
        return v;
    }

    /**
     * verify the code exist exist or not exist
     *
     * @param code code
     * @return result
     */
    public static boolean exist(Integer code) {
        return get(code) != null;
    }

    /**
     * verify the english code exist or not exist
     *
     * @param englishCode english code
     * @return result
     */
    public static boolean exist(String englishCode) {
        return get(englishCode) != null;
    }

}
