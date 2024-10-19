package com.ducthong.TopCV.domain.dto.application;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record AppliedCandidateResponseDTO(
        String name,
        String email,
        String phoneNumber,
        String applyDay,
        List<String> experiences,
        List<String> education,
        String statusApplication
) {
}
