package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
class RestTemplatePlusTest {

    @Disabled("Network does not work")
    @Test
    void exchangeSkipSsl() {
        ResponseEntity<String> entity = RestTemplatePlus.exchangeSkipSsl("https://www.baidu.com/sugrec", HttpMethod.GET, null, null, null, String.class);
        Assertions.assertEquals(HttpStatus.OK,entity.getStatusCode());
    }

    @Test
    void head() {
        ResponseEntity<Void> entity = RestTemplatePlus.exchange("https://www.baidu.com/sugrec", HttpMethod.HEAD, null, null, null, Void.class);
        log.info("headers={}",entity.getHeaders());
        Assertions.assertEquals(HttpStatus.OK,entity.getStatusCode());
    }

}