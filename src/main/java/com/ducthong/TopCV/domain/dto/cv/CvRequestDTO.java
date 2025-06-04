package com.ducthong.TopCV.domain.dto.cv;

import jakarta.validation.constraints.NotBlank;

import com.ducthong.TopCV.annotation.EnumValid;
import com.ducthong.TopCV.domain.enums.CvType;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CvRequestDTO(
        @NotBlank(message = "Tên CV không được để trống") String name,
        MultipartFile cvFile,
        @EnumValid(name = "cvType", enumClass = CvType.class) String cvType) {}
