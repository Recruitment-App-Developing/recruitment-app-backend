package com.ducthong.TopCV.domain.dto.cv;

import lombok.Builder;

@Builder
public record CvResponseDTO(
        String id, String name, String cvLink, String cvType, String lastUpdated, String whenCreated) {}
