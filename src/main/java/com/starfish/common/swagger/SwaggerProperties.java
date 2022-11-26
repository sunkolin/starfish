package com.starfish.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SwaggerProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2018-11-05
 */
@Data
@ConfigurationProperties(prefix = "application.swagger")
public class SwaggerProperties {

    /**
     * 是否启用,true启用，false禁用
     */
    private Boolean enabled = true;

    /**
     * 包路径，多个逗号分割
     */
    private String basePackage = "com.starfish.controller";

    /**
     * 文档标题
     */
    private String title = "xxx服务接口文档";

    /**
     * 文档描述
     */
    private String description = "xxx服务接口文档";

    /**
     * 版本
     */
    private String version = "1.0.0";

    /**
     * 许可证
     */
    private String license = "Apache License2.0";

    /**
     * 许可证地址
     */
    private String licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0";

    /**
     * 姓名
     */
    private String name = "xxx";

    /**
     * 网站
     */
    private String url = "http://www.xxx.com/";

    /**
     * 邮箱
     */
    private String email = "xxx@qq.com";

}
