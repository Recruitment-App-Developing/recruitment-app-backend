package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Candidate;

public interface CandidateMapper {
    Candidate candidateRequestDtoToCandidate(CandidateRequestDTO requestDTO);
}
