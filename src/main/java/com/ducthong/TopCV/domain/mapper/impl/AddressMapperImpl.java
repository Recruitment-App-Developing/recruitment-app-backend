package com.ducthong.TopCV.domain.mapper.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.dto.address.AddPersonAddressRequestDTO;
import com.ducthong.TopCV.domain.entity.address.JobAddress;
import com.ducthong.TopCV.domain.entity.address.PersonAddress;
import com.ducthong.TopCV.domain.entity.address.Ward;
import com.ducthong.TopCV.domain.mapper.AddressMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.address.WardRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressMapperImpl implements AddressMapper {
    private final WardRepository wardRepo;

    @Override
    public PersonAddress addRequestToPersonAddressEntity(AddPersonAddressRequestDTO requestDTO) {
        return null;
    }

    @Override
    public JobAddress toJobAddress(String detail, String wardCode) {

        Optional<Ward> wardOptional = wardRepo.findById(wardCode);
        if (wardOptional.isEmpty()) throw new AppException("This address is invalid");
        Ward ward = wardOptional.get();

        return JobAddress.builder()
                .detail(detail)
                .wardCode(ward.getCode())
                .wardName(ward.getName())
                .provinceCode(ward.getDistrict().getProvince().getCode())
                .provinceName(ward.getDistrict().getProvince().getName())
                .districtCode(ward.getDistrict().getCode())
                .districtName(ward.getDistrict().getName())
                .build();
    }
}
