package com.ducthong.TopCV.domain.dto.application;

import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import lombok.Builder;

@Builder
public record ApplicationForCandidateResponseDTO(
    String cvLink,
    ApplicationStatus status,
    String applicationTime,
    String statusChangeTime,
    JobResponseDTO jobInfor
) {
}
