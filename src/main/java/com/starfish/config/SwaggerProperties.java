package com.starfish.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.Contact;

/**
 * SwaggerProperties
 *
 * @author sunny
 * @version 1.0.0
 * @since 2018-11-05
 */
@Data
@ConfigurationProperties(prefix = "application.swagger")
public class SwaggerProperties {

    private Boolean enabled = true;

    private String basePackage;

    private String title;

    private String description;

    private String version = "1.0.0";

    private String license = "Apache License2.0";

    private String licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0";

    private  String name;

    private  String url;

    private  String email;

    .title("XXX系统接口服务")
    // 文档描述
                .description("XXX系统API接口文档简要描述")
    // .termsOfServiceUrl("https://github.com/xxx")
                .version("v1")
                .license("Apache License2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("小志", "https://github.com/xxx", "xxx@gmail.com"))
}
