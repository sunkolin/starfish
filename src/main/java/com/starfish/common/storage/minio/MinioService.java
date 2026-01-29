package com.starfish.common.storage.minio;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * MinioService
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-29
 */
@SuppressWarnings("unused")
public class MinioService {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    public MinioService(MinioClient minioClient,MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    /**
     * 创建桶，如果桶已存在则忽略，如果桶不存在则创建
     */
    public void createBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("桶 " + bucketName + " 创建成功");
            }
        } catch (Exception e) {
            throw new RuntimeException("创建MinIO桶失败：" + e.getMessage(), e);
        }
    }

    /**
     * 上传文件（支持Spring的MultipartFile，适配前端上传）
     *
     * @param file       前端上传的文件
     * @param objectName 存储到MinIO的文件名（如：images/2026/1.jpg）
     */
    public String uploadFile(MultipartFile file, String objectName) {
        return uploadFile(file, objectName, minioProperties.getBucket());
    }

    /**
     * 重载：指定桶名上传
     */
    public String uploadFile(MultipartFile file, String objectName, String bucketName) {
        try {
            // 先创建桶（确保桶存在）
            createBucket(bucketName);

            // 上传MultipartFile（通过InputStream）
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            // 返回文件临时访问链接
            return getPresignedUrl(objectName, bucketName,minioProperties.getExpire());
        } catch (Exception e) {
            throw new RuntimeException("上传文件到MinIO失败：" + e.getMessage(), e);
        }
    }

    /**
     * 下载文件（返回InputStream，适配SpringMVC响应流）
     */
    public InputStream downloadFile(String objectName) {
        return downloadFile(objectName, minioProperties.getBucket());
    }

    /**
     * 重载：指定桶名下载
     */
    public InputStream downloadFile(String objectName, String bucketName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("下载MinIO文件失败：" + e.getMessage(), e);
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String objectName) {
        deleteFile(objectName, minioProperties.getBucket());
    }

    /**
     * 重载：指定桶名删除
     */
    public void deleteFile(String objectName, String bucketName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("删除MinIO文件失败：" + e.getMessage(), e);
        }
    }

    /**
     * 生成文件临时访问链接
     */
    public String getPresignedUrl(String objectName) {
        return getPresignedUrl(objectName, minioProperties.getBucket(), minioProperties.getExpire());
    }

    /**
     * 重载：指定桶名和有效期
     */
    public String getPresignedUrl(String objectName, String bucketName, int expire) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expire)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("生成MinIO临时链接失败：" + e.getMessage(), e);
        }
    }

    /**
     * 列出桶内所有文件
     */
    public List<String> listFiles(String bucketName) {
        List<String> fileNames = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).build()
            );
            for (Result<Item> result : results) {
                Item item = result.get();
                fileNames.add(item.objectName());
            }
        } catch (Exception e) {
            throw new RuntimeException("列出MinIO文件失败：" + e.getMessage(), e);
        }
        return fileNames;
    }

    /**
     * 辅助方法：MultipartFile转File（可选）
     */
    private File multipartFileToFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        // JVM 退出时删除临时文件
        tempFile.deleteOnExit();
        return tempFile;
    }

}

