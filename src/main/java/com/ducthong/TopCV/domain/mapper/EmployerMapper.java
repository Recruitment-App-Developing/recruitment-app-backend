package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.employer.AddEmployerRequestDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.UpdEmployerRequestDTO;
import com.ducthong.TopCV.domain.entity.account.Employer;

public interface EmployerMapper {
    EmployerResponseDTO toEmployerResponseDto(Employer entity);

    Employer addEmployerDtoToEmployerEntity(AddEmployerRequestDTO requestDTO);

    Employer updEmployerDtoToEmployerEntity(Employer employerEntity, UpdEmployerRequestDTO requestDTO);
}
