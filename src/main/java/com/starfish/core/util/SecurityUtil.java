package com.starfish.core.util;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.exception.CustomException;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * SecurityUtil
 * 编码和加解密工具类
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-06
 */
@SuppressWarnings("unused")
public final class SecurityUtil {

    public static final String ALGORITHM_MD5 = "MD5";

    public static final String ALGORITHM_SHA1 = "SHA-1";

    public static final String ALGORITHM_SHA256 = "SHA-256";

    public static final String ALGORITHM_SHA512 = "SHA-512";

    public static final String ALGORITHM_DES = "DES";

    public static final String ALGORITHM_AES = "AES/GCM/NoPadding";

    public static final String ALGORITHM_RSA = "RSA";

    private SecurityUtil() {

    }

    /**
     * encode base64,generally,the result may be convert to String
     *
     * @param data data data
     * @return return
     */
    public static String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * encode base64,generally,the result may be convert to String
     *
     * @param data data data
     * @return return
     */
    public static String encodeBase64(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    /**
     * decode base64 string
     *
     * @param data data data
     * @return return
     */
    public static byte[] decodeBase64String(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static byte[] decodeBase64(String data) {
        return Base64.getDecoder().decode(data.getBytes());
    }

    /**
     * encode hex,generally,the result may be convert to String
     *
     * @param data data
     * @return return
     */
    public static String encodeHex(byte[] data) {
        return HexUtil.encodeHexStr(data);
    }

    /**
     * decode hex
     *
     * @param data data
     * @return return
     */
    public static byte[] decodeHex(String data) {
        try {
            return HexUtil.decodeHex(data);
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "decode hex exception");
        }
    }

    /**
     * md5 encode,generally,the result may be convert to String
     *
     * @param data data
     * @return return
     */
    public static String encodeMd5(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_MD5);
            return encodeHex(digest.digest(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode md5 exception");
        }
    }

    /**
     * 消息摘要算法的一种，被广泛认可的MD5算法的继任者.
     * SHA算法家族除了其代表SHA-1算法以外，还有SHA-224、SHA-256、SHA-384
     * 和SHA-512四种SHA算法的变体，以其摘要信息字节长度不同而命名
     * ，通常将这组算法并称为SHA-2算法。摘要信息字节长度的差异是SHA-2和SHA-1算法的最大差异。
     *
     * @param data data
     * @return return
     */
    public static String encodeSha1(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA1);
            return encodeHex(digest.digest(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode sha1 exception");
        }
    }

    public static String encodeSha256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA256);
            return encodeHex(digest.digest(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode sha256 exception");
        }
    }

    public static String encodeSha512(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA512);
            return encodeHex(digest.digest(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode sha512 exception");
        }
    }

    /**
     * Data Encryption Standard,数据加密标准，速度较快，适用于加密大量数据的场合,加密后转出16进制
     *
     * @param data      data
     * @param base64Key key length must greater than 8
     * @return return
     */
    public static String encodeDes(String data, String base64Key) {
        byte[] key = decodeBase64(base64Key);
        return SecureUtil.des(key).encryptBase64(data);
    }

    /**
     * DES
     *
     * @param data      data
     * @param base64Key base64Key
     * @return return
     */
    public static String decodeDes(String data, String base64Key) {
        byte[] key = decodeBase64(base64Key);
        return SecureUtil.des(key).decryptStr(data);
    }

    /**
     * Advanced Encryption Standard,下一代的加密算法标准,加密后转出16进制
     *
     * @param data      data
     * @param base64Key base64Key
     * @return return
     */
    public static String encodeAes(String data, String base64Key) {
        byte[] key = decodeBase64(base64Key);
        return SecureUtil.aes(key).encryptHex(data);
    }

    /**
     * decode aes
     *
     * @param data      data
     * @param base64Key base64Key
     * @return return
     */
    public static String decodeAes(String data, String base64Key) {
        byte[] key = decodeBase64(base64Key);
        return SecureUtil.aes(key).decryptStr(data);
    }

    /**
     * 生成rsa非对称密钥的公钥和私钥，据说不能在ios中使用。可以使用openssl替换。
     *
     * @return 公钥和私钥
     */
    public static Map<String, String> createRsaKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            keyPairGen.initialize(2048);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            HashMap<String, String> result = new HashMap<>(20);
            result.put("PublicKey", encodeBase64(publicKey.getEncoded()));
            result.put("PrivateKey", encodeBase64(privateKey.getEncoded()));
            return result;
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "create rsa key pair exception");
        }
    }

    /**
     * encode rsa
     *
     * @param data             data
     * @param base64PrivateKey base64PrivateKey
     * @param base64PublicKey  base64PublicKey
     * @return return
     */
    public static String encodeRsa(String data, String base64PrivateKey, String base64PublicKey, KeyType keyType) {
        return SecureUtil.rsa(base64PrivateKey, base64PublicKey).encryptHex(data, keyType);
    }

    /**
     * encode rsa
     *
     * @param data             data
     * @param base64PrivateKey base64PrivateKey
     * @param base64PublicKey  base64PublicKey
     * @return return
     */
    public static String decodeRsa(String data, String base64PrivateKey, String base64PublicKey, KeyType keyType) {
        return SecureUtil.rsa(base64PrivateKey, base64PublicKey).decryptStr(data, keyType);
    }

    protected static int toDigit(final char ch, final int index) {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

}