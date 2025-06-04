package com.ducthong.TopCV.extract_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployerUploadCvRequest {
    private List<MultipartFile> files;
    private List<String> names;
    private String sessionId;
}
