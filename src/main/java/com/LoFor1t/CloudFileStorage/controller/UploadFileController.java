package com.LoFor1t.CloudFileStorage.controller;

import com.LoFor1t.CloudFileStorage.service.MinioService;
import com.LoFor1t.CloudFileStorage.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;

@RestController
@RequiredArgsConstructor
public class UploadFileController {

    private final MinioService minioService;
    private final UserServiceImpl userService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        Long userId = userService.getUserIdBySecurityContext();


        try {
            minioService.uploadObject(userId, file);
        } catch (FileAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("File uploaded successfully");
    }
}
