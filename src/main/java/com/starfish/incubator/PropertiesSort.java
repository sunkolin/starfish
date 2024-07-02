package com.starfish.incubator;

import com.starfish.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * PropertiesSort
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2024-07-02
 */
@Slf4j
public class PropertiesSort {

    public static void main(String[] args) {
        PropertiesSort.sort("classpath:application-starfish.properties");
    }

    /**
     * Properties文件内容排序
     *
     * @param filePath 文件路径，例如classpath:application-starfish.properties
     */
    public static void sort(String filePath) {
        List<String> lines = FileUtil.readLines(filePath);

        // 删除空行和注释
        lines.removeIf(line -> line.isEmpty() || line.isBlank() || line.startsWith("#"));

        // 排序
        Collections.sort(lines);

        // 打印
        String currentPrefix = null;
        for (String item : lines) {
            String[] strings = item.split("\\.");
            String prefix = strings[0];
            if (currentPrefix == null) {
                currentPrefix = prefix;
            }
            if (currentPrefix.equalsIgnoreCase(prefix)) {
                System.out.println(item);
            } else {
                System.out.println();
                System.out.println(item);
                currentPrefix = prefix;
            }
        }
    }

}
