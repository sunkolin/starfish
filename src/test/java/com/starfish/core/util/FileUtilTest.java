package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void readTest() throws IOException {
        String result = FileUtil.readString("classpath:application-starfish.properties");
        log.info("result={}", result);
        Assertions.assertNotNull(result);

        String result2 = FileUtil.readString("file:/etc/profile");
        log.info("result2={}", result2);
        Assertions.assertNotNull(result2);

        String result3 = FileUtil.readString("/etc/profile");
        log.info("result3={}", result3);
        Assertions.assertNotNull(result3);
    }

    @Test
    void readLinesTest() throws IOException {
        List<String> result = FileUtil.readLines("classpath:application-starfish.properties");
        Assertions.assertFalse(result.isEmpty());

        List<String> result2 = FileUtil.readLines("file:/etc/profile");
        System.out.println(result2);
        Assertions.assertFalse(result2.isEmpty());
    }

//    @Test
//    void getMultipartFile() throws IOException {
//        MultipartFile f = FileUtil.getMultipartFile("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
//        log.info(f.getName());
//    }

//    @Test
//    void getFile() throws IOException {
//        String fileName = StringUtil.random(16);
//        File file = new File("./.tmp/" + fileName + ".png");
//        FileUtil.getFile("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png", file);
//    }

}
