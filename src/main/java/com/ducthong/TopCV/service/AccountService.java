package com.ducthong.TopCV.service;

import java.util.List;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.responses.Response;

public interface AccountService {
    Response<List<AccountResponseDTO>> getAllActiveAccount();

    Response<CandidateResponseDTO> getCandidateAccount(Integer id);
}
