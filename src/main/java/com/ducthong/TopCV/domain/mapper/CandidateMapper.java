package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.candidate.DetailCandidateResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Candidate;

public interface CandidateMapper {
    DetailCandidateResponseDTO toDetailCandidateResponseDto(Candidate entity);
    Candidate candidateRequestDtoToCandidate(CandidateRequestDTO requestDTO);
    Candidate udpCandidateDtoToCandidate(Candidate candidateEntity, UpdCandidateRequestDTO requestDTO);
}
