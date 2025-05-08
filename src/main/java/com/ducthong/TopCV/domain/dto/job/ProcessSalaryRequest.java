package com.ducthong.TopCV.domain.dto.job;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessSalaryRequest {
    private String salaryType;

    private String salaryUnit;

    private Integer salaryFrom;

    private Integer salaryTo;

    public ProcessSalaryRequest(String salaryType, String salaryUnit, Integer salaryFrom, Integer salaryTo) {
        this.salaryType = salaryType;
        this.salaryUnit = salaryUnit;
        this.salaryFrom = salaryFrom;
        this.salaryTo = salaryTo;
    }
}
