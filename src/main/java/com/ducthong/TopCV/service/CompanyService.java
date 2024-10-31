package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.company.*;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.responses.MetaResponse;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    Company isVerifyCompanyByAccountId(Integer accountId);
    MetaResponse<MetaResponseDTO, List<CompanyResponseDTO>> getListCompany(MetaRequestDTO requestDTO, String nameCom);
    MetaResponse<MetaResponseDTO, List<CompanyResponseForEmployerDTO>> getListCompanyForEmployer(MetaRequestDTO requestDTO);
    MyCompanyResponseDTO getMyCompany(Integer accountId);
    BriefCompanyResponseDTO getBriefCompany(Integer companyId);
    DetailCompanyResponseDTO getDetailCompany(Integer companyId);
    CompanyRequestDTO addCompany(Integer accountId, CompanyRequestDTO requestDTO) throws IOException;
}
