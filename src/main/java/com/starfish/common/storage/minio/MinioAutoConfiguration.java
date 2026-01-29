package com.starfish.common.storage.minio;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * MinioAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-29
 */
@AutoConfiguration
@ConditionalOnProperty(value = {"starfish.minio.enabled"}, havingValue = "true")
@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {

    @Bean("minioClient")
    public MinioClient minioClient(MinioProperties minioProperties) {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @Bean("minioService")
    public MinioService minioClient(MinioClient minioClient, MinioProperties minioProperties) {
        return new MinioService(minioClient, minioProperties);
    }

}
