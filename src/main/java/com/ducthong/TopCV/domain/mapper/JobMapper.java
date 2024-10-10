package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.job.DetailJobResponseDTO;
import com.ducthong.TopCV.domain.dto.job.EmployerJobResponseDTO;
import com.ducthong.TopCV.domain.dto.job.JobRequestDTO;
import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.entity.Job;

public interface JobMapper {
    JobResponseDTO toJobResponseDto(Job entity);

    EmployerJobResponseDTO toEmployerJobResponseDto(Job entity);

    DetailJobResponseDTO toDetailJobResponseDto(Job entity, Boolean isApply);

    Job jobRequestDtoToJobEntity(JobRequestDTO requestDTO);
}
