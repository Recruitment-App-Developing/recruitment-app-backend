package com.ducthong.TopCV.domain.dto.cv_profile;

import java.util.List;

public record CvProfileRequestDTO(
        List<EducationRequestDTO> educations,
        List<ExperienceRequestDTO> experiences
) {
}
