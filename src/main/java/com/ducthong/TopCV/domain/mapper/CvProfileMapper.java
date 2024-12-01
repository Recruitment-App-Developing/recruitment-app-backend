package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.cv_profile.EducationRequestDTO;
import com.ducthong.TopCV.domain.dto.cv_profile.ExperienceRequestDTO;
import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import com.ducthong.TopCV.domain.entity.CvProfile.Experience;

public interface CvProfileMapper {
    Education educationRequestDtoToEducation(EducationRequestDTO requestDTO);

    Experience experienceRequestDtoToExperience(ExperienceRequestDTO requestDTO);
}
