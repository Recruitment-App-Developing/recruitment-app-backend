package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.company.CompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.company.DetailCompanyResponseDTO;
import com.ducthong.TopCV.domain.entity.Company;

public interface CompanyMapper {
    CompanyResponseDTO toCompanyResponseDto(Company entity);
    BriefCompanyResponseDTO toBriefCompanyResponseDto(Company entity);
    DetailCompanyResponseDTO toDetailCompanyResponseDto(Company entity);
}
