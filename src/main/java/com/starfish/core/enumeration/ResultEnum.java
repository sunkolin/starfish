package com.starfish.core.enumeration;

/**
 * 结果枚举
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2014-07-07
 */
@SuppressWarnings("unused")
public enum ResultEnum {

    /**
     * 自定义错误从1001开始
     */
    SUCCESS(0, "success", "成功"),

    SYSTEM_EXCEPTION(500, "system_exception", "系统异常"),

    PARAM_ERROR(1001, "param_error", "参数错误"),

    DATE_TIME_FORMAT_ERROR(1002, "date_time_format_error", "日期格式有误"),

    PARSE_DATE_TIME_ERROR(1003, "parse_date_time_error", "解析日期异常"),

    FILE_PATH_IS_EMPTY(1004, "file_path_is_empty", "文件路径为空"),

    FILE_ALREADY_EXIST(1005, "file_already_exist", "文件已存在"),

    FILE_DOWNLOAD_ERROR(1006, "file_download_error", "下载文件异常"),

    DECOMPRESS_FILE_ERROR(1007, "decompress_file_error", "解压文件异常"),

    COMPRESS_FILE_ERROR(1008, "compress_file_error", "压缩文件异常"),

    CAN_NOT_FIND_METHOD(1009, "can_not_find_method", "找不到方法"),

    FIELD_COUNT_IS_ZERO(1010, "field_count_is_zero", "当前对象中没有任何属性值"),

    FILE_TYPE_ERROR(1011, "file_type_error", "文件类型错误"),

    SAVE_FILE_EXCEPTION(1012, "save_file_exception", "保存文件异常"),

    GET_WEATHER_EXCEPTION(1013, "get_weather_exception", "查询天气异常"),

    GET_FILE_NAME_EXCEPTION(1014, "get_file_name_exception", "获取文件名异常"),

    NO_READ_PERMISSION(1015, "no_read_permission", "没有读权限"),

    NO_WRITE_PERMISSION(1016, "no_write_permission", "没有写权限"),

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
     * ResultEnum
     *
     * @param code        code
     * @param englishCode englishCode
     * @param name        name
     * @param message     message
     */
    ResultEnum(Integer code, String englishCode, String name, String message) {
        this.code = code;
        this.englishCode = englishCode;
        this.name = name;
        this.message = message;
    }

    /**
     * ResultEnum
     *
     * @param code        code
     * @param englishCode englishCode
     * @param message     message
     */
    ResultEnum(Integer code, String englishCode, String message) {
        this.code = code;
        this.englishCode = englishCode;
        this.name = "";
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
     * @param englishCode englishCode
     * @return the enum
     */
    public static ResultEnum get(String englishCode) {
        ResultEnum[] values = ResultEnum.values();
        ResultEnum v = null;
        for (ResultEnum value : values) {
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
