package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.application.ApplicationRequestDTO;
import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;

public interface ApplicationService {
    ApplicationResponseDTO addApplication(Integer accountId, ApplicationRequestDTO requestDTO);
}
