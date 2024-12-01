package com.ducthong.TopCV.domain.dto.employer;

import java.util.Date;
import java.util.Map;

import com.ducthong.TopCV.domain.entity.address.Address;
import com.ducthong.TopCV.domain.enums.Gender;

import lombok.Builder;

@Builder
public record EmployerResponseDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        Gender gender,
        String dateOfBirth,
        String email,
        Map<String, String> avatar,
        String phoneNumber,
        Address address,
        String lastUpdated,
        Date lastLogIn,
        String whenCreated,
        Date whenDeleted,
        Integer verifiedLevel) {}
