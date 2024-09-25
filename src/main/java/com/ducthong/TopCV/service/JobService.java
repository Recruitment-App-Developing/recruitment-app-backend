package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.dto.job.DetailJobResponseDTO;
import com.ducthong.TopCV.domain.dto.job.JobRequestDTO;
import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;

public interface JobService {
    DetailJobResponseDTO getDetailJob(Integer jobId);
    MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJobSpecification(MetaRequestDTO metaRequestDTO);
    MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJob(MetaRequestDTO metaRequestDTO);
    DetailJobResponseDTO addJob(JobRequestDTO requestDTO, Integer userId) throws IOException;
}
