package com.starfish.enumeration;

/**
 * 角色枚举
 *
 * @author sunny
 * @version 1.0.0
 * @since 2014-07-07
 */
@SuppressWarnings(value = "unused")
public enum RoleEnum {

    /**
     * 游客
     */
    GUEST(1, "guest", "游客", "游客"),

    /**
     * 普通
     */
    USER(2, "user", "普通用户", "普通用户"),

    /**
     * 管理
     */
    ADMIN(3, "admin", "管理员", "管理员"),

    /**
     * 超级
     */
    SUPER(4, "super", "超级管理员", "超级管理员"),

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
     * RoleEnum
     *
     * @param code        code
     * @param englishCode englishCode
     * @param name        name
     * @param message     message
     */
    RoleEnum(Integer code, String englishCode, String name, String message) {
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
    public static RoleEnum get(Integer code) {
        RoleEnum[] values = RoleEnum.values();
        RoleEnum v = null;
        for (RoleEnum value : values) {
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
    public static RoleEnum get(String englishCode) {
        RoleEnum[] values = RoleEnum.values();
        RoleEnum v = null;
        for (RoleEnum value : values) {
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
