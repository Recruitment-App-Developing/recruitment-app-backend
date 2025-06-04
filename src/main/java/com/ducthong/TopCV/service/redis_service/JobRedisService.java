package com.ducthong.TopCV.service.redis_service;

import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface JobRedisService {
    void clear();
    MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJob(MetaRequestDTO metaRequestDTO) throws JsonProcessingException;
    void saveListJob(MetaRequestDTO metaRequestDTO, MetaResponse<MetaResponseDTO, List<JobResponseDTO>> saveObject) throws JsonProcessingException;
}
