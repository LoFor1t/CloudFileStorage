package com.LoFor1t.CloudFileStorage.controller;

import com.LoFor1t.CloudFileStorage.service.StorageService;
import com.LoFor1t.CloudFileStorage.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;
    private final UserServiceImpl userService;


    @GetMapping
    public String storage(Model model) {
        Long userId = userService.getUserIdBySecurityContext();
        List<String> userFileNames = storageService.listNamesOfUserFiles(userId);

        model.addAttribute("fileNames", userFileNames);
        return "storage";
    }
}
