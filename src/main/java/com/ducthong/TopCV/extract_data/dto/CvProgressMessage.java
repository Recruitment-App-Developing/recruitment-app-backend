package com.ducthong.TopCV.extract_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvProgressMessage {
    private String cvId;
    private String status;
    private int totalFiles;
    private int completedFiles;
}
