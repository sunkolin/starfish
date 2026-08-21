package com.starfish.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Sm4UtilTest {

    @Test
    public void testSm4Util() {
        String encryptValue = Sm4Util.encrypt("123456789");
        System.out.println("encryptValue=" + encryptValue);
        Assertions.assertEquals("61cff9c127cd02b1f188a62f62f82926", encryptValue);
        String decryptValue = Sm4Util.decrypt(encryptValue);
        System.out.println("decryptValue=" + decryptValue);
        Assertions.assertEquals("123456789", decryptValue);
    }

    @Test
    public void testSm4Util2() {
        String encryptValue = Sm4Util.encrypt("123456789", Sm4Util.KEY);
        System.out.println("encryptValue=" + encryptValue);
        Assertions.assertEquals("61cff9c127cd02b1f188a62f62f82926", encryptValue);
        String decryptValue = Sm4Util.decrypt(encryptValue, Sm4Util.KEY);
        System.out.println("decryptValue=" + decryptValue);
        Assertions.assertEquals("123456789", decryptValue);
    }

}
