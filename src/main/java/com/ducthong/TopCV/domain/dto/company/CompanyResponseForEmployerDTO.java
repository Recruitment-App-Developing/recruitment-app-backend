package com.ducthong.TopCV.domain.dto.company;

import lombok.Builder;

import java.util.List;

@Builder
public record CompanyResponseForEmployerDTO(
        Integer id,
        String companyName,
        String logo,
        String taxCode,
        String headQuaters,
        List<String> activeFields
) {
}
