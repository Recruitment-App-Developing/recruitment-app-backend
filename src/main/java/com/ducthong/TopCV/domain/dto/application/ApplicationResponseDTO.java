package com.ducthong.TopCV.domain.dto.application;

import lombok.Builder;

import java.util.Map;

@Builder
public record ApplicationResponseDTO(
    Map<String, String> company,
    Map<String, String> job,
    String cvLink,
    String applicationTime,
    String applicationStatus,
    String statusChangeTime
) {
}
