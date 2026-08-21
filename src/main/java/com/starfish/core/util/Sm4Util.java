package com.starfish.core.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class Sm4Util {

    /**
     * 默认国密4密钥
     */
    public static final String KEY = "jYsoqkOFZZKXIVotxV1YxA";

    public static String encrypt(String password) {
        return encrypt(password, KEY);
    }

    public static String encrypt(String password, String key) {
        byte[] base64Key = Base64.decode(key);
        SymmetricCrypto sm4 = SmUtil.sm4(base64Key);
        return sm4.encryptHex(password);
    }

    public static String decrypt(String password) {
        return decrypt(password, KEY);
    }

    public static String decrypt(String password, String key) {
        byte[] base64Key = Base64.decode(key);
        SymmetricCrypto sm4 = SmUtil.sm4(base64Key);
        return sm4.decryptStr(password, CharsetUtil.CHARSET_UTF_8);
    }

}






