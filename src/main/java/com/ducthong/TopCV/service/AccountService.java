package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.AddCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;

public interface AccountService {
    // CANDIDATE
    Response<CandidateResponseDTO> getActiveCandidateAccount(Integer id);

//    Response<CandidateResponseDTO> addCandidateAccount(AddCandidateRequestDTO request) throws IOException;

    Response<CandidateResponseDTO> updCandidateAccount(Integer id, UpdCandidateRequestDTO requestDTO);
    // ADMIN
    MetaResponse<MetaResponseDTO, List<AccountResponseDTO>> getAllActiveAccount(MetaRequestDTO requestDTO);

    Response<List<AccountResponseDTO>> getAllDeletedAccount();

    Response<?> deleteTempAccount(Integer id);

    Response<AccountResponseDTO> retrieveAccount(Integer id);

    Response<?> deletePermAccount(Integer id);
}
