package com.LoFor1t.CloudFileStorage.controller;

import com.LoFor1t.CloudFileStorage.service.StorageService;
import com.LoFor1t.CloudFileStorage.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.FileAlreadyExistsException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final StorageService storageService;
    private final UserServiceImpl userService;

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

        return "redirect:/storage";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("fileName") String fileName) {
        System.out.println("Check deleteFile method");

        Long userId = userService.getUserIdBySecurityContext();

        storageService.deleteObject(userId, fileName);

        return "redirect:/storage";
    }
}
