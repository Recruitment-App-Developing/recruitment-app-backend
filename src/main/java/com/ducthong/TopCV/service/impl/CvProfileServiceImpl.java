package com.ducthong.TopCV.service.impl;

import com.ducthong.TopCV.domain.dto.cv_profile.CvProfileResponseDTO;
import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.CvProfileRepository;
import com.ducthong.TopCV.service.CvProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CvProfileServiceImpl implements CvProfileService {
    private final CvProfileRepository cvProfileRepo;
    @Override
    public CvProfileResponseDTO getCvProfile(String cvProfileId) {
        Optional<CvProfile> findCvProfile = cvProfileRepo.findById(cvProfileId);
        if (findCvProfile.isEmpty()) throw new AppException("This cv is not existed");
        CvProfile cvProfile = findCvProfile.get();

        return CvProfileResponseDTO.builder()
                .id(cvProfile.getId())
                .avatar(cvProfile.getCandidate().getAvatar().getImageUrl())
                .candidateName(cvProfile.getCandidate().getFirstName() + cvProfile.getCandidate().getLastName())
                .educations(cvProfile.getEducations())
                .experiences(cvProfile.getExperiences())
                .build();
    }
}
