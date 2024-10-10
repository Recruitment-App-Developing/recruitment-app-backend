package com.ducthong.TopCV.domain.dto.job;

import java.util.Map;

import lombok.Builder;

@Builder
public record JobResponseDTO(
        Integer id,
        String name,
        String cities,
        Map<String, String> company,
        String salary) {}
