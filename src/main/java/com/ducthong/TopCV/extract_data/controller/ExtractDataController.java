package com.ducthong.TopCV.extract_data.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.domain.dto.cv.CvRequestDTO;
import com.ducthong.TopCV.extract_data.dto.EmployerUploadCvRequest;
import com.ducthong.TopCV.extract_data.service.CvInforService;
import com.ducthong.TopCV.extract_data.service.ExtractDataService;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.utility.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestApiV1
@RequiredArgsConstructor
public class ExtractDataController {
    private final ExtractDataService extractDataService;
    private final CvInforService cvInforService;

    @PostMapping("/api/v1/upload-pdf")
    public ResponseEntity extractData(@RequestParam("file") MultipartFile file) {
        if (!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.badRequest().body("Chỉ chấp nhận tệp PDF.");
        }
        String cvId = Common.generateUUID();
        extractDataService.extractData(file, cvId);
        return ResponseEntity.ok().body("Thành công");
    }

    @PostMapping("/api/v1/employer/upload-file")
    public ResponseEntity extractDataEmployer(@ModelAttribute EmployerUploadCvRequest request) {
        Response response = cvInforService.uploadCvFiles(request);
        return ResponseEntity.ok().body(response);
    }
}
