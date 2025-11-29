package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * IpUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-21
 */
@Slf4j
class IpUtilTest {

    @Disabled("Network does not work")
    @Test
    void getPublicAddressTest() {
        String result = IpUtil.getPublicNetworkIp();
        log.info(result);
        Assertions.assertNotNull(result);
    }

    @Test
    void getAddress() {
        String result = IpUtil.getAddress("203.119.241.126");
        Assertions.assertEquals("中国 广东 深圳", result);
    }

}
