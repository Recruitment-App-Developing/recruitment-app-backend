package com.ducthong.TopCV.domain.mapper.impl;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.entity.address.Address;
import com.ducthong.TopCV.domain.entity.address.CompanyAddress;
import com.ducthong.TopCV.domain.mapper.CompanyMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.CompanyRepository;
import com.ducthong.TopCV.utility.AddressUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompanyMapperImpl implements CompanyMapper {
    private final CompanyRepository companyRepo;
    @Override
    public BriefCompanyResponseDTO toBriefCompanyResponseDto(Company entity) {
        System.out.println("Ok1");
        Optional<Address> address = companyRepo.getHeadquartersAddress(entity.getId());
        if (address.isEmpty()) throw new AppException("This company has not headquarters");
        System.out.println("Ok2");
        return BriefCompanyResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .logo(entity.getLogo().getImageUrl())
                .urlCom(entity.getUrlCom())
                .headquarters(AddressUtil.toString(address.get()))
                .employeeScale(entity.getEmployeeScale())
                .build();
    }
}
