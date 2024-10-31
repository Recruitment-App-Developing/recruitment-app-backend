package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.cv_profile.CvProfileResponseDTO;
import com.ducthong.TopCV.domain.dto.cv_profile.EducationRequestDTO;
import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;
import com.ducthong.TopCV.domain.entity.CvProfile.Education;

import java.util.List;

public interface CvProfileService {
    CvProfileResponseDTO getCvProfile(String cvProfileId);
    List<Education> updateEducationInCvProfile(EducationRequestDTO requestDTO, Integer accountId);
    List<Education> deleteEducationInCvProfile(String educationId, Integer accountId);
}
