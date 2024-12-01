package com.ducthong.TopCV.domain.dto.application;

import java.util.Map;

import lombok.Builder;

@Builder
public record ApplicationResponseDTO(
        Map<String, String> company,
        Map<String, String> job,
        String cvLink,
        String applicationTime,
        String applicationStatus,
        String statusChangeTime) {}
