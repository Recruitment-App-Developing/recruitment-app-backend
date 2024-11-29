package com.ducthong.TopCV.service;

import java.io.IOException;

import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.AddEmployerRequestDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerHomePageResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.UpdEmployerRequestDTO;
import com.ducthong.TopCV.responses.Response;

public interface EmployerService {
    EmployerHomePageResponseDTO homePageForEmployer(Integer accountId);
    Response<EmployerResponseDTO> getActiveEmployerAccount(Integer accountId);
    LoginResponseDTO registerEmployerAccount(AddEmployerRequestDTO requestDTO) throws IOException;
    Response<EmployerResponseDTO> updEmployerAccount(Integer acountId, UpdEmployerRequestDTO requestDTO) throws IOException;
    void registerCompany(Integer accountId, Integer companyId);
}
