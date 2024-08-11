package com.ducthong.TopCV.service;

import java.io.IOException;

import com.ducthong.TopCV.domain.dto.employer.AddEmployerRequestDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.UpdEmployerRequestDTO;
import com.ducthong.TopCV.responses.Response;

public interface EmployerService {
    Response<EmployerResponseDTO> getActiveEmployerAccount(Integer id);

    Response<EmployerResponseDTO> addEmployerAccount(AddEmployerRequestDTO requestDTO) throws IOException;

    Response<EmployerResponseDTO> updEmployerAccount(Integer id, UpdEmployerRequestDTO requestDTO);
}
