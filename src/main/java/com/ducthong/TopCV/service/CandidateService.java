package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;

public interface CandidateService {
    LoginResponseDTO registerCandidate(CandidateRequestDTO requestDTO);
}
