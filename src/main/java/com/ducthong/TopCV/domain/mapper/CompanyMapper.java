package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.company.*;
import com.ducthong.TopCV.domain.entity.Company;

public interface CompanyMapper {
    CompanyResponseDTO toCompanyResponseDto(Company entity);
    CompanyResponseForEmployerDTO toCompanyResponseForEmployerDto(Company entity);
    MyCompanyResponseDTO toMyCompanyResponseDto(Company entity);
    BriefCompanyResponseDTO toBriefCompanyResponseDto(Company entity);
    DetailCompanyResponseDTO toDetailCompanyResponseDto(Company entity);
    Company companyRequestDtoToCompany(CompanyRequestDTO requestDTO);
    CompanyRequestDTO toCompanyRequestDto(Company entity);
}
