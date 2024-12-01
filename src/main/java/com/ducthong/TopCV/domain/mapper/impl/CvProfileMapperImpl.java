package com.ducthong.TopCV.domain.mapper.impl;

import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.dto.cv_profile.EducationRequestDTO;
import com.ducthong.TopCV.domain.dto.cv_profile.ExperienceRequestDTO;
import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import com.ducthong.TopCV.domain.entity.CvProfile.Experience;
import com.ducthong.TopCV.domain.mapper.CvProfileMapper;
import com.ducthong.TopCV.utility.TimeUtil;

@Component
public class CvProfileMapperImpl implements CvProfileMapper {
    @Override
    public Education educationRequestDtoToEducation(EducationRequestDTO requestDTO) {
        return Education.builder()
                .id(requestDTO.educationId())
                .schoolName(requestDTO.schoolName())
                .mainIndustry(requestDTO.mainIndustry())
                .startTime(TimeUtil.monthYearToDate(requestDTO.startTime()))
                .endTime(TimeUtil.monthYearToDate(requestDTO.endTime()))
                .detail(requestDTO.detail())
                .build();
    }

    @Override
    public Experience experienceRequestDtoToExperience(ExperienceRequestDTO requestDTO) {
        return Experience.builder()
                .companyName(requestDTO.companyName())
                .position(requestDTO.position())
                .startTime(TimeUtil.monthYearToDate(requestDTO.startTime()))
                .endTime(TimeUtil.monthYearToDate(requestDTO.endTime()))
                .detail(requestDTO.detail())
                .build();
    }
}
