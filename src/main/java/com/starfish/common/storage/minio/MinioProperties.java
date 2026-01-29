package com.starfish.common.storage.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MinioProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-29
 */
@Data
@ConfigurationProperties(prefix = "starfish.s")
public class MinioProperties {

    private boolean enabled = false;

    private String endpoint = "http://127.0.0.1:9000";

    private String accessKey;

    private String secretKey;

    private String bucket;

    private int expire;

}