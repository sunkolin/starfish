package com.starfish.common.document;

import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * SpringDocAutoConfiguration
 * 无需单独配置，使用spring doc自动配置
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-12
 */
@AutoConfiguration
public class SpringDocAutoConfiguration {

//    @Bean
//    public OpenAPI openAPI() {
//        License license = new License();
//        license.setName("OpenAPI 3.1.0");
//        license.setUrl("https://github.com/OAI/OpenAPI-Specification/blob/3.1.0/versions/3.1.0.md");
//
//        Info info = new Info();
//        info.setTitle("");
//        info.setDescription("");
//        info.setVersion("1.0.0");
//        info.setLicense(license);
//
//        ExternalDocumentation externalDocumentation = new ExternalDocumentation();
//        externalDocumentation.setUrl("");
//        externalDocumentation.setDescription("");
//
//        OpenAPI openApi = new OpenAPI();
//        openApi.setInfo(info);
//        openApi.setExternalDocs(externalDocumentation);
//
//        return openApi;
//    }

//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("public")
//                .pathsToMatch("/**")
//                .pathsToExclude("/admin/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("admin")
//                .pathsToMatch("/admin/**")
//                .build();
//    }

}
