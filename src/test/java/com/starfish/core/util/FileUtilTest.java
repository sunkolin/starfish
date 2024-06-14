package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * FileUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-11-08
 */
@Slf4j
class FileUtilTest {

    @Test
    void readTest() {
        String result = FileUtil.read("classpath:application-starfish.properties");
        Assertions.assertNotNull(result);

        String result2 = FileUtil.read("file:/etc/profile");
        System.out.println(result2);
        Assertions.assertNotNull(result2);
    }

    @Test
    void readLinesTest() {
        List<String> result = FileUtil.readLines("classpath:application-starfish.properties");
        Assertions.assertTrue(result != null && !result.isEmpty());

        List<String> result2 = FileUtil.readLines("file:/etc/profile");
        System.out.println(result2);
        Assertions.assertTrue(result2 != null && !result2.isEmpty());
    }

    @Test
    void getMultipartFile() throws IOException {
        MultipartFile f = FileUtil.getMultipartFile("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        log.info(f.getName());
    }

    @Test
    void getFile() throws IOException {
        String fileName = StringUtil.random(16);
        File file = new File("./.tmp/" + fileName + ".png");
        FileUtil.getFile("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png", file);
    }

}
