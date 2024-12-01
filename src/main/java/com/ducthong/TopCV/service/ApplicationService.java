package com.ducthong.TopCV.service;

import java.util.List;

import com.ducthong.TopCV.domain.dto.application.ApplicationRequestDTO;
import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.application.AppliedCandidateResponseDTO;
import com.ducthong.TopCV.domain.dto.application.StatisticApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;

public interface ApplicationService {
    ApplicationResponseDTO addApplication(Integer accountId, ApplicationRequestDTO requestDTO);

    StatisticApplicationResponseDTO statisticCvByCompany(Integer accountId);

    MetaResponse<MetaResponseDTO, List<AppliedCandidateResponseDTO>> getAppliedCandidateByJob(
            Integer jobId, Integer accountId, MetaRequestDTO requestDTO);
}
