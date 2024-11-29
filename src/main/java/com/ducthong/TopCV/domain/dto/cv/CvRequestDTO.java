package com.ducthong.TopCV.domain.dto.cv;

import com.ducthong.TopCV.annotation.EnumValid;
import com.ducthong.TopCV.domain.enums.CvType;
import jakarta.validation.constraints.NotBlank;

public record CvRequestDTO(
        @NotBlank(message = "Tên CV không được để trống")
        String name,
        String cvFile,
        @EnumValid(name = "cvType", enumClass = CvType.class)
        String cvType
) {}
