package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.application.*;
import com.ducthong.TopCV.domain.dto.candidate.SearchCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;

import java.util.List;

public interface ApplicationService {
    ApplicationResponseDTO addApplication(Integer accountId, ApplicationRequestDTO requestDTO);
    StatisticApplicationResponseDTO statisticCvByCompany(Integer accountId);
    MetaResponse<MetaResponseDTO, List<AppliedCandidateResponseDTO>> getAppliedCandidateByJob(Integer jobId, Integer accountId, MetaRequestDTO requestDTO);
    MetaResponse<MetaResponseDTO, List<ApplicationForCandidateResponseDTO>> getHistoryApplication(Integer accountId, MetaRequestDTO metaRequestDTO, String status);
    MetaResponse<MetaResponseDTO, List<AppliedCandidateResponseDTO>> searchAppliedCandidateByJob(SearchCandidateRequestDTO requestDTO, Integer accountId ,Integer jobId, MetaRequestDTO metaRequestDTO);
}
