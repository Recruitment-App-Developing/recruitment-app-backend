package com.ducthong.TopCV.domain.dto.cv_profile;

import java.util.List;

import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import com.ducthong.TopCV.domain.entity.CvProfile.Experience;

import lombok.Builder;

@Builder
public record CvProfileResponseDTO(
        String id,
        String avatar,
        String dateOfBirth,
        String phoneNumber,
        String candidateName,
        String email,
        List<Education> educations,
        List<Experience> experiences) {}
