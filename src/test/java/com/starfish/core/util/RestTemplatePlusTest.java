package com.starfish.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RestTemplatePlusTest {

    @Disabled("Network does not work")
    @Test
    void exchangeSkipSsl() {
        ResponseEntity<String> entity = RestTemplatePlus.exchangeSkipSsl("https://www.baidu.com/sugrec", HttpMethod.GET, null, null, null, String.class);
        Assertions.assertEquals(HttpStatus.OK,entity.getStatusCode());
    }

}