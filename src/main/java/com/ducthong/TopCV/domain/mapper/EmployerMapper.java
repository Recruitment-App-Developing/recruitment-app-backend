package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.employer.*;
import com.ducthong.TopCV.domain.entity.account.Employer;

public interface EmployerMapper {
    EmployerResponseDTO toEmployerResponseDto(Employer entity);

    DetailEmployerResponseDTO toDetailEmployerResponseDto(Employer entity);

    Employer addEmployerDtoToEmployerEntity(AddEmployerRequestDTO requestDTO);

    Employer updEmployerDtoToEmployerEntity(Employer employerEntity, UpdEmployerRequestDTO requestDTO);
}
