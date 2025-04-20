package com.ducthong.TopCV.extract_data.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExtractDataService {
    String extractData(MultipartFile cvFile, String cvId);
}
