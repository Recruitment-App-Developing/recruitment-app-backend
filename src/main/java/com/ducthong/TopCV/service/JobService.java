package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.dto.job.*;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.responses.MetaResponse;

public interface JobService {
    Job isVerifiedJob(Integer jobId, Integer accountId);
    DetailJobResponseDTO getDetailJob(Integer jobId);
    MetaResponse<MetaResponseDTO, List<EmployerJobResponseDTO>> getListJobByCompany(MetaRequestDTO metaRequestDTO, Integer accountId);
    MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJobSpecification(MetaRequestDTO metaRequestDTO);
    MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJob(MetaRequestDTO metaRequestDTO);
    DetailJobResponseDTO addJob(JobRequestDTO requestDTO, Integer userId) throws IOException;
    DetailJobResponseDTO updateJob(UpdJobRequestDTO requestDTO, Integer userId, Integer jobId);
}
