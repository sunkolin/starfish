package com.starfish.core.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.util.Map;

/**
 * SecurityUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-06
 */
@Slf4j
public class SecurityUtilTest {

    @Test
    public void encodeBase64Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeBase64(data.getBytes());
        Assert.assertEquals("amFja3Nvbg==", result);
    }

    @Test
    public void decodeBase64Test() {
        String data = "amFja3Nvbg==";
        String result = new String(SecurityUtil.decodeBase64(data));
        Assert.assertEquals("jackson", result);
    }

    @Test
    public void encodeHexTest() {
        String data = "jackson";
        String result = SecurityUtil.encodeHex(data.getBytes());
        Assert.assertEquals("6a61636b736f6e", result);
    }

    @Test
    public void decodeHexTest() {
        String data = "6a61636b736f6e";
        String result = new String(SecurityUtil.decodeHex(data));
        Assert.assertEquals("jackson", result);
    }

    @Test
    public void encodeMd5Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeMd5(data);
        Assert.assertEquals("b41779690b83f182acc67d6388c7bac9", result);
    }

    @Test
    public void encodeSha1Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeSha1(data);
        Assert.assertEquals("f732dfdbd0aed62727f958cccca9ec3a5cb13eda", result);
    }

    @Test
    public void encodeSha256Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeSha256(data);
        Assert.assertEquals("dc355ec75a2dc4a1d29582933b52f9f2ed71061432d72e1991d8b15445b2ff03", result);
    }

    @Test
    public void encodeSha512Test() {
        String data = "jackson";
        String result = SecurityUtil.encodeSha512(data);
        Assert.assertEquals("64bb0ae3efdd054aff98d1c04432967b8e5bde522feffc55f85234b27990a8fa8d2cc553e7af34595e8c48de97768e929a15c2a183657c42c172e0f9269c95ec", result);
    }


    @Test
    public void desTest() {
        String data = "jackson";
        SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue());
        String key = SecurityUtil.encodeBase64(secretKey.getEncoded());
        String tmp = SecurityUtil.encodeDes(data, key);
        String result = SecurityUtil.decodeDes(tmp, key);
        Assert.assertEquals(data, result);
    }

    @Test
    public void aesTest() {
        String data = "jackson";
        SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue());
        String base64Key = SecurityUtil.encodeBase64(secretKey.getEncoded());
        String encodeAesResult = SecurityUtil.encodeAes(data, base64Key);
        String decodeAesResult = SecurityUtil.decodeAes(encodeAesResult, base64Key);
        Assert.assertEquals(data, decodeAesResult);
    }

    @Test
    public void rsaTest() {
        String data = "jackson";
        Map<String, String> keys = SecurityUtil.createRsaKeyPair();
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
        String base64PublicKey = SecurityUtil.encodeBase64(keyPair.getPublic().getEncoded());
        String base64PrivateKey = SecurityUtil.encodeBase64(keyPair.getPrivate().getEncoded());
        log.info("base64PublicKey:{}", base64PublicKey);
        log.info("base64PrivateKey:{}", base64PrivateKey);
        // 公钥加密私钥解密
        String encodeRsaResult = SecurityUtil.encodeRsa(data, base64PrivateKey, base64PublicKey, KeyType.PublicKey);
        String decodeRsaResult = SecurityUtil.decodeRsa(encodeRsaResult, base64PrivateKey, base64PublicKey, KeyType.PrivateKey);

        // 私钥加密公钥解密
        String encodeRsaResult2 = SecurityUtil.encodeRsa(data, base64PrivateKey, base64PublicKey, KeyType.PrivateKey);
        String decodeRsaResult2 = SecurityUtil.decodeRsa(encodeRsaResult2, base64PrivateKey, base64PublicKey, KeyType.PublicKey);

        Assert.assertEquals(data, decodeRsaResult);
    }

}