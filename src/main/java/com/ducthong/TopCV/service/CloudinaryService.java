package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ducthong.TopCV.domain.dto.cloudinary.CloudinaryResponseDTO;

public interface CloudinaryService {
    Map uploadFileBase64(String base64String, String folder) throws IOException;

    CloudinaryResponseDTO uploadFileBase64_v2(String base64String, String folder) throws IOException;

    Map upload(MultipartFile multipartFile, String folder) throws IOException;

    void delete(String id);
}
