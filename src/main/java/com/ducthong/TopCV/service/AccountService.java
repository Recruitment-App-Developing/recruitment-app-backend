package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.AddCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.responses.Response;

public interface AccountService {
    // CANDIDATE
    Response<CandidateResponseDTO> getNoDeletedCandidateAccount(Integer id);
    Response<CandidateResponseDTO> addCandidateAccount(AddCandidateRequestDTO request)
            throws IOException;
    Response<CandidateResponseDTO> updCandidateAccount(Integer id, UpdCandidateRequestDTO requestDTO);
    // ADMIN
    Response<List<AccountResponseDTO>> getAllActiveAccount();
    Response<List<AccountResponseDTO>> getAllDeletedAccount();
}
