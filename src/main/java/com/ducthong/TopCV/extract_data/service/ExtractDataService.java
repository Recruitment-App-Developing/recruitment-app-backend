package com.ducthong.TopCV.extract_data.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExtractDataService {
    void extractData(MultipartFile cvFile, String cvId);
}
