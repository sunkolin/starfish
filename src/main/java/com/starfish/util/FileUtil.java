package com.starfish.util;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * FileUtil
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-05
 */
@Slf4j
@SuppressWarnings(value = "unused")
public final class FileUtil {

    /**
     * http前缀
     */
    public static final String HTTP_PREFIX = "http";

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
     * @param fileName      文件名，例如123.txt，https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png等
     * @param extensionSign 是否带拓展名，true带，false不带
     * @return 结果 123，123.txt
     */
    public static String getFileName(String fileName, boolean extensionSign) {
        // http 或者https
        if (fileName.startsWith(HTTP_PREFIX)) {
            fileName = getUrlFileName(fileName);
        }

        if (!extensionSign) {
            fileName = getFileName(fileName);
        }

        return fileName;
    }

    /**
     * 获取文件名，不带后缀
     *
     * @param fileName 文件名，例如123.txt
     * @return 结果
     */
    private static String getFileName(String fileName) {
        int index = fileName.lastIndexOf(Constant.DOT);
        return fileName.substring(0, index);
    }

    /**
     * 获取文件名
     *
     * @param url，链接，例如https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png
     * @return 结果，PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png
     */
    private static String getUrlFileName(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            //设置应用程序要从网络链接读取数据
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();

            // 获取文件名
            String fileName;
            String attachmentFileName = connection.getHeaderField("attachment; filename=");
            if (!Strings.isNullOrEmpty(attachmentFileName)) {
                fileName = attachmentFileName;
            } else {
                fileName = url.substring(url.lastIndexOf('/') + 1);
            }
            return fileName;
        } catch (Exception e) {
            throw new CustomException(ResultEnum.GET_FILE_NAME_EXCEPTION);
        }
    }

    /**
     * 更换文件拓展格式
     *
     * @param sourceName 源文件名
     * @param extension  拓展名，必须以点开头
     * @return 结果
     */
    public static String changeExtension(String sourceName, String extension) {
        String targetName = getFileName(sourceName);

        // 如果没有写点，加上点
        if (!extension.startsWith(Constant.DOT)) {
            extension = Constant.DOT + extension;
        }

        targetName = targetName + extension;

        return targetName;
    }

    /**
     * 随机生成一个文件名
     *
     * @param sourceNameOrExtension 源文件名称或拓展名，必须包含点
     * @return 结果
     */
    public static String randomName(String sourceNameOrExtension) {
        String fileName = getRandomNameString();
        String extension = getExtension(sourceNameOrExtension);
        return fileName + extension;
    }

    /**
     * 获取一个24位字符串名称，由年月日时分秒加随机字符串组成
     *
     * @return 结果
     */
    private static String getRandomNameString() {
        // 第一段：14位，yyyyMMddHHmmss
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String id1 = format.format(new Date());

//        //第二段：3位随机数字
//        String id2 = StringUtil.randomString(3);

        //第三段：12位随机字符
        String id2 = StringUtil.random("abcdefghijklmnopqrstuvwxyz", 10);

        return id1 + id2;
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
     * 获取文件拓展名
     *
     * @param name 文件名称
     * @return 文件类型
     */
    public static String getExtension(String name) {
        // 校验文件名称
        if (Strings.isNullOrEmpty(name)) {
            throw new CustomException(ResultEnum.FILE_TYPE_ERROR);
        }

        int index = name.lastIndexOf(Constant.DOT);
        return name.substring(index).toLowerCase();
    }

    /**
     * File转MultipartFile
     *
     * @param file 文件
     * @return 结果
     * @throws IOException 异常
     */
    public static MultipartFile toMultipartFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile(file.getName(), file.getName(), "application/octet-stream", fileInputStream);
    }

    /**
     * url转MultipartFile
     *
     * @param url 链接地址，例如https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png
     * @return 结果
     * @throws IOException 异常
     */
    public static MultipartFile toMultipartFile(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setReadTimeout(3000);
        connection.setConnectTimeout(3000);
        //设置应用程序要从网络链接读取数据
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();

        // 获取文件名
        String fileName = getFileName(url, true);
        return new MockMultipartFile(fileName, fileName, "application/octet-stream", inputStream);
    }

    public static void main(String[] args) throws IOException {
        MultipartFile f = toMultipartFile("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        System.out.println(f.getName());

        File target = new File("/Users/sunlin/tmp/" + f.getName());
        f.transferTo(target);
    }

}
