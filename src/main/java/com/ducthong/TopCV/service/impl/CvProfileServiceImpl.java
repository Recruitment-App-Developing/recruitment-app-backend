package com.ducthong.TopCV.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.domain.dto.cv_profile.CvProfileResponseDTO;
import com.ducthong.TopCV.domain.dto.cv_profile.EducationRequestDTO;
import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;
import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.CvProfileMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.CvProfileRepository;
import com.ducthong.TopCV.repository.cv_profile.EducationRepository;
import com.ducthong.TopCV.service.CvProfileService;
import com.ducthong.TopCV.utility.GetRoleUtil;
import com.ducthong.TopCV.utility.TimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CvProfileServiceImpl implements CvProfileService {
    private final CvProfileRepository cvProfileRepo;
    private final EducationRepository educationRepo;

    private final CvProfileMapper cvProfileMapper;
    // Varient
    @Value("${cloudinary.folder.default-avatar}")
    private String DEFAULT_AVATAR;

    @Override
    @Transactional
    public CvProfileResponseDTO getCvProfile(String cvProfileId) {
        Optional<CvProfile> findCvProfile = cvProfileRepo.findById(cvProfileId);
        if (findCvProfile.isEmpty()) throw new AppException("This cv is not existed");
        CvProfile cvProfile = findCvProfile.get();

        // Avatar
        String avatar = DEFAULT_AVATAR;
        if (cvProfile.getCandidate().getAvatar() != null)
            avatar = cvProfile.getCandidate().getAvatar().getImageUrl();
        // Candidate
        Candidate candidate = cvProfile.getCandidate();

        return CvProfileResponseDTO.builder()
                .id(cvProfile.getId())
                .avatar(avatar)
                .candidateName(cvProfile.getCandidate().getFirstName()
                        + cvProfile.getCandidate().getLastName())
                .dateOfBirth(TimeUtil.toStringDate(candidate.getDateOfBirth()))
                .phoneNumber(candidate.getPhoneNumber())
                .email(candidate.getEmail())
                .educations(cvProfile.getEducations())
                .experiences(cvProfile.getExperiences())
                .build();
    }

    @Override
    public List<Education> updateEducationInCvProfile(EducationRequestDTO requestDTO, Integer accountId) {
        Candidate candidate = GetRoleUtil.getCandidate(accountId);
        Education updEducation = cvProfileMapper.educationRequestDtoToEducation(requestDTO);
        if (requestDTO.educationId() != null && !requestDTO.educationId().isEmpty()) {
            Optional<Education> education = candidate.getCvProfile().getEducations().stream()
                    .filter(item -> Objects.equals(item.getId(), requestDTO.educationId()))
                    .findFirst();
            if (education.isEmpty()) throw new AppException("This education is not existed");
        }
        updEducation.setCvProfile(candidate.getCvProfile());
        educationRepo.save(updEducation);

        return cvProfileRepo.findById(candidate.getCvProfile().getId()).get().getEducations().stream()
                .toList();
    }

    @Override
    public List<Education> deleteEducationInCvProfile(String educationId, Integer accountId) {
        Candidate candidate = GetRoleUtil.getCandidate(accountId);
        CvProfile cvProfile = candidate.getCvProfile();
        Optional<Education> education = cvProfile.getEducations().stream()
                .filter(item -> Objects.equals(item.getId(), educationId))
                .findFirst();
        if (education.isEmpty()) throw new AppException("This education is not existed");
        educationRepo.deleteById(educationId);

        return cvProfileRepo.findById(candidate.getCvProfile().getId()).get().getEducations().stream()
                .toList();
    }
}
