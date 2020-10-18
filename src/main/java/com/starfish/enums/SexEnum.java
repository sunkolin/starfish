package com.starfish.enums;

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
    MAN(1, "man", "男"),

    /**
     * 女
     */
    WOMAN(2, "woman", "女"),

    /**
     * 其他
     */
    OTHER(3, "other", "其他"),

    /**
     * 保密
     */
    SECRET(4, "secret", "保密"),

    ;

    private final Integer code;
    private final String name;
    private final String message;

    /**
     * @param code    code
     * @param name    name
     * @param message message
     */
    SexEnum(Integer code, String name, String message) {
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
     * @param name name
     * @return the enum
     */
    public static SexEnum get(String name) {
        SexEnum[] values = SexEnum.values();
        SexEnum v = null;
        for (SexEnum value : values) {
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
