package com.ducthong.TopCV.domain.dto.cv;

import jakarta.validation.constraints.NotBlank;

public record UpdCvRequestDTO(
        String id,
        @NotBlank(message = "Tên CV không được để trống")
        String cvName
) {}
