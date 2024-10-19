package com.ducthong.TopCV.domain.dto.cv_profile;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ExperienceRequestDTO(
        String companyName,
        String position,
        @JsonFormat(pattern = TimeFormatConstant.MONTH_YEAR)
        String startTime,
        @JsonFormat(pattern = TimeFormatConstant.MONTH_YEAR)
        String endTime,
        String detail
) {
}
