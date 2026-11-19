package com.starfish.common.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RustFSProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-11-19
 */
@Data
@ConfigurationProperties(prefix = "starfish.rustfs")
public class RustFSProperties {

    private boolean enabled = false;

    private String endpoint = "http://127.0.0.1:9000";

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private int expire;

}