package com.ducthong.TopCV.domain.mapper.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ducthong.TopCV.configuration.AppConfig;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.candidate.DetailCandidateResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.CandidateMapper;
import com.ducthong.TopCV.utility.TimeUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CandidateMapperImpl implements CandidateMapper {
    private final AppConfig appConfig;

    @Override
    public CandidateResponseDTO toCandidateResponseDto(Candidate entity) {
        return CandidateResponseDTO.builder().build();
    }

    @Override
    public DetailCandidateResponseDTO toDetailCandidateResponseDto(Candidate entity) {
        // Avatar
        String id = "default";
        String avatar = appConfig.getDEFAULT_AVATAR();
        if (entity.getAvatar() != null) {
            id = entity.getAvatar().getId().toString();
            avatar = entity.getAvatar().getImageUrl();
        }

        return DetailCandidateResponseDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .dateOfBirth(TimeUtil.toStringDate(entity.getDateOfBirth()))
                .email(entity.getEmail())
                .avatar(Map.of(
                        "id", id,
                        "url", avatar))
                .phoneNumber(entity.getPhoneNumber())
                .address(entity.getAddress())
                .lastUpdated(TimeUtil.toStringFullDateTime(entity.getLastUpdated()))
                .lastLogIn(TimeUtil.toStringFullDateTime(entity.getWhenCreated()))
                .isFindJob(entity.getIsFindJob())
                .build();
    }

    @Override
    public Candidate candidateRequestDtoToCandidate(CandidateRequestDTO requestDTO) {
        return (Candidate) Account.builder()
                .username(requestDTO.username())
                .email(requestDTO.email())
                .deleted(false)
                .build();
    }

    @Override
    public Candidate udpCandidateDtoToCandidate(Candidate candidateEntity, UpdCandidateRequestDTO requestDTO) {
        candidateEntity.setFirstName(requestDTO.firstName());
        candidateEntity.setLastName(requestDTO.lastName());
        candidateEntity.setGender(requestDTO.gender());
        candidateEntity.setPhoneNumber(requestDTO.phoneNumber());
        candidateEntity.setDateOfBirth(TimeUtil.convertToDate(requestDTO.dateOfBirth()));
        candidateEntity.setLastUpdated(TimeUtil.getDateTimeNow());

        return candidateEntity;
    }
}
