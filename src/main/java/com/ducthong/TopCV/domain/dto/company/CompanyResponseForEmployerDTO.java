package com.ducthong.TopCV.domain.dto.company;

import java.util.List;

import lombok.Builder;

@Builder
public record CompanyResponseForEmployerDTO(
        Integer id, String companyName, String logo, String taxCode, String headQuaters, List<String> activeFields) {}
