package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.application.AppliedCandidateResponseDTO;
import com.ducthong.TopCV.domain.entity.Application;

public interface ApplicationMapper {
    ApplicationResponseDTO toApplicationResponseDto(Application entity);

    AppliedCandidateResponseDTO toAppliedCandidateResponseDto(Application entity);
}
