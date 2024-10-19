package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.cv_profile.CvProfileResponseDTO;
import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;

public interface CvProfileService {
    CvProfileResponseDTO getCvProfile(String cvProfileId);
}
