package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.application.ApplicationRequestDTO;
import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.application.StatisticApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;

import java.util.List;

public interface ApplicationService {
    ApplicationResponseDTO addApplication(Integer accountId, ApplicationRequestDTO requestDTO);
    StatisticApplicationResponseDTO statisticCvByCompany(Integer accountId);
    //MetaResponse<MetaResponseDTO, List<>> //TODO
}
