package com.starfish.common.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Swagger3AutoConfiguration
 * 与application-starfish.properties两种方式写一个即可
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-12
 */
@AutoConfiguration
public class Swagger3AutoConfiguration {

    @Bean
    public OpenAPI openAPI() {
        License license = new License();
        license.setName("OpenAPI 3.1.0");
        license.setUrl("https://github.com/OAI/OpenAPI-Specification/blob/3.1.0/versions/3.1.0.md");

        Info info = new Info();
        info.setTitle("");
        info.setDescription("");
        info.setVersion("1.0.0");
        info.setLicense(license);

        ExternalDocumentation externalDocumentation = new ExternalDocumentation();
        externalDocumentation.setUrl("");
        externalDocumentation.setDescription("");

        OpenAPI openApi = new OpenAPI();
        openApi.openapi("3.1.0");
        openApi.setInfo(info);
        openApi.setExternalDocs(externalDocumentation);

        return openApi;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .pathsToExclude("/admin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/admin/**")
                .build();
    }

}
