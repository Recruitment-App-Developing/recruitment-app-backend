package com.ducthong.TopCV.domain.dto.account;

import java.util.Date;

import com.ducthong.TopCV.domain.enums.Gender;

public record UpdCandidateRequestDTO(
        String firstName,
        String lastName,
        Gender gender,
        Date dateOfBirth,
        String email,
        String phoneNumber,
        Boolean isFindJob) {}
