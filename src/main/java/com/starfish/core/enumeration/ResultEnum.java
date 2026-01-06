package com.starfish.core.enumeration;

/**
 * ResultEnum
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2014-07-07
 */
@SuppressWarnings("unused")
public enum ResultEnum {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION(500, "系统异常"),

    /**
     * 自定义错误从1001开始
     */
    PARAM_ERROR(1001, "参数错误"),

    DATE_TIME_FORMAT_ERROR(1002, "日期格式有误"),

    PARSE_DATE_TIME_ERROR(1003, "解析日期异常"),

    FILE_PATH_IS_EMPTY(1004, "文件路径为空"),

    FILE_ALREADY_EXIST(1005, "文件已存在"),

    FILE_DOWNLOAD_ERROR(1006, "下载文件异常"),

    DECOMPRESS_FILE_ERROR(1007, "解压文件异常"),

    COMPRESS_FILE_ERROR(1008, "压缩文件异常"),

    CAN_NOT_FIND_METHOD(1009, "找不到方法"),

    FIELD_COUNT_IS_ZERO(1010, "当前对象中没有任何属性值"),

    FILE_TYPE_ERROR(1011, "文件类型错误"),

    SAVE_FILE_EXCEPTION(1012, "保存文件异常"),

    GET_WEATHER_EXCEPTION(1013, "查询天气异常"),

    GET_FILE_NAME_EXCEPTION(1014, "获取文件名异常"),

    NO_READ_PERMISSION(1015, "没有读权限"),

    NO_WRITE_PERMISSION(1016, "没有写权限"),

    PUSH_DEER_PUSH_RESPONSE_STATUS_CODE_ERROR(1017, "PushDeer推送请求返回HttpStatusCode错误。"),

    PUSH_DEER_PUSH_RETURN_CODE_ERROR(1018, "PushDeer推送请求返回code错误。"),

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
     * ResultEnum
     *
     * @param code    code
     * @param message message
     */
    ResultEnum(Integer code, String message) {
        this.code = code;
        this.name = "";
        this.message = message;
    }

    /**
     * ResultEnum
     *
     * @param code    code
     * @param name    name
     * @param message message
     */
    ResultEnum(Integer code, String name, String message) {
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
    public static ResultEnum get(Integer code) {
        ResultEnum[] values = ResultEnum.values();
        ResultEnum v = null;
        for (ResultEnum value : values) {
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
    public static ResultEnum get(String name) {
        ResultEnum[] values = ResultEnum.values();
        ResultEnum v = null;
        for (ResultEnum value : values) {
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
