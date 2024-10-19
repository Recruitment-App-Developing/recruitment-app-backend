package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.job.*;
import com.ducthong.TopCV.domain.entity.Job;

public interface JobMapper {
    JobResponseDTO toJobResponseDto(Job entity);

    EmployerJobResponseDTO toEmployerJobResponseDto(Job entity);

    DetailJobResponseDTO toDetailJobResponseDto(Job entity, Boolean isApply);

    Job jobRequestDtoToJobEntity(JobRequestDTO requestDTO);

    Job updJobRequestDtoToJobEntity(UpdJobRequestDTO requestDTO, Job entity);
}
