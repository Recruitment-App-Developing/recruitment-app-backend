package com.ducthong.TopCV.domain.dto.application;

import lombok.Builder;

@Builder
public record StatisticApplicationResponseDTO(
        Integer numberOfCv,
        Integer numberOfApplyCv,
        Integer numberOfOpenContactCv,
        Integer numberOfInterviewCv,
        Integer numberOfFollowCv
) {
}
