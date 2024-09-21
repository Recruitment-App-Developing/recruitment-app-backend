package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;

public interface CompanyService {
    BriefCompanyResponseDTO getBriefCompany(Integer companyId);
}
