package com.starfish.common.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * RustFSService
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-11-19
 */
@Slf4j
@SuppressWarnings("unused")
public class RustFSService {

    private final S3Client rustFSClient;

    private final RustFSProperties rustFSProperties;

    private final S3Presigner s3Presigner;

    public RustFSService(RustFSProperties rustFSProperties) {
        this.rustFSProperties = rustFSProperties;

        this.rustFSClient = S3Client.builder()
                .endpointOverride(URI.create(rustFSProperties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(rustFSProperties.getAccessKey(), rustFSProperties.getSecretKey())))
                .region(Region.US_EAST_1)
                .build();

        this.s3Presigner = S3Presigner.builder()
                .endpointOverride(URI.create(rustFSProperties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(rustFSProperties.getAccessKey(), rustFSProperties.getSecretKey())))
                .region(Region.US_EAST_1)
                .build();
    }

    /**
     * 创建桶，如果桶已存在则忽略，如果桶不存在则创建
     */
    public void createBucket(String bucketName) {
        try {
            rustFSClient.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
        } catch (NoSuchBucketException e) {
            rustFSClient.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
            log.info("桶 {} 创建成功", bucketName);
        } catch (Exception e) {
            throw new RuntimeException("创建桶失败：" + e.getMessage(), e);
        }
    }

    /**
     * 上传文件（支持Spring的MultipartFile，适配前端上传）
     *
     * @param file       前端上传的文件
     * @param objectName 存储到MinIO的文件名（如：images/2026/1.jpg）
     */
    public String uploadFile(MultipartFile file, String objectName) {
        return uploadFile(file, objectName, rustFSProperties.getBucketName());
    }

    /**
     * 重载：指定桶名上传
     */
    public String uploadFile(MultipartFile file, String objectName, String bucketName) {
        try {
            createBucket(bucketName);
            rustFSClient.putObject(PutObjectRequest.builder().bucket(bucketName).key(objectName).contentType(file.getContentType()).build(), RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return getPresignedUrl(objectName, bucketName, rustFSProperties.getExpire());
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：" + e.getMessage(), e);
        }
    }

    /**
     * 下载文件（返回InputStream，适配SpringMVC响应流）
     */
    public InputStream downloadFile(String objectName) {
        return downloadFile(objectName, rustFSProperties.getBucketName());
    }

    /**
     * 重载：指定桶名下载
     */
    public InputStream downloadFile(String objectName, String bucketName) {
        try {
            return rustFSClient.getObject(GetObjectRequest.builder().bucket(bucketName).key(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException("下载文件失败：" + e.getMessage(), e);
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String objectName) {
        deleteFile(objectName, rustFSProperties.getBucketName());
    }

    /**
     * 重载：指定桶名删除
     */
    public void deleteFile(String objectName, String bucketName) {
        try {
            rustFSClient.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException("删除文件失败：" + e.getMessage(), e);
        }
    }

    /**
     * 生成文件临时访问链接
     */
    public String getPresignedUrl(String objectName) {
        return getPresignedUrl(objectName, rustFSProperties.getBucketName(), rustFSProperties.getExpire());
    }

    /**
     * 重载：指定桶名和有效期
     */
    public String getPresignedUrl(String objectName, String bucketName, int expire) {
        try {
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder().signatureDuration(Duration.ofSeconds(expire)).getObjectRequest(GetObjectRequest.builder().bucket(bucketName).key(objectName).build()).build();
            return s3Presigner.presignGetObject(presignRequest).url().toString();
        } catch (Exception e) {
            throw new RuntimeException("生成临时链接失败：" + e.getMessage(), e);
        }
    }

    /**
     * 列出桶内所有文件
     */
    public List<String> listFiles(String bucketName) {
        List<String> fileNames = new ArrayList<>();
        try {
            ListObjectsV2Iterable results = rustFSClient.listObjectsV2Paginator(ListObjectsV2Request.builder().bucket(bucketName).build());
            for (S3Object object : results.contents()) {
                fileNames.add(object.key());
            }
        } catch (Exception e) {
            throw new RuntimeException("列出文件失败：" + e.getMessage(), e);
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
        tempFile.deleteOnExit();
        return tempFile;
    }

}