package com.institution.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private AmazonClient amazonClient;
    @Autowired
    ImageService(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }
    public String uploadFile(MultipartFile file, String fileName) {
        return this.amazonClient.uploadFile(file);
    }

    public String uploadFile(String base64, String fileName){
        return this.amazonClient.uploadFile(base64, fileName);
    }
}
