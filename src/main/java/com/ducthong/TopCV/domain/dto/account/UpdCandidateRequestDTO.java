package com.ducthong.TopCV.domain.dto.account;

import com.ducthong.TopCV.domain.enums.Gender;
import lombok.Getter;

import java.util.Date;

public record UpdCandidateRequestDTO(
        String firstName,
        String lastName,
        Gender gender,
        Date dateOfBirth,
        String email,
        String phoneNumber,
        Boolean isFindJob
) {
}
