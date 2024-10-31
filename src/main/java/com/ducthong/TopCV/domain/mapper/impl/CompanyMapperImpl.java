package com.ducthong.TopCV.domain.mapper.impl;

import com.ducthong.TopCV.domain.dto.company.*;
import com.ducthong.TopCV.domain.entity.Industry;
import com.ducthong.TopCV.domain.entity.address.CompanyAddress;
import com.ducthong.TopCV.domain.mapper.ImageMapper;
import com.ducthong.TopCV.repository.IndustryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.mapper.CompanyMapper;
import com.ducthong.TopCV.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompanyMapperImpl implements CompanyMapper {
    private final ImageMapper imageMapper;
    private final IndustryRepository industryRepo;
    // Variant
    @Value("${cloudinary.folder.default-company-logo}")
    private String DEFAULT_COMPANY_LOGO;
    @Override
    public CompanyResponseDTO toCompanyResponseDto(Company entity) {
        return CompanyResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .logo(entity.getLogo().getImageUrl())
                .detailIntro(entity.getDetailIntro())
                .urlCom(entity.getUrlCom())
                .build();
    }

    @Override
    public CompanyResponseForEmployerDTO toCompanyResponseForEmployerDto(Company entity) {
        // Logo
        String logo = null;
        if (entity.getLogo() != null) logo = entity.getLogo().getImageUrl();

        // Address
        Optional<CompanyAddress> companyAddress = entity.getAddressList().stream()
                .filter(CompanyAddress::getIsMain).findFirst();
        String address = companyAddress.map(value -> value.getDetail() + ", " + value.getWardName()
                + ", " + value.getDistrictName() + ", " + value.getProvinceName()).orElse(null);
        // Active Fields
        List<String> activeFields = new ArrayList<>();
        if (entity.getActiveFields() != null) {
            Arrays.stream(entity.getActiveFields().split(";")).forEach(
                    item->{
                        Optional<Industry> industry = industryRepo.findById(Integer.valueOf(item));
                        industry.ifPresent(value -> activeFields.add(value.getName()));
                    }
            );
        }
        return CompanyResponseForEmployerDTO.builder()
                .id(entity.getId())
                .companyName(entity.getName())
                .logo(logo)
                .taxCode(entity.getTaxCode())
                .headQuaters(address)
                .activeFields(activeFields)
                .build();
    }

    @Override
    public MyCompanyResponseDTO toMyCompanyResponseDto(Company entity) {
        // Logo
        String logo = null;
        if (entity.getLogo() != null) logo = entity.getLogo().getImageUrl();
        // Address
        Optional<CompanyAddress> companyAddress = entity.getAddressList().stream()
                .filter(CompanyAddress::getIsMain).findFirst();
        String address = companyAddress.map(value -> value.getDetail() + ", " + value.getWardName()
                + ", " + value.getDistrictName() + ", " + value.getProvinceName()).orElse(null);
        // Active Fields
        List<String> activeFields = new ArrayList<>();
        if (entity.getActiveFields() != null) {
            Arrays.stream(entity.getActiveFields().split(";")).forEach(
                    item->{
                        Optional<Industry> industry = industryRepo.findById(Integer.valueOf(item));
                        industry.ifPresent(value -> activeFields.add(value.getName()));
                    }
            );
        }
        return  MyCompanyResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .logo(logo)
                .phoneNumber(entity.getPhoneNumber())
                .taxCode(entity.getTaxCode())
                .urlCom(entity.getUrlCom())
                .employeeScale(entity.getEmployeeScale())
                .headQuaters(address)
                .subAddress(null) // TODO
                .activeFields(activeFields)
                .detailIntro(entity.getDetailIntro())
                .build();
    }

    @Override
    public BriefCompanyResponseDTO toBriefCompanyResponseDto(Company entity) {
            return BriefCompanyResponseDTO.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .logo(entity.getLogo().getImageUrl())
                    .urlCom(entity.getUrlCom())
                    .employeeScale(entity.getEmployeeScale())
                    .build();
    }

    @Override
    public DetailCompanyResponseDTO toDetailCompanyResponseDto(Company entity) {
        return DetailCompanyResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .logo(imageMapper.toImageResponseDto(entity.getLogo()))
                .urlCom(entity.getUrlCom())
                .employeeScale(entity.getEmployeeScale())
                .numberOfFollow(entity.getNumberOfFollow())
                .detailIntro(entity.getDetailIntro())
                .build();
    }

    @Override
    public Company companyRequestDtoToCompany(CompanyRequestDTO requestDTO) {
        return Company.builder()
                .name(requestDTO.name())
                .urlCom(requestDTO.urlCom())
                .email(requestDTO.email())
                .phoneNumber(requestDTO.phoneNumber())
                .employeeScale(requestDTO.employeeScale())
                .taxCode(requestDTO.taxCode())
                .briefIntro(requestDTO.briefIntro())
                .detailIntro(requestDTO.detailIntro())
                .addressList(new ArrayList<>())
                .employerList(new ArrayList<>())
                .build();
    }

    @Override
    public CompanyRequestDTO toCompanyRequestDto(Company entity) {
        return CompanyRequestDTO.builder()
                .name(entity.getName())
                .logo(entity.getLogo().getImageUrl())
                .urlCom(entity.getUrlCom())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .employeeScale(entity.getEmployeeScale())
                .taxCode(entity.getTaxCode())
                .activeFields(Arrays.stream(entity.getActiveFields().split(";")).map(
                        Integer::valueOf
                ).toList())
                .briefIntro(entity.getBriefIntro())
                .detailIntro(entity.getDetailIntro())
                .subAddress(entity.getAddressList().stream().map(
                        item -> item.getDetail()+", "+item.getWardName()+", "
                                +item.getDistrictName()+", "+item.getProvinceName()
                ).toList())
                .build();
    }
}
