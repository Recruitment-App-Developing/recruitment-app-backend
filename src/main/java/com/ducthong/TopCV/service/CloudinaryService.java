package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    Map upload(MultipartFile multipartFile, String folder) throws IOException;
}
