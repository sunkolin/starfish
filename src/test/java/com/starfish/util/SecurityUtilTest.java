package com.starfish.util;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * SecurityUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-06
 */
public class SecurityUtilTest {

    @Test
    public void encodeBase64Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeBase64(data.getBytes());
        assertEquals("amFja3Nvbg==", result);
    }

    @Test
    public void decodeBase64Test() {
        String data = "amFja3Nvbg==";
        String result = new String(SecurityUtil.decodeBase64(data));
        assertEquals("jackson", result);
    }

    @Test
    public void encodeHexTest() {
        String data = "jackson";
        String result = SecurityUtil.encodeHex(data.getBytes());
        assertEquals("6a61636b736f6e", result);
    }

    @Test
    public void decodeHexTest() {
        String data = "6a61636b736f6e";
        String result = new String(SecurityUtil.decodeHex(data));
        assertEquals("jackson", result);
    }

    @Test
    public void encodeMd5Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeMd5(data);
        assertEquals("b41779690b83f182acc67d6388c7bac9", result);
    }

    @Test
    public void encodeSha1Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeSha1(data);
        assertEquals("f732dfdbd0aed62727f958cccca9ec3a5cb13eda", result);
    }

    @Test
    public void encodeSha256Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeSha256(data);
        assertEquals("dc355ec75a2dc4a1d29582933b52f9f2ed71061432d72e1991d8b15445b2ff03", result);
    }

    @Test
    public void encodeSha512Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeSha512(data);
        assertEquals("64bb0ae3efdd054aff98d1c04432967b8e5bde522feffc55f85234b27990a8fa8d2cc553e7af34595e8c48de97768e929a15c2a183657c42c172e0f9269c95ec", result);
    }


    @Test
    public void desTest() {
        String data = "jackson";
        String key = "1234567890";
        String tmp = SecurityUtil.encodeDes(data, key);
        String result = SecurityUtil.decodeDes(tmp, key);
        assertEquals(data, result);
    }

    @Test
    public void aesTest() {
        String data = "jackson";
        String key = "1234567890";
        String tmp = SecurityUtil.encodeAes(data, key);
        String result = SecurityUtil.decodeAes(tmp, key);
        assertEquals(data, result);
    }

    @Test
    public void rsaTest() {
        String data = "jackson";
        Map<String, String> keys = SecurityUtil.createRsaKeyPair();
        String tmp = SecurityUtil.encodeRsa(data, keys.get("PublicKey"));
        String result = SecurityUtil.decodeRsa(tmp, keys.get("PrivateKey"));
        assertEquals(data, result);
    }

}