package com.LoFor1t.CloudFileStorage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    @GetMapping
    public String storage() {
        return "storage";
    }
}
