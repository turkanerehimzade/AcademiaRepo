package com.example.education.controller;

import com.example.education.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/s3")
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("photo") MultipartFile photo) {
        s3Service.uploadFile(photo, "uploads/" + photo.getOriginalFilename());
        return "Fayl uğurla yükləndi!";
    }

}
