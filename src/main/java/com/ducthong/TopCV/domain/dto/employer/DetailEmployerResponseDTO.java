package com.ducthong.TopCV.domain.dto.employer;

import com.ducthong.TopCV.domain.entity.address.PersonAddress;
import com.ducthong.TopCV.domain.enums.Gender;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

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
        Integer verifiedLevel
) {
}
