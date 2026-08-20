package com.starfish.core;

import com.google.common.base.Strings;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DecryptConfigUtil
 * 国密4加密配置，使用国密4解密
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-07-22
 */
public class DecryptConfigUtil {

    public static void decrypt(String key, ConfigurableEnvironment environment) {
        decrypt(null, key, environment);
    }

    /**
     * 解密配置并应用
     *
     * @param enabledKeys 启用解密的keys
     * @param key         需要解密的key
     * @param environment 环境
     *
     */
    public static void decrypt(List<String> enabledKeys, String key, ConfigurableEnvironment environment) {
        try {
            Binder binder = Binder.get(environment);
            Boolean enabled = enabled(enabledKeys, binder);

            // 如果未启用，不需要解密，直接返回
            if (!enabled) {
                return;
            }

            // 解密，如果解密后数据为空，直接返回
            String decryptValue = decrypt(key, binder);
            if (Strings.isNullOrEmpty(decryptValue)) {
                return;
            }

            // 应用
            apply(key, decryptValue, environment);
        } catch (Throwable e) {
            System.out.println("配置解密失败，key是" + key + "异常是" + e.getMessage());
        }
    }

    private static Boolean enabled(List<String> enabledKeys, Binder binder) {
        // 如果没有启用条件key，直接启用
        if (enabledKeys == null || enabledKeys.isEmpty()) {
            return true;
        }

        // 所有条件都为true才启用，遍历所有的条件，如果都不是false，则启用返回true
        for (String enabledKey : enabledKeys) {
            Boolean enabled = binder.bind(enabledKey, Boolean.class).orElse(false);
            if (enabled == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * 解密
     *
     * @param key    键
     * @param binder binder
     * @return 值
     */
    private static String decrypt(String key, Binder binder) {
        String value = binder.bind(key, String.class).orElse("");
        if (Strings.isNullOrEmpty(value)) {
            return "";
        }
        try {
            String v = Sm4Util.decrypt(value, "2WSX#edc4RFV%tgb");

            if (!Strings.isNullOrEmpty(v)) {
                return v;
            }

            return v.trim();
        } catch (Throwable e) {
            System.out.println("配置解密失败，key=" + key + "异常是" + e.getMessage());
        }

        return "";
    }

    /**
     * 应用解密配置
     *
     * @param key         键
     * @param value       值
     * @param environment 环境
     */
    private static void apply(String key, String value, ConfigurableEnvironment environment) {
        try {
            if (Strings.isNullOrEmpty(value)) {
                System.out.println("解密数据为空字符串，key=" + key);
                return;
            }

            // 解密成功并且不为空才覆盖配置
            Map<String, Object> decryptedMap = new HashMap<>();
            decryptedMap.put(key, value);

            // 将解密后的配置放到最高优先级，全局生效;
            // 将key中的点号换成-加上-property-source作为propertySourceName
            String propertySourceName = key.replace(".", "-") + "-property-source";
            environment.getPropertySources().addFirst(new MapPropertySource(propertySourceName, decryptedMap));
        } catch (Throwable e) {
            System.out.println("配置解密失败，异常是" + e.getMessage());
        }
    }

}
