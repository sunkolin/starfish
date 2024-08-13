package com.starfish.core.util;

import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.exception.CustomException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * FileUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-05
 */
@Slf4j
@SuppressWarnings("unused")
public final class FileUtil {

    /**
     * http前缀
     */
    public static final String HTTP_PREFIX = "http";

    public static final String FILE_PREFIX = "file:";

    public static final String CLASSPATH_PREFIX = "classpath:";

    public static final String DOT_SYMBOL = ".";

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("file-util-thread-pool-factory").build();

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), NAMED_THREAD_FACTORY);

    private FileUtil() {
        // constructor
    }

    /**
     * 创建文件及父目录
     *
     * @param path 路径
     */
    public static void create(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            Files.createDirectories(file.toPath());
        } else {
            Files.createDirectories(file.getParentFile().toPath());
            Files.createFile(file.toPath());
        }
    }

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
    @SneakyThrows
    public static void delete(String path) {
        // 判断文件是否存在
        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        // 如果是文件，直接删除
        if (file.isFile()) {
            Files.delete(file.toPath());
        } else {
            // 如果是文件夹，便利文件夹，删除文件下所有文件，再删除文件夹
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    delete(f.getAbsolutePath());
                }
            }
            Files.delete(file.toPath());
        }
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
//    public static String readClassPathFile(String path) {
//        String result = "";
//        try {
//            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//            Resource[] resources = resolver.getResources("classpath*:" + path);
//            if (resources.length > 0) {
//                EncodedResource enc = new EncodedResource(resources[0], "UTF-8");
//                result = FileCopyUtils.copyToString(enc.getReader());
//            }
//        } catch (IOException e) {
//            log.error("error", e);
//        }
//        return result;
//    }

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
     * @param fileName      文件名，例如123.txt，<a href="https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png">PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png</a>等
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
        int index = fileName.lastIndexOf(DOT_SYMBOL);
        return fileName.substring(0, index);
    }

    /**
     * 获取文件名
     *
     * @param url，链接，例如<a href="https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png">https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png</a>
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
        if (!extension.startsWith(DOT_SYMBOL)) {
            extension = DOT_SYMBOL + extension;
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
        // 第一段：17位，yyyyMMddHHmmssSSS
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String id1 = format.format(new Date());

        //第二段：7位随机字符
        String id2 = StringUtil.random("abcdefghijklmnopqrstuvwxyz", 7);

        return id1 + id2;
    }

    /**
     * 读取项目下文件,转成字符串,支持格式：/etc/profile,file:/etc/profile,classpath:application-starfish.properties
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException 异常
     */
    public static String readString(String path) throws IOException {
        //验证参数
        if (path == null) {
            throw new CustomException(ResultEnum.FILE_PATH_IS_EMPTY);
        }

        if (path.startsWith(CLASSPATH_PREFIX)) {
            // ClassPathResource创建参数不需要classpath:,如果有自动去掉；此方法可以读取到jar中的文件，不可替换
            path = path.replaceFirst(CLASSPATH_PREFIX, "");
            ClassPathResource resource = new ClassPathResource(path);
            InputStream inputStream = resource.getInputStream();
            return CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } else {
            File file = ResourceUtils.getFile(path);
            return CharStreams.toString(new FileReader(file, StandardCharsets.UTF_8));
        }
    }

    /**
     * 读取项目下文件，按行转成字符串，以行为单位放入list中，适合读取小文件
     * 支持格式：/etc/profile,file:/etc/profile,classpath:application-starfish.properties
     * File file = classPathResource.getFile(); 方式无法获取jar包中的文件
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException 异常
     */
    public static List<String> readLines(String path) throws IOException {
        //验证参数
        if (path == null) {
            throw new CustomException(ResultEnum.FILE_PATH_IS_EMPTY);
        }

        if (path.startsWith(CLASSPATH_PREFIX)) {
            // ClassPathResource创建参数不需要classpath:,如果有自动去掉
            path = path.replaceFirst(CLASSPATH_PREFIX, "");
            ClassPathResource resource = new ClassPathResource(path);
            InputStream inputStream = resource.getInputStream();
            return CharStreams.readLines(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } else {
            File file = ResourceUtils.getFile(path);
            return CharStreams.readLines(new FileReader(file, StandardCharsets.UTF_8));
        }
    }

    /**
     * 写文件
     *
     * @param file 文件路径，例如file:/etc/profile,/etc/profile
     */
    private static void write(String file, List<String> list) throws IOException {
        // 如果有file:前缀需要去掉
        if (file.startsWith(FILE_PREFIX)) {
            file = file.replaceFirst(FILE_PREFIX, "");
        }

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

        int index = name.lastIndexOf(DOT_SYMBOL);
        return name.substring(index).toLowerCase();
    }

//    /**
//     * File转MultipartFile
//     *
//     * @param file 文件
//     * @return 结果
//     * @throws IOException 异常
//     */
//    public static MultipartFile toMultipartFile(File file) throws IOException {
//        FileInputStream fileInputStream = new FileInputStream(file);
//        return new MockMultipartFile(file.getName(), file.getName(), "application/octet-stream", fileInputStream);
//    }

//    /**
//     * url转MultipartFile
//     *
//     * @param url 链接地址，例如https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png
//     * @return 结果
//     * @throws IOException 异常
//     */
//    public static MultipartFile getMultipartFile(String url) throws IOException {
//        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//        connection.setReadTimeout(3000);
//        connection.setConnectTimeout(3000);
//        //设置应用程序要从网络链接读取数据
//        connection.setDoInput(true);
//        connection.setRequestMethod("GET");
//        InputStream inputStream = connection.getInputStream();
//
//        // 获取文件名
//        String fileName = getFileName(url, true);
//        return new MockMultipartFile(fileName, fileName, "application/octet-stream", inputStream);
//    }

//    /**
//     * getFile
//     *
//     * @param url  url
//     * @param file file
//     * @throws IOException IOException
//     */
//    public static void getFile(String url, File file) throws IOException {
//        // 判断文件不存在
//        if (file.isDirectory()){
//            throw new CustomException();
//        }
//        if (file.exists()){
//            throw new CustomException();
//        }
//
//        // 创建父目录和文件
//        File parentFile = file.getParentFile();
//        if(!parentFile.exists()){
//            Files.createDirectories(parentFile.toPath());
//        }
//        Files.createFile(file.toPath());
//
//        MultipartFile multipartFile = getMultipartFile(url);
//        multipartFile.transferTo(file);
//    }

}
