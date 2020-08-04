package com.starfish.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * CompressUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-03-24
 */
@Slf4j
public class CompressUtilTest {

    public static String path;

    @Before
    public void before() {
        path = SystemUtil.getBasePath();
    }

    @Test
    public void test() throws IOException {
        File f1 = new File(path + "tmp/美女.zip");
        File f2 = new File(path + "tmp/美女/");
        File f3 = new File(path + "tmp/解压后压缩美女文件.zip");

        FileUtil.delete(path + "tmp/美女.zip");
        FileUtil.delete(path + "tmp/美女/");
        FileUtil.delete(path + "tmp/解压后压缩美女文件.zip");

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource rs[] = resolver.getResources("classpath*:/**/美女.zip");
        if (rs != null && rs.length == 1) {
            File file = rs[0].getFile();
            FileCopyUtils.copy(file, f1);
        } else {
            log.info("未找到文件美女.zip");
        }

        CompressUtil.decompress(path + "tmp/美女.zip", path + "tmp/美女/");
        CompressUtil.compress(path + "tmp/美女/", path + "tmp/解压后压缩美女文件.zip");
        CompressUtil.compress(path + "tmp/美女/可爱.jpg", path + "tmp/可爱.zip");
        assertTrue(new File(path + "tmp/可爱.zip").exists());
    }

}
