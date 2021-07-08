package com.starfish.core.enumeration;

/**
 * 性别枚举
 * （1）枚举类不能有public修饰的构造函数，构造函数都是隐含private，编译器自动处理。
 * （2）每个枚举值隐含都是由public、static、final修饰的，不需要添加这些修饰符。
 *
 * @author sunny
 * @version 1.0.0
 * @since 2014-07-07
 */
@SuppressWarnings(value = "unused")
public enum SexEnum {

    /**
     * 男
     */
    MAN(1, "man", "男", "男"),

    /**
     * 女
     */
    WOMAN(2, "woman", "女", "女"),

    /**
     * 保密
     */
    SECRET(3, "secret", "保密", "保密"),

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
     * SexEnum
     *
     * @param code        code
     * @param englishCode englishCode
     * @param name        name
     * @param message     message
     */
    SexEnum(Integer code, String englishCode, String name, String message) {
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
    public static SexEnum get(Integer code) {
        SexEnum[] values = SexEnum.values();
        SexEnum v = null;
        for (SexEnum value : values) {
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
    public static SexEnum get(String englishCode) {
        SexEnum[] values = SexEnum.values();
        SexEnum v = null;
        for (SexEnum value : values) {
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
