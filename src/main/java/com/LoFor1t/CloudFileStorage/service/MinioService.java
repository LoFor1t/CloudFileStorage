package com.LoFor1t.CloudFileStorage.service;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    private boolean userBucketExists(Long userId) {
        try {
            return minioClient.bucketExists(BucketExistsArgs
                    .builder()
                    .bucket("user-" + userId + "-files")
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createUserBucket(Long userId) {
        try {
            if (!userBucketExists(userId)) {
                minioClient.makeBucket(MakeBucketArgs
                        .builder()
                        .bucket("user-" + userId + "-files")
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadObject(Long userId, MultipartFile file) {
        createUserBucket(userId);

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket("user-" + userId + "-files")
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
