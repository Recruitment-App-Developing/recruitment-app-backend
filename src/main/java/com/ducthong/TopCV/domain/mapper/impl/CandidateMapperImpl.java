package com.ducthong.TopCV.domain.mapper.impl;

import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.CandidateMapper;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapperImpl implements CandidateMapper {

    @Override
    public Candidate candidateRequestDtoToCandidate(CandidateRequestDTO requestDTO) {
        return (Candidate) Account.builder()
                .username(requestDTO.username())
                .email(requestDTO.email())
                .deleted(false)
                .build();
    }
}
