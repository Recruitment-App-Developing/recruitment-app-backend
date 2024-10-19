package com.ducthong.TopCV.domain.dto.job;

import com.ducthong.TopCV.annotation.EnumValid;
import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.domain.enums.ApplicationMethod;
import com.ducthong.TopCV.domain.enums.Gender;
import com.ducthong.TopCV.domain.enums.JobPosition;
import com.ducthong.TopCV.domain.enums.WorkMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record UpdJobRequestDTO(
        String name,
        @EnumValid(name = "jobPosition", enumClass = JobPosition.class)
        String jobPosition,
        @Min(value = 1, message = "The number of vacancy must be greater than 0")
        @Max(value = 100, message = "The number of vacancy must less than 100")
        @PositiveOrZero(message = "The number of vacancy must be a positive integer")
        Integer numberOfVacancy,
        @EnumValid(name = "workMethod", enumClass = WorkMethod.class)
        String workMethod,
        @EnumValid(name = "sexRequired", enumClass = Gender.class)
        String sexRequired,
        String salary,
        String jobExp,
        @NotBlank(message = "The application due time cannot blank")
        @JsonFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
        String applicationDueTime,
        String jobBenefit,
        String jobDescript,
        String jobRequirement,
        String addApplicationInfor,
        @EnumValid(name = "applicationMethod", enumClass = ApplicationMethod.class) String applicationMethod,
        Integer mainIndustry,
        List<Integer> subIndustries
) {
}
