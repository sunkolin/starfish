package com.starfish.util;

import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import joptsimple.internal.Strings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩解压工具 zip compress
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-03-20
 */
@SuppressWarnings(value = "unused")
@Slf4j
public class CompressUtil {

    /**
     * 压缩
     *
     * @param sourcePath 源文件或者目录的路径
     * @param targetPath 目标文件，要求是以zip结尾的全路径
     */
    public static void compress(String sourcePath, String targetPath) {
        //验证参数
        File file = new File(sourcePath);
        if ((!targetPath.endsWith(".zip") && !targetPath.endsWith(".ZIP"))) {
            throw new CustomException(ResultEnum.COMPRESS_FILE_ERROR);
        }

        //计算 basePath
        String basePath = sourcePath;
        if (file.isFile()) {
            basePath = file.getParent();
        }

        // 遍历文件得到文件字符串list
        List<String> filePaths = traverse(sourcePath);
        List<Entry> fileEntries = new ArrayList<>();
        for (String f : filePaths) {
            Entry entry = new Entry();
            entry.setBasePath(basePath);
            entry.setFilePath(f);
            entry.setRelativePath(calculateRelativePath(basePath, f));
            fileEntries.add(entry);
        }

        // 遍历list获取相对路径，压缩文件
        compress(targetPath, fileEntries);
    }

    /**
     * 解压
     *
     * @param sourcePath 源文件，要求是zip结尾的压缩文件路径
     * @param targetPath 目标文件夹，解压后文件放置目录
     */
    public static void decompress(String sourcePath, String targetPath) {
        //验证参数
        if (!isEndsWithZip(sourcePath) || new File(targetPath).isFile()) {
            throw new CustomException(ResultEnum.PARAM_ERROR);
        }

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(sourcePath, Charset.forName("GBK"));
            Enumeration<? extends ZipEntry> e = zipFile.entries();
            while (e.hasMoreElements()) {
                ZipEntry entry = e.nextElement();
                File f = new File(targetPath, entry.getName());
                //创建完整路径
                if (entry.isDirectory()) {
                    //noinspection ResultOfMethodCallIgnored
                    f.mkdirs();
                    continue;
                } else {
                    //noinspection ResultOfMethodCallIgnored
                    f.getParentFile().mkdirs();
                }
                FileCopyUtils.copy(zipFile.getInputStream(entry), new FileOutputStream(f));
            }
        } catch (IOException e) {
            throw new CustomException(ResultEnum.DECOMPRESS_FILE_ERROR);
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException e) {
                log.error("解压文件异常", e);
            }
        }
    }

    private static void addEntry(ZipOutputStream zos, String filePath, String relativePath) {
        try {
            ZipEntry entry = new ZipEntry(relativePath);
            zos.putNextEntry(entry);
            StreamUtils.copy(new FileInputStream(filePath), zos);
            zos.closeEntry();
        } catch (IOException e) {
            throw new CustomException(ResultEnum.COMPRESS_FILE_ERROR);
        }
    }

    /**
     * 压缩文件列表
     */
    private static void compress(String targetPath, List<Entry> fileEntries) {
        try {
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(targetPath));
            for (Entry entry : fileEntries) {
                addEntry(outputStream, entry.getFilePath(), entry.getRelativePath());
            }
            outputStream.finish();
        } catch (Exception e) {
            throw new CustomException(ResultEnum.COMPRESS_FILE_ERROR);
        }
    }

    /**
     * 遍历文件或文件夹
     */
    private static List<String> traverse(String sourcePath) {
        List<String> result = new ArrayList<>();
        File f = new File(sourcePath);
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    result.addAll(traverse(file.getAbsolutePath()));
                }
            }
        } else if (f.isFile()) {
            result.add(f.getAbsolutePath());
        }
        return result;
    }

    /**
     * 计算相对路径
     */
    private static String calculateRelativePath(String basePath, String filePath) {
        StringBuilder relativePath = new StringBuilder();

        File file = new File(filePath);
        File baseFile = new File(basePath);
        while (!file.getAbsolutePath().equalsIgnoreCase(baseFile.getAbsolutePath())) {
            relativePath.insert(0, file.getName() + File.separator);
            file = file.getParentFile();
        }
        if (relativePath.toString().endsWith(File.separator)) {
            relativePath = new StringBuilder(relativePath.substring(0, relativePath.length() - 1));
        }

        return relativePath.toString();
    }

    /**
     * 判断zip文件
     */
    private static boolean isEndsWithZip(String fileName) {
        return !Strings.isNullOrEmpty(fileName) && fileName.trim().toLowerCase().endsWith(".zip");
    }

    @Data
    private static class Entry {

        private String basePath;

        private String filePath;

        private String relativePath;

    }

}
