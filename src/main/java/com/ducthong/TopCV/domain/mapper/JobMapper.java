package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.job.DetailJobResponseDTO;
import com.ducthong.TopCV.domain.dto.job.JobRequestDTO;
import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.entity.Job;

public interface JobMapper {
    JobResponseDTO toJobResponseDto(Job entity);

    DetailJobResponseDTO toDetailJobResponseDto(Job entity);

    Job jobRequestDtoToJobEntity(JobRequestDTO requestDTO);
}
