package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    Map uploadFileBase64(String base64String, String folder) throws IOException;
    Map upload(MultipartFile multipartFile, String folder) throws IOException;

    Map delete(String id) throws IOException;
}
