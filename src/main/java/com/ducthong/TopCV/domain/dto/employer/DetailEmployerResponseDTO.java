package com.ducthong.TopCV.domain.dto.employer;

import java.util.Map;

import com.ducthong.TopCV.domain.entity.address.PersonAddress;
import com.ducthong.TopCV.domain.enums.Gender;

import lombok.Builder;

@Builder
public record DetailEmployerResponseDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        Gender gender,
        String dateOfBirth,
        String email,
        Map<String, String> avatar,
        String phoneNumber,
        PersonAddress address,
        String whenCreated,
        Integer verifiedLevel) {}
