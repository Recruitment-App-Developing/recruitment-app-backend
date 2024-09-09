package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import org.springframework.stereotype.Service;

public interface CompanyService {
    BriefCompanyResponseDTO getBriefCompany(Integer companyId);
}
