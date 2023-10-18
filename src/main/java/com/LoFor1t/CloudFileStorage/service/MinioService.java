package com.LoFor1t.CloudFileStorage.service;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    private String getBucketName(Long userId) {
        return "user-" + userId + "-files";
    }

    private boolean userBucketExists(Long userId) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(getBucketName(userId))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createUserBucket(Long userId) {
        try {
            if (!userBucketExists(userId)) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(getBucketName(userId))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadObject(Long userId, MultipartFile file) throws FileAlreadyExistsException {
        createUserBucket(userId);

        if (checkFileExisting(userId, file.getOriginalFilename())) {
            throw new FileAlreadyExistsException("File " + file.getOriginalFilename() + " already exists in your bucket");
        }

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(getBucketName(userId))
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkFileExisting(Long userId, String fileName) {
        try {
            return minioClient.statObject(StatObjectArgs.builder()
                    .bucket(getBucketName(userId))
                    .object(fileName)
                    .build()) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
