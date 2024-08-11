package com.ducthong.TopCV.domain.mapper;

import java.util.Date;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.ducthong.TopCV.domain.dto.address.AddPersonAddressRequestDTO;
import com.ducthong.TopCV.domain.entity.address.PersonAddress;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    PersonAddress addRequestToPersonAddressEntity(AddPersonAddressRequestDTO requestDTO);

    @AfterMapping
    default void editAddPersonAddressRequestDto(
            @MappingTarget PersonAddress entity, AddPersonAddressRequestDTO request) {
        entity.setWhenCreated(new Date());
    }

    //    PersonAddress editRequestToPersonAddressEntity(AddPersonAddressRequestDTO requestDTO);
    //    @AfterMapping
    //    default void
}
