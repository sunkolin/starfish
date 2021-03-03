package com.starfish.util;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.starfish.enumeration.ResultEnum;
import com.starfish.constant.Constant;
import com.starfish.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * FileUtil
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-05
 */
@SuppressWarnings(value = "unused")
@Slf4j
public final class FileUtil {

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("file-util-thread-pool-factory").build();

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), NAMED_THREAD_FACTORY);

    /**
     * 获取文件大小
     *
     * @param path 路径
     * @return 结果
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        return file.length();
    }

    /**
     * 删除文件或目录
     *
     * @param path 路径
     */
    public static void delete(String path) {
        File file = new File(path);

        // 判断文件是否存在
        if (!file.exists()) {
            return;
        }

        // 如果是文件，直接删除
        if (file.isFile()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }

        // 如果是文件夹，便利文件夹，删除文件下所有文件，再删除文件夹
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File f : files) {
                delete(f.getAbsolutePath());
            }
        }
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

    /**
     * 加载文件 支持classpath: file: 的地址前缀
     *
     * @param path 路径
     * @return 文件
     * @throws FileNotFoundException 文件不存在异常
     */
    public static File getFile(String path) throws FileNotFoundException {
        return ResourceUtils.getFile(path);
    }

    /**
     * 获取当前工作目录，与getCurrentDir功能一致
     *
     * @return 当前目录路径
     */
    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * 根据new File得到当前目录，与getCurrentDirectory功能相同
     *
     * @return 当前目录路径
     */
    public static String getCurrentDir() {
        File file = new File("");
        return file.getAbsolutePath();
    }

    /**
     * 读取项目下文件的全部内容转成字符串
     * 与FileTool.read()方法功能相同
     *
     * @param path 文件路径
     * @return 文件内容
     * @see FileUtil
     */
    public static String readClassPathFile(String path) {
        String result = "";
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:" + path);
            if (resources.length > 0) {
                EncodedResource enc = new EncodedResource(resources[0], "UTF-8");
                result = FileCopyUtils.copyToString(enc.getReader());
            }
        } catch (IOException e) {
            log.error("error", e);
        }
        return result;
    }

    /**
     * 如果文件是使用数字命名的，获取文件名的数字
     *
     * @param fileName 文件名，格式123.txt
     * @return 结果
     */
    public static int getFileNameNumber(String fileName) {
        return Integer.parseInt(getFileName(fileName));
    }

    /**
     * 获取文件名，不带后缀
     *
     * @param fileName 文件名，例如123.txt
     * @return 结果 123
     */
    public static String getFileName(String fileName) {
        int index = fileName.lastIndexOf(Constant.DOT);
        return fileName.substring(0, index);
    }

    /**
     * 更换文件拓展格式
     *
     * @param sourceName 源文件名
     * @param extension  拓展名
     * @return 结果
     */
    public static String changeName(String sourceName, String extension) {
        String targetName = getFileName(sourceName);

        // 如果没有写点，加上点
        if (!extension.startsWith(Constant.DOT)) {
            extension = Constant.DOT + extension;
        }

        targetName = targetName + extension;

        return targetName;
    }

    /**
     * 读取项目下文件的全部内容转成字符串
     *
     * @param path 文件路径
     * @return 文件内容
     */
    public static String read(String path) {
        //验证参数
        if (path == null) {
            throw new CustomException(ResultEnum.FILE_PATH_IS_EMPTY);
        }

        String result = "";
        try {
            File file = ResourceUtils.getFile(path);
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            EncodedResource encodedResource = new EncodedResource(fileSystemResource, "UTF-8");
            result = FileCopyUtils.copyToString(encodedResource.getReader());
        } catch (IOException e) {
            log.error("read file error", e);
        }
        return result;
    }

    /**
     * 读取项目下文件的全部内容转成字符串
     *
     * @param path 文件路径
     * @return 文件内容
     */
    public static String readString(String path) {
        //验证参数
        if (path == null) {
            throw new CustomException(ResultEnum.FILE_PATH_IS_EMPTY);
        }

        String result = "";
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("read file error", e);
        }
        return result;
    }

    /**
     * 读取项目下文件，按行转成字符串，以行为单位放入list中，适合读取小文件
     * <p>
     * File file = classPathResource.getFile(); 方式无法获取jar包中的文件
     *
     * @param path 文件路径
     * @return 文件内容
     */
    public static List<String> readLines(String path) {
        //验证参数
        if (path == null) {
            throw new CustomException(ResultEnum.FILE_PATH_IS_EMPTY);
        }

        List<String> result = new ArrayList<>();

        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            result = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("read file error", e);
        }
        return result;
    }

    /**
     * 写文件
     *
     * @param file 文件路径，例如/tmp/data.csv
     */
    private static void write(String file, List<String> list) throws IOException {
        Path filePath = Paths.get(file);
        Files.write(filePath, list);
    }

    /**
     * 获取文件类型
     *
     * @param name 文件名称
     * @return 文件类型
     */
    public static String getFileType(String name) {
        int index = name.lastIndexOf(Constant.DOT);
        return name.substring(index).toLowerCase();
    }

    /**
     * 文件分割
     *
     * @param fileName   待拆分的完整文件名，例如/opt/tmp/2017080714245658403aec312.mp4
     * @param targetPath 目标文件夹，例如/opt/tmp/8b3df7067dcb4d0fb27f90b7598163d4/
     * @param size       单位MB，按多少字节大小拆分，例如100
     * @return 拆分成功true，拆分失败false
     */
    public static boolean split(String fileName, String targetPath, int size) {
        List<String> parts = new ArrayList<>();
        File file = new File(fileName);
        int byteSize = 1024 * 1024 * size;
        int count = (int) Math.ceil(file.length() / (double) byteSize);
        int countLen = (count + "").length();

        // 创建目标文件
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            targetFile.mkdirs();
        }

        List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String partFileName = Strings.padStart(String.valueOf(i + 1), countLen, '0') + ".part";
            parts.add(partFileName);
            Future<Boolean> future = EXECUTOR.submit(new SplitCallable(byteSize, i * byteSize, partFileName, file, targetPath));
            futures.add(future);
        }

        // 等待完成
        for (Future<Boolean> future : futures) {
            try {
                Boolean result = future.get();
                if (!result) {
                    return false;
                }
            } catch (Exception e) {
                log.error("split file error,file={},targetPath={},parts={}", fileName, targetPath, JSON.toJSONString(parts), e);
                return false;
            }
        }
        log.info("split file success,file={},targetPath={},parts={}", fileName, targetPath, JSON.toJSONString(parts));

        return true;
    }

    private static class SplitCallable implements Callable<Boolean> {

        int byteSize;
        String partFileName;
        File originFile;
        int startPos;
        String targetPath;

        SplitCallable(int byteSize, int startPos, String partFileName, File originFile, String targetPath) {
            this.startPos = startPos;
            this.byteSize = byteSize;
            this.partFileName = partFileName;
            this.originFile = originFile;
            this.targetPath = targetPath;
        }

        @Override
        public Boolean call() {
            RandomAccessFile rFile;
            OutputStream os;
            try {
                rFile = new RandomAccessFile(originFile, "r");
                byte[] b = new byte[byteSize];
                // 移动指针到每“段”开头
                rFile.seek(startPos);
                int s = rFile.read(b);
                os = new FileOutputStream(targetPath + partFileName);
                os.write(b, 0, s);
                os.flush();
                os.close();
                log.info("split file complete.tmpFile={}", originFile.getName());
                return true;
            } catch (IOException e) {
                log.info("split file error.", e);
                return false;
            }
        }
    }

}
