package com.ducthong.TopCV.domain.dto.job;

import lombok.Builder;

@Builder
public record EmployerJobResponseDTO(
        Integer id,
        String name,
        String postingTime,
        Integer numberOfView,
        Integer numberOfApplicated,
        Float applicationRate
) {
}
