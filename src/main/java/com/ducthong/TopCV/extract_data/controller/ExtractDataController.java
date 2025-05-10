package com.ducthong.TopCV.extract_data.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.extract_data.service.ExtractDataService;
import com.ducthong.TopCV.utility.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
public class ExtractDataController {
    private final ExtractDataService extractDataService;

    @PostMapping("/api/v1/upload-pdf")
    public ResponseEntity<String> extractData(@RequestParam("file") MultipartFile file) {
        if (!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.badRequest().body("Chỉ chấp nhận tệp PDF.");
        }
        String cvId = Common.generateUUID();
        String result = extractDataService.extractData(file, cvId);
        return ResponseEntity.ok().body(result);
    }
}
