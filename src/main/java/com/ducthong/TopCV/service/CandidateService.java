package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.dto.candidate.DetailCandidateResponseDTO;

import java.io.IOException;

public interface CandidateService {
    DetailCandidateResponseDTO getDetailCandidate(Integer accountId);
    LoginResponseDTO registerCandidate(CandidateRequestDTO requestDTO);
    DetailCandidateResponseDTO updateCandidate(Integer accountId, UpdCandidateRequestDTO requestDTO) throws IOException;
}
