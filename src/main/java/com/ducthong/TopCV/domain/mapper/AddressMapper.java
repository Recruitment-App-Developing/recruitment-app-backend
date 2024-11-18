package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.address.AddPersonAddressRequestDTO;
import com.ducthong.TopCV.domain.entity.address.JobAddress;
import com.ducthong.TopCV.domain.entity.address.PersonAddress;

public interface AddressMapper {
    PersonAddress addRequestToPersonAddressEntity(AddPersonAddressRequestDTO requestDTO);
    JobAddress toJobAddress(String detail, String wardCode);
    PersonAddress toPersonAddress(String detail, String wardCode);
}
