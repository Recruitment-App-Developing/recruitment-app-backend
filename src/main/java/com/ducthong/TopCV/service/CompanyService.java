package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.company.CompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.company.DetailCompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;

import java.util.List;

public interface CompanyService {
    MetaResponse<MetaResponseDTO, List<CompanyResponseDTO>> getListCompany(MetaRequestDTO requestDTO, String nameCom);
    BriefCompanyResponseDTO getBriefCompany(Integer companyId);
    DetailCompanyResponseDTO getDetailCompany(Integer companyId);
}
