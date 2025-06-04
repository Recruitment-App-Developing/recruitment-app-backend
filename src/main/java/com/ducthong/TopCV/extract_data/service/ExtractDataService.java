package com.ducthong.TopCV.extract_data.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ExtractDataService {
    void extractData(MultipartFile cvFile, String cvId);
    CompletableFuture<Void> extractDataWithWebSocket(MultipartFile file, String cvId, String sessionId);
}
