package com.ducthong.TopCV.domain.dto.employer;

import java.util.Date;

import com.ducthong.TopCV.domain.enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data for Update One Employer Request")
public record UpdEmployerRequestDTO(
        String firstName,
        String lastName,
        Gender gender,
        Date dateOfBirth,
        String email,
        String phoneNumber,
        Boolean isFindJob,
        Integer verifiedLevel) {}
