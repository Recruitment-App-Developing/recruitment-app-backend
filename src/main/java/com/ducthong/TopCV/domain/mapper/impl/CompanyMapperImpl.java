package com.ducthong.TopCV.domain.mapper.impl;

import com.ducthong.TopCV.domain.dto.company.CompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.company.DetailCompanyResponseDTO;
import com.ducthong.TopCV.domain.mapper.ImageMapper;
import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.mapper.CompanyMapper;
import com.ducthong.TopCV.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyMapperImpl implements CompanyMapper {
    private final ImageMapper imageMapper;
    private final CompanyRepository companyRepo;

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
    public BriefCompanyResponseDTO toBriefCompanyResponseDto(Company entity) {
        //        Optional<Address> address = companyRepo.getHeadquartersAddress(entity.getId());
        //        if (address.isEmpty()) throw new AppException("This company has not headquarters");
        //        return BriefCompanyResponseDTO.builder()
        //                .id(entity.getId())
        //                .name(entity.getName())
        //                .logo(entity.getLogo().getImageUrl())
        //                .urlCom(entity.getUrlCom())
        //                .headquarters(AddressUtil.toString(address.get()))
        //                .employeeScale(entity.getEmployeeScale())
        //                .build();
        // TODO
        return null;
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
}
