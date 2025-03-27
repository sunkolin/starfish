package com.starfish.core.enumeration;

/**
 * RoleEnum
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2014-07-07
 */
@SuppressWarnings("unused")
public enum RoleEnum {

    /**
     * 游客
     */
    GUEST(1, "guest", "游客"),

    /**
     * 普通
     */
    USER(2, "user", "普通用户"),

    /**
     * 管理
     */
    ADMIN(3, "admin", "管理员"),

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
     * RoleEnum
     *
     * @param code    code
     * @param name    name
     * @param message message
     */
    RoleEnum(Integer code, String name, String message) {
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
     * @param name name
     * @return the enum
     */
    public static RoleEnum get(String name) {
        RoleEnum[] values = RoleEnum.values();
        RoleEnum v = null;
        for (RoleEnum value : values) {
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
