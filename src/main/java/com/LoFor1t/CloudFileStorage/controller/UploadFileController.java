package com.LoFor1t.CloudFileStorage.controller;

import com.LoFor1t.CloudFileStorage.service.StorageService;
import com.LoFor1t.CloudFileStorage.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.FileAlreadyExistsException;


@Controller
@RequiredArgsConstructor
public class UploadFileController {

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
}
