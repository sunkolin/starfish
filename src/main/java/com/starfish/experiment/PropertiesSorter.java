package com.starfish.experiment;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Properties文件排序工具：处理空行、重复key、注释关联，按key字母序重写
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2025-12-17
 */
@Slf4j
public class PropertiesSorter {

    // 存储单个键值对及关联的注释
    static class PropertyEntry {
        // 关联的注释行（按原顺序）
        List<String> comments = new ArrayList<>();
        // 键值对的值
        String value;
    }

    /**
     * 核心方法：执行Properties文件排序
     *
     * @param inputPath  输入文件路径
     * @param outputPath 输出文件路径
     * @throws IOException 文件操作异常
     */
    public static void sortProperties(String inputPath, String outputPath) throws IOException {
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            throw new FileNotFoundException("输入文件不存在：" + inputPath);
        }

        // 读取并解析文件
        Map<String, PropertyEntry> entryMap = readProperties(inputFile);

        // 写入排序后的文件
        File outputFile = new File(outputPath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        writeSortedProperties(outputFile, entryMap);

        log.info("排序完成！输出文件：" + outputFile.getAbsolutePath());
    }

    /**
     * 读取Properties文件，解析注释、键值对，处理空行
     *
     * @param inputFile 输入文件路径
     * @return 键 -> 注释+值的映射
     * @throws IOException 读取文件异常
     */
    private static Map<String, PropertyEntry> readProperties(File inputFile) throws IOException {
        Map<String, PropertyEntry> entryMap = new HashMap<>();
        // 暂存当前未关联的注释行
        List<String> pendingComments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();

                // 处理空行：trim后为空则跳过
                if (trimmedLine.isEmpty()) {
                    continue;
                }

                // 处理注释行（#开头，兼容行首有空格的情况）
                if (trimmedLine.startsWith("#") || trimmedLine.startsWith("!")) {
                    pendingComments.add(line);
                    continue;
                }

                // 处理键值行：解析key和value
                int separatorIndex = findSeparatorIndex(line);
                if (separatorIndex == -1) {
                    // 无分隔符的行（纯key，无值），按key=空处理
                    String key = line.trim();
                    PropertyEntry entry = new PropertyEntry();
                    entry.comments = new ArrayList<>(pendingComments);
                    entry.value = "";
                    entryMap.put(key, entry);
                } else {
                    // 分割key和value（保留value中的分隔符）
                    String key = line.substring(0, separatorIndex).trim();
                    String value = line.substring(separatorIndex + 1).trim();
                    // 处理value中的转义（简单处理，如需完整可参考Properties源码）
                    value = unescapeValue(value);

                    PropertyEntry entry = new PropertyEntry();
                    entry.comments = new ArrayList<>(pendingComments);
                    entry.value = value;
                    // 重复key覆盖
                    entryMap.put(key, entry);
                }

                // 关联注释后清空暂存
                pendingComments.clear();
            }
        }

        return entryMap;
    }

    /**
     * 查找键值行的分隔符位置（=、:、空格/制表符）
     * 参考Properties源码的分隔符解析逻辑
     *
     * @param line 键值行
     * @return 分隔符索引，无则返回-1
     */
    private static int findSeparatorIndex(String line) {
        int len = line.length();
        int index = 0;

        // 跳过键前面的空白字符
        while (index < len && Character.isWhitespace(line.charAt(index))) {
            index++;
        }

        // 遍历找分隔符（=、:、空格/制表符）
        while (index < len) {
            char c = line.charAt(index);
            if (c == '=' || c == ':') {
                return index;
            } else if (Character.isWhitespace(c)) {
                return index;
            }
            index++;
        }

        // 无分隔符（纯key）
        return -1;
    }

    /**
     * 简单处理value的转义字符（如需完整可扩展）
     *
     * @param value 原始value
     * @return 处理后的value
     */
    private static String unescapeValue(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        // 处理常见转义：\=、\:、\n等（简化版）
        return value.replace("\\=", "=").replace("\\:", ":").replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t");
    }

    /**
     * 将排序后的键值对写入目标文件
     *
     * @param outputFile 输出文件路径
     * @param entryMap   键->注释+值的映射
     * @throws IOException 写入文件异常
     */
    private static void writeSortedProperties(File outputFile, Map<String, PropertyEntry> entryMap) throws IOException {
        // 按字母顺序排序key
        List<String> sortedKeys = new ArrayList<>(entryMap.keySet());
        Collections.sort(sortedKeys);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {
            for (String key : sortedKeys) {
                PropertyEntry entry = entryMap.get(key);

                // 先写关联的注释行
                for (String comment : entry.comments) {
                    writer.write(comment);
                    writer.newLine();
                }

                // 写键值对（格式：key=value）
                writer.write(key + "=" + entry.value);
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) {
        // 示例：替换为实际的输入/输出文件路径
        String inputPath = "/Users/sunlin/workspace/starfish/config.properties";
        String outputPath = "/Users/sunlin/workspace/starfish/config_sorted.properties";

        try {
            sortProperties(inputPath, outputPath);
        } catch (IOException e) {
            log.error("处理失败：" + e.getMessage(), e);
        }
    }
}