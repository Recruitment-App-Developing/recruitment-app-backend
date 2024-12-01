package com.ducthong.TopCV.domain.dto.job;

import java.util.List;

import com.ducthong.TopCV.domain.dto.company.CompanyResponseDTO;

import lombok.Builder;

@Builder
public record RelatedJobResponseDTO(
        Integer id,
        String name,
        List<String> provinces,
        CompanyResponseDTO company,
        String salary,
        Integer applyTime,
        Integer lastUpdated,
        Boolean isApply) {}
