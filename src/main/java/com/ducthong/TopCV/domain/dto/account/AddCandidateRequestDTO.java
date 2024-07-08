package com.ducthong.TopCV.domain.dto.account;

import java.util.Date;

import com.ducthong.TopCV.domain.enums.Gender;

public record AddCandidateRequestDTO(
        String username,
        String password,
        String firstName,
        String lastName,
        Gender gender,
        Date dateOfBirth,
        String email,
        String avatar,
        String phoneNumber,
        Boolean isFindJob) {}
