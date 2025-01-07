package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * CommonUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-21
 */
@Slf4j
class CommonUtilTest {

    @Disabled("Network does not work")
    @Test
    void getPublicAddressTest() {
        String result = CommonUtil.getPublicInternetProtocolAddress();
        log.info(result);
        Assertions.assertNotNull(result);
    }

}
