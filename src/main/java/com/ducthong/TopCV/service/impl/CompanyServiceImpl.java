package com.ducthong.TopCV.service.impl;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.mapper.CompanyMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.CompanyRepository;
import com.ducthong.TopCV.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    // Repository
    private final CompanyRepository companyRepo;
    // Service
    // Mapper
    private final CompanyMapper companyMapper;
    @Override
    public BriefCompanyResponseDTO getBriefCompany(Integer companyId) {
        Optional<Company> findCompany = companyRepo.findById(companyId);
        if (findCompany.isEmpty()) throw new AppException("This company is not existed");

        Company company = findCompany.get();
        return companyMapper.toBriefCompanyResponseDto(company);
    }
}
