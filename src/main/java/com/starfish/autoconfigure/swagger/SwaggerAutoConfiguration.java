package com.starfish.autoconfigure.swagger;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.assertj.core.util.Strings;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;

/**
 * SwaggerConfig TODO
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2018-03-05
 */
@AutoConfiguration
@Profile({"dev", "test"})
@ConditionalOnProperty(value = {"application.swagger.enabled"}, matchIfMissing = true)
@EnableConfigurationProperties({SwaggerProperties.class})
@EnableSwagger2
public class SwaggerAutoConfiguration {

    @Resource
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        // 处理多个包路径
        String basePackage = swaggerProperties.getBasePackage();
        List<String> basePackageList = Splitter.on(",").splitToList(basePackage);
        Predicate<RequestHandler> predicate = requestHandler -> false;
        for (String item : basePackageList) {
            if (Strings.isNullOrEmpty(item)) {
                continue;
            }
            Predicate<RequestHandler> p = RequestHandlerSelectors.basePackage(item);
            predicate = predicate.or(p);
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .protocols(Sets.newHashSet("http", "https"))
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .useDefaultResponseMessages(false)
                .select()
                // 指定controller存放的目录路径
                .apis((com.google.common.base.Predicate<RequestHandler>) predicate)
//                .paths(PathSelectors.ant("/api/v1/*"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title(swaggerProperties.getTitle());
        apiInfoBuilder.description(swaggerProperties.getDescription());
        apiInfoBuilder.version(swaggerProperties.getVersion());
        apiInfoBuilder.license(swaggerProperties.getLicense());
        apiInfoBuilder.licenseUrl(swaggerProperties.getLicenseUrl());

        Contact contact = new Contact(swaggerProperties.getName(), swaggerProperties.getUrl(), swaggerProperties.getEmail());
        apiInfoBuilder.contact(contact);
        return apiInfoBuilder.build();
    }

    @Bean
    public SwaggerInterceptor newSwaggerInterceptor() {
        SwaggerInterceptor swaggerInterceptor = new SwaggerInterceptor();
        swaggerInterceptor.setEnabled(swaggerProperties.getEnabled());
        return swaggerInterceptor;
    }

}
