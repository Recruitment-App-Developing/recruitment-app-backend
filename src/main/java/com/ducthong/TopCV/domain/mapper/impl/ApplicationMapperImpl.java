package com.ducthong.TopCV.domain.mapper.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.dto.application.ApplicationForCandidateResponseDTO;
import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.application.AppliedCandidateResponseDTO;
import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.ApplicationMapper;
import com.ducthong.TopCV.domain.mapper.JobMapper;
import com.ducthong.TopCV.utility.TimeUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationMapperImpl implements ApplicationMapper {
    private final JobMapper jobMapper;

    @Override
    public ApplicationResponseDTO toApplicationResponseDto(Application entity) {
        Company company = entity.getJob().getCompany();
        Job job = entity.getJob();

        return ApplicationResponseDTO.builder()
                .company(Map.of(
                        "id", company.getId().toString(),
                        "logo", company.getLogo().getImageUrl(),
                        "urlCom", company.getUrlCom(),
                        "name", company.getName()))
                .job(Map.of(
                        "id", job.getId().toString(),
                        "name", job.getName(),
                        "salary", job.getSalary()))
                .cvLink(entity.getCvLink())
                .applicationTime(TimeUtil.toStringDateTime(entity.getApplicationTime()))
                .applicationStatus(entity.getStatus().getTitle())
                .statusChangeTime(TimeUtil.toStringDateTime(entity.getStatusChangeTime()))
                .build();
    }

    @Override
    public AppliedCandidateResponseDTO toAppliedCandidateResponseDto(Application entity) {
        Candidate candidate = entity.getCandidate();
        CvProfile cvProfile = entity.getCandidate().getCvProfile();

        List<String> experiences = cvProfile.getExperiences().stream()
                .map(item -> item.getPosition() + " - " + item.getCompanyName())
                .toList();

        List<String> education = cvProfile.getEducations().stream()
                .map(item -> item.getMainIndustry() + " - " + item.getSchoolName())
                .toList();

        // Name
        String name = candidate.getUsername();
        //        if (!candidate.getFirstName().isEmpty() || !candidate.getLastName().isEmpty())
        //            name = candidate.getFirstName() + " " + candidate.getLastName();

        return AppliedCandidateResponseDTO.builder()
                .id(entity.getId())
                .name(name)
                .email(candidate.getEmail())
                .phoneNumber(candidate.getPhoneNumber())
                .applyDay(TimeUtil.toStringDateTime(entity.getApplicationTime()))
                .experiences(experiences)
                .education(education)
                .statusApplication(String.valueOf(entity.getStatus()))
                .cvLink(entity.getCvLink())
                .build();
    }

    @Override
    public ApplicationForCandidateResponseDTO toApplicationForCandidateResponseDto(Application entity) {
        return ApplicationForCandidateResponseDTO.builder()
                .cvLink(entity.getCvLink())
                .status(entity.getStatus())
                .applicationTime(TimeUtil.toStringFullDateTime(entity.getApplicationTime()))
                .statusChangeTime(TimeUtil.toStringFullDateTime(entity.getStatusChangeTime()))
                .jobInfor(jobMapper.toJobResponseDto(entity.getJob()))
                .build();
    }
}
