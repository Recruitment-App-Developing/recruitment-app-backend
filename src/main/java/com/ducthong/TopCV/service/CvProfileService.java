package com.ducthong.TopCV.service;

import java.util.List;

import com.ducthong.TopCV.domain.dto.cv_profile.CvProfileResponseDTO;
import com.ducthong.TopCV.domain.dto.cv_profile.EducationRequestDTO;
import com.ducthong.TopCV.domain.entity.CvProfile.Education;

public interface CvProfileService {
    CvProfileResponseDTO getCvProfile(String cvProfileId);

    List<Education> updateEducationInCvProfile(EducationRequestDTO requestDTO, Integer accountId);

    List<Education> deleteEducationInCvProfile(String educationId, Integer accountId);
}
