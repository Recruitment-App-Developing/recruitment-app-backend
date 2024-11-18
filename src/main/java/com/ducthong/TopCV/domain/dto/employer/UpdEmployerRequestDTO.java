package com.ducthong.TopCV.domain.dto.employer;

import java.util.Date;
import java.util.Map;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.domain.enums.Gender;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data for Update One Employer Request")
public record UpdEmployerRequestDTO(
        String firstName,
        String lastName,
        Gender gender,
        @NotBlank(message = "Ngày sinh không được để trống")
        @JsonFormat(pattern = TimeFormatConstant.DATE_FORMAT)
        String dateOfBirth,
        String avatar,
        String address) {}
