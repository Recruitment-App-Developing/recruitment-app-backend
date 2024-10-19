package com.ducthong.TopCV.domain.dto.cv_profile;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;

public record EducationRequestDTO(
        String schoolName,
        String mainIndustry,
        @JsonFormat(pattern = TimeFormatConstant.MONTH_YEAR)
        String startTime,
        @JsonFormat(pattern = TimeFormatConstant.MONTH_YEAR)
        String endTime,
        String detail
) {
}
