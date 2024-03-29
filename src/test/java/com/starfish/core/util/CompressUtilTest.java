package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * CompressUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-03-24
 */
@Slf4j
 class CompressUtilTest {

    public static String basePathPrefix;

    @BeforeAll
    public static void before() {
        basePathPrefix = SystemUtil.getBasePathPrefix();
    }

    @Test
     void compressTest() throws IOException {
        String filePath = basePathPrefix + "/tmp/images/favicon.ico";
        if (!new File(filePath).exists()) {
            WebUtil.download("https://www.baidu.com/favicon.ico", filePath);
        }

        // 文件夹压缩
        String sourcePath1 = basePathPrefix + "/tmp/images/";
        String targetPath1 = basePathPrefix + "/tmp/compress/favicon1.zip";
        String compressPath = basePathPrefix + "/tmp/compress/";
        CompressUtil.compress(sourcePath1, targetPath1);
        Assertions.assertTrue(new File(targetPath1).exists());

        // 文件压缩
        String sourcePath2 = basePathPrefix + "/tmp/images/favicon.ico";
        String targetPath2 = basePathPrefix + "/tmp/compress/favicon2.zip";
        CompressUtil.compress(sourcePath2, targetPath2);
        Assertions.assertTrue(new File(targetPath2).exists());

        // 文件解压
        String sourcePath3 = basePathPrefix + "/tmp/compress/favicon2.zip";
        String targetPath3 = basePathPrefix + "/tmp/decompress/";
        String decompressPath = basePathPrefix + "/tmp/decompress/";
        CompressUtil.decompress(sourcePath3, targetPath3);
        Assertions.assertTrue(new File(targetPath3).exists());

        // 删除文件
        FileUtil.delete(compressPath);
        FileUtil.delete(targetPath2);
        FileUtil.delete(decompressPath);
    }

}
