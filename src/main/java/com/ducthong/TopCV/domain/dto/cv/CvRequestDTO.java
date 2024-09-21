package com.ducthong.TopCV.domain.dto.cv;

import com.ducthong.TopCV.annotation.EnumValid;
import com.ducthong.TopCV.domain.enums.CvType;

public record CvRequestDTO(String cvFile, @EnumValid(name = "cvType", enumClass = CvType.class) String cvType) {}
