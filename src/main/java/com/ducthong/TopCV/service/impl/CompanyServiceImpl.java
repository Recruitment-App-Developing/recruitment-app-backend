package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.configuration.AppConfig;
import com.ducthong.TopCV.constant.meta.MetaConstant;
import com.ducthong.TopCV.domain.dto.cloudinary.CloudinaryResponseDTO;
import com.ducthong.TopCV.domain.dto.company.*;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.SortingDTO;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.entity.address.CompanyAddress;
import com.ducthong.TopCV.domain.entity.address.Ward;
import com.ducthong.TopCV.domain.mapper.CompanyMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.CompanyRepository;
import com.ducthong.TopCV.repository.EmployerRepository;
import com.ducthong.TopCV.repository.IndustryRepository;
import com.ducthong.TopCV.repository.address.WardRepository;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.service.CompanyService;
import com.ducthong.TopCV.utility.GetRoleUtil;
import com.ducthong.TopCV.utility.TimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class CompanyServiceImpl implements CompanyService {
    // Repository
    private final EmployerRepository employerRepo;
    private final CompanyRepository companyRepo;
    private final IndustryRepository industryRepo;
    private final WardRepository wardRepo;
    // Service
    private final CloudinaryService cloudinaryService;
    // Mapper
    private final CompanyMapper companyMapper;
    // Varient
    private final AppConfig appConfig;

    @Override
    public Company isVerifyCompanyByAccountId(Integer accountId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        Company company = employer.getCompany();
        if (company == null) throw new AppException("This account is register company");

        return company;
    }

    @Override
    public MetaResponse<MetaResponseDTO, List<CompanyResponseDTO>> getListCompany(
            MetaRequestDTO metaRequestDTO, String nameCom) {
        Sort sort = metaRequestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(metaRequestDTO.sortField()).ascending()
                : Sort.by(metaRequestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), metaRequestDTO.pageSize(), sort);
        // if (nameCom == null) nameCo
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
    @Transactional
    public MetaResponse<MetaResponseDTO, List<CompanyResponseForEmployerDTO>> getListCompanyForEmployer(
            MetaRequestDTO metaRequestDTO) {
        Sort sort = metaRequestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(metaRequestDTO.sortField()).ascending()
                : Sort.by(metaRequestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), metaRequestDTO.pageSize(), sort);
        Page<Company> page = companyRepo.findAll(pageable);
        if (page.getContent().isEmpty()) throw new AppException("List company is empty");
        List<CompanyResponseForEmployerDTO> li = page.getContent().stream()
                .map(companyMapper::toCompanyResponseForEmployerDto)
                .toList();
        return MetaResponse.successfulResponse(
                "Get list company for employer success",
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
    @Transactional
    public MyCompanyResponseDTO getMyCompany(Integer accountId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);

        if (employer.getCompany() == null) throw new AppException("Tài khoản chưa đăng kí công ty");

        return companyMapper.toMyCompanyResponseDto(employer.getCompany());
    }

    @Override
    public BriefCompanyResponseDTO getBriefCompany(Integer companyId) {
        Optional<Company> findCompany = companyRepo.findById(companyId);
        if (findCompany.isEmpty()) throw new AppException("This company is not existed");

        Company company = findCompany.get();
        return companyMapper.toBriefCompanyResponseDto(company);
    }

    @Override
    @Transactional
    public DetailCompanyResponseDTO getDetailCompany(Integer companyId) {
        Optional<Company> findCompany = companyRepo.findById(companyId);
        if (findCompany.isEmpty()) throw new AppException("This company is not existed");

        Company company = findCompany.get();
        return companyMapper.toDetailCompanyResponseDto(company);
    }

    @Override
    @Transactional
    public CompanyRequestDTO addCompany(Integer accountId, CompanyRequestDTO requestDTO) throws IOException {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        if (employer.getCompany() != null) throw new AppException("Tài khoản này đã được đăng kí công ty");

        Company company = companyMapper.companyRequestDtoToCompany(requestDTO);
        // The active fields
        if (requestDTO.activeFields().isEmpty()) throw new AppException("Lĩnh vực hoạt động không được để trống");
        StringBuilder activeFields = new StringBuilder();
        requestDTO.activeFields().stream().forEach(item -> {
            if (industryRepo.findById(item).isEmpty()) throw new AppException("Lĩnh vực hoạt động không hợp lệ");
            activeFields.append(item.toString()).append(";");
        });
        activeFields.deleteCharAt(activeFields.length() - 1);
        company.setActiveFields(String.valueOf(activeFields));
        // Address
        // Headquaters
        try {
            String[] headQuatersTemp = requestDTO.headQuaters().split(";");
            Optional<Ward> wardOfHeadQuatersOptional = wardRepo.findById(headQuatersTemp[1]);
            if (wardOfHeadQuatersOptional.isEmpty())
                throw new AppException("Không tìm thấy Xã/ Phường cho địa chỉ chính này");
            Ward wardOfHeadQuaters = wardOfHeadQuatersOptional.get();
            CompanyAddress headQuaters = new CompanyAddress(headQuatersTemp[0], wardOfHeadQuaters);
            headQuaters.setIsMain(true);
            headQuaters.setCompany(company);
            company.getAddressList().add(headQuaters);
        } catch (AppException e) {
            throw new AppException("Địa chỉ trụ sở chính không hợp lệ");
        }
        // Sub Address
        if (!requestDTO.subAddress().isEmpty())
            requestDTO.subAddress().forEach(item -> {
                try {
                    String[] wardTemp = item.split(";");
                    Optional<Ward> wardOfSubAddressOptional = wardRepo.findById(wardTemp[1]);
                    if (wardOfSubAddressOptional.isEmpty())
                        throw new AppException("Không tìm thấy Xã/ Phường cho địa chỉ phụ này");
                    Ward wardOfSubAddress = wardOfSubAddressOptional.get();
                    CompanyAddress subAddress = new CompanyAddress(wardTemp[0], wardOfSubAddress);
                    subAddress.setCompany(company);
                    company.getAddressList().add(subAddress);
                } catch (AppException e) {
                    throw new AppException("Địa chỉ phụ này không hợp lệ");
                }
            });
        // Logo
        Image logo;
        if (requestDTO.logo() != null && !requestDTO.logo().isEmpty()) {
            CloudinaryResponseDTO logoUpload =
                    cloudinaryService.uploadFileBase64_v2(requestDTO.logo(), appConfig.getFOLDER_COMPANY_LOGO());
            logo = Image.builder()
                    .name("The logo of " + requestDTO.name())
                    .imageUrl(logoUpload.url())
                    .imagePublicId(logoUpload.public_id())
                    .whenCreated(TimeUtil.getDateTimeNow())
                    .build();
        } else {
            logo = Image.builder()
                    .name("defaul_company_logo")
                    .imageUrl(appConfig.getDEFAULT_COMPANY_LOGO())
                    .imagePublicId(null)
                    .whenCreated(TimeUtil.getDateTimeNow())
                    .build();
        }
        company.setLogo(logo);
        try {
            Company companySave = companyRepo.save(company);
            // Register Company for Employer
            employer.setCompany(companySave);
            employerRepo.save(employer);
            return companyMapper.toCompanyRequestDto(companySave);
        } catch (Exception e) {
            throw new AppException("Đăng kí công ty không thành công");
        }
    }
}
