package com.ducthong.TopCV.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ducthong.TopCV.repository.ImageRepository;
import com.ducthong.TopCV.service.impl.CloudinaryServiceImpl;

@RestController
public class CloudinaryController {
    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    @Autowired
    private ImageRepository imageRepository;

    //    @Autowired
    //    private ImageServiceImpl imageService;

    //    @PostMapping("/api/v1/upload")
    //    public ResponseEntity<Response<Image>> upload(@RequestParam(name = "uploadFile") MultipartFile uploadFile)
    // throws IOException {
    //        Image res = imageService.uploadImage(uploadFile, null);
    //        return ResponseEntity.status(HttpStatus.OK).body(Response.successfulResponse(HttpStatus.OK.value(),"Upload
    // Successful", res));
    //    }
}
