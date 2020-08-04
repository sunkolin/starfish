package com.sunny.enums;

/**
 * 启用禁用状态枚举
 *
 * @author sunny
 * @version 1.0.0
 * @since 2014-07-07
 */
@SuppressWarnings(value = "unused")
public enum EnableStatusEnum {

    /**
     * 启用
     */
    ENABLE(1, "enable", "启用"),

    /**
     * 禁用
     */
    DISABLE(2, "disable", "禁用"),

    ;

    private Integer code;
    private String name;
    private String message;

    /**
     * @param code    code
     * @param name    name
     * @param message message
     */
    EnableStatusEnum(Integer code, String name, String message) {
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
    public static EnableStatusEnum get(Integer code) {
        EnableStatusEnum[] values = EnableStatusEnum.values();
        EnableStatusEnum v = null;
        for (EnableStatusEnum value : values) {
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
     * @param name the enum name
     * @return the enum
     */
    public static EnableStatusEnum get(String name) {
        EnableStatusEnum[] values = EnableStatusEnum.values();
        EnableStatusEnum v = null;
        for (EnableStatusEnum value : values) {
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
