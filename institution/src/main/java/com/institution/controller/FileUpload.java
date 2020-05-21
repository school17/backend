package com.institution.controller;


import com.institution.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUpload {
    @Autowired
    ImageService imageService;
    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        System.out.println("GETTING INSIDE IMAGE UPLOAD");
        imageService.uploadFile(file, "DemoFile");
        return "done";
    }
}
