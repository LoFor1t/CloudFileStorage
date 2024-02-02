package com.LoFor1t.CloudFileStorage.controller;

import com.LoFor1t.CloudFileStorage.service.StorageService;
import com.LoFor1t.CloudFileStorage.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {
    private final StorageService storageService;
    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<String>> getUserFileNames() {
        Long userId = userService.getUserIdBySecurityContext();

//        Long userId = 102L;

        return new ResponseEntity<>(storageService.listNamesOfUserFiles(userId), HttpStatus.OK);
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        Long userId = userService.getUserIdBySecurityContext();

        String fileUploadError = "";

        try {
            storageService.uploadObject(userId, file);
        } catch (FileAlreadyExistsException e) {
            fileUploadError = e.getMessage();
        }

        redirectAttributes.addFlashAttribute("fileUploadError", fileUploadError);

        return file.getName();
    }

    @GetMapping("/delete")
    public ResponseEntity deleteFile(@RequestParam("fileName") String fileName) {
        Long userId = userService.getUserIdBySecurityContext();

        storageService.deleteObject(userId, fileName);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/changeName")
    public String changeFileName(@RequestParam("oldFileName") String fileName, @RequestParam("newFileName") String newFileName) {
        Long userId = userService.getUserIdBySecurityContext();

        storageService.changeObjectName(userId, fileName, newFileName);

        return "redirect:/storage";
    }
}
