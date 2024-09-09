package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.domain.entity.Company;

public interface CompanyMapper {
    BriefCompanyResponseDTO toBriefCompanyResponseDto(Company entity);
}
