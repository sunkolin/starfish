package com.starfish.experiment.text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * 文本文件编辑器工具类。
 * <p>
 * 提供文件内容的查找与替换功能，支持对单个文件或整个目录（含子目录）进行批量文本替换。
 * 仅处理受支持的文本文件类型，具体类型见 {@link #supportedFileTypes}。
 * </p>
 */
public class TextFileEditor {

    /**
     * 支持的文本文件扩展名列表。
     * <p>
     * 当对目录进行批量替换时，仅会处理扩展名在此列表中的文件，
     * 其他类型的文件（如图片、二进制文件等）将被自动跳过。
     * </p>
     * <p>
     * 包含以下类型：
     * <ul>
     *   <li>普通文本：.txt</li>
     *   <li>Markdown：.md</li>
     *   <li>JSON 配置：.json</li>
     *   <li>XML：.xml</li>
     *   <li>YAML 配置：.yaml, .yml</li>
     *   <li>配置文件：.ini</li>
     *   <li>程序配置：.conf, .config</li>
     *   <li>日志文件：.log</li>
     *   <li>逗号分隔表格：.csv</li>
     *   <li>SQL 脚本：.sql</li>
     *   <li>Shell 脚本：.sh</li>
     *   <li>Windows 批处理：.bat</li>
     *   <li>源代码：.py, .go, .java, .js, .html, .css</li>
     *   <li>Java 配置：.properties</li>
     *   <li>Git 忽略配置：.gitignore</li>
     * </ul>
     */
    public static final List<String> supportedFileTypes = List.of(".txt", ".md", ".json", ".xml", ".yaml", ".yml", ".ini", ".conf", ".config", ".log", ".csv", ".sql", ".sh", ".bat", ".py", ".go", ".java", ".js", ".html", ".css", ".properties", ".gitignore");

    /**
     * 替换文件或目录中的文本内容。
     * <p>
     * 如果 filePath 指向一个目录，则会递归遍历该目录及其所有子目录下的
     * 所有受支持类型的文件，将 oldText 全部替换为 newText。
     * 如果 filePath 指向一个文件，则仅对该文件执行替换操作。
     * </p>
     *
     * @param filePath  目标文件或目录的路径
     * @param oldText 要被替换的原字符串
     * @param newText 替换后的新字符串
     */
    public static void replaceText(String filePath, String oldText, String newText) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.err.println("Path does not exist: " + filePath);
            return;
        }

        try {
            if (Files.isDirectory(path)) {
                replaceTextInDirectory(path, oldText, newText);
            } else if (Files.isRegularFile(path)) {
                replaceTextInFile(path, oldText, newText);
            }
        } catch (IOException e) {
            System.err.println("Error processing path: " + filePath + " - " + e.getMessage());
        }
    }

    /**
     * 递归替换目录下所有受支持类型文件中的文本内容。
     * <p>
     * 使用 {@link Files#walk} 递归遍历目录，
     * 对每个受支持类型的文件调用 {@link #replaceTextInFile} 进行替换。
     * 单个文件处理失败不会中断整个批处理流程。
     * </p>
     *
     * @param directory 目标目录路径
     * @param oldText 要被替换的原字符串
     * @param newText 替换后的新字符串
     * @throws IOException 如果遍历目录时发生 I/O 错误
     */
    private static void replaceTextInDirectory(Path directory, String oldText, String newText) throws IOException {
        try (Stream<Path> walk = Files.walk(directory)) {
            walk.filter(Files::isRegularFile).filter(TextFileEditor::isSupportedFileType).forEach(file -> {
                try {
                    replaceTextInFile(file, oldText, newText);
                } catch (IOException e) {
                    System.err.println("Error processing file: " + file + " - " + e.getMessage());
                }
            });
        }
    }

    /**
     * 替换单个文件中的文本内容。
     * <p>
     * 读取文件全部内容，若包含 oldText 则执行替换并写回文件。
     * 若文件内容中不包含 oldText，则不进行任何写回操作，以避免不必要的 I/O。
     * </p>
     *
     * @param file      目标文件路径
     * @param oldText 要被替换的原字符串
     * @param newText 替换后的新字符串
     * @throws IOException 如果读取或写入文件时发生 I/O 错误
     */
    private static void replaceTextInFile(Path file, String oldText, String newText) throws IOException {
        String content = Files.readString(file);
        if (content.contains(oldText)) {
            String newContent = content.replace(oldText, newText);
            Files.writeString(file, newContent);
            System.out.println("Updated file: " + file);
        }
    }

    /**
     * 判断文件是否属于受支持的文本文件类型。
     * <p>
     * 通过比较文件扩展名（不区分大小写）与 {@link #supportedFileTypes} 列表进行判断。
     * </p>
     *
     * @param file 待判断的文件路径
     * @return 如果文件扩展名在支持列表中则返回 {@code true}，否则返回 {@code false}
     */
    private static boolean isSupportedFileType(Path file) {
        String fileName = file.getFileName().toString().toLowerCase();
        return supportedFileTypes.stream().anyMatch(fileName::endsWith);
    }

}