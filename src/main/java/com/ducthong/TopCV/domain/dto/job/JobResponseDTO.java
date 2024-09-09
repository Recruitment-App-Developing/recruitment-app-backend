package com.ducthong.TopCV.domain.dto.job;

import lombok.Builder;

import java.util.Map;

@Builder
public record JobResponseDTO(
    Integer id,
    String name,
    String cities,
    Map<String, String> comapny,
    String salary
) {
}
