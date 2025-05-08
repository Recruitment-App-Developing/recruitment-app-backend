package com.ducthong.TopCV.domain.dto.application;

import java.util.List;

import lombok.Builder;

@Builder
public record AppliedCandidateResponseDTO(
        String id,
        String name,
        String email,
        String phoneNumber,
        String applyDay,
        List<String> experiences,
        List<String> education,
        String statusApplication,
        String cvLink) {}
