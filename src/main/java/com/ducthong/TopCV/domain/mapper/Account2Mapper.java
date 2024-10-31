package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.account.AddCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;

public interface Account2Mapper {
    LoginResponseDTO toLoginResponseDto(Account account);
    Candidate updCandidateToCandidate(Integer id, UpdCandidateRequestDTO requestDTO);
    Candidate addCandidateDtoToCandidateEntity(AddCandidateRequestDTO requestDTO);
}
