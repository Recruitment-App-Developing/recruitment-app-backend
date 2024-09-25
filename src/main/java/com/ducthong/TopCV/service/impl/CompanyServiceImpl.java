package com.ducthong.TopCV.service.impl;

import java.util.List;
import java.util.Optional;

import com.ducthong.TopCV.constant.meta.MetaConstant;
import com.ducthong.TopCV.domain.dto.company.CompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.SortingDTO;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.responses.MetaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.mapper.CompanyMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.CompanyRepository;
import com.ducthong.TopCV.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    // Repository
    private final CompanyRepository companyRepo;
    // Service
    // Mapper
    private final CompanyMapper companyMapper;

    @Override
    public MetaResponse<MetaResponseDTO, List<CompanyResponseDTO>> getListCompany(MetaRequestDTO metaRequestDTO, String nameCom) {
        Sort sort = metaRequestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(metaRequestDTO.sortField()).ascending()
                : Sort.by(metaRequestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), metaRequestDTO.pageSize(), sort);
        //if (nameCom == null) nameCo
        Page<Company> page = companyRepo.getListCompany(pageable, nameCom);
        if (page.getContent().isEmpty()) throw new AppException("List company is empty");
        List<CompanyResponseDTO> li = page.getContent().stream()
                .map(temp -> companyMapper.toCompanyResponseDto(temp))
                .toList();
        return MetaResponse.successfulResponse(
                "Get list company success",
                MetaResponseDTO.builder()
                        .totalItems((int) page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(metaRequestDTO.currentPage())
                        .pageSize(metaRequestDTO.pageSize())
                        .sorting(SortingDTO.builder()
                                .sortField(metaRequestDTO.sortField())
                                .sortDir(metaRequestDTO.sortDir())
                                .build())
                        .build(),
                li);
    }

    @Override
    public BriefCompanyResponseDTO getBriefCompany(Integer companyId) {
        Optional<Company> findCompany = companyRepo.findById(companyId);
        if (findCompany.isEmpty()) throw new AppException("This company is not existed");

        Company company = findCompany.get();
        return companyMapper.toBriefCompanyResponseDto(company);
    }
}
