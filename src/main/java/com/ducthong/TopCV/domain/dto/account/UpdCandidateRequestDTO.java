package com.ducthong.TopCV.domain.dto.account;

import java.util.Date;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.domain.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

public record UpdCandidateRequestDTO(
        String firstName,
        String lastName,
        Gender gender,
        @NotBlank(message = "Ngày sinh không được để trống")
        @JsonFormat(pattern = TimeFormatConstant.DATE_FORMAT)
        String dateOfBirth,
        String phoneNumber,
        String avatar,
        String address) {}
