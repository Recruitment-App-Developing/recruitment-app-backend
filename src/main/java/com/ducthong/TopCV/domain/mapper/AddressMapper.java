package com.ducthong.TopCV.domain.mapper;

import java.util.Date;

import com.ducthong.TopCV.domain.entity.address.JobAddress;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.ducthong.TopCV.domain.dto.address.AddPersonAddressRequestDTO;
import com.ducthong.TopCV.domain.entity.address.PersonAddress;

public interface AddressMapper {
    PersonAddress addRequestToPersonAddressEntity(AddPersonAddressRequestDTO requestDTO);
    JobAddress toJobAddress(String address);
}
