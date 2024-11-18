package com.ducthong.TopCV.domain.dto.company;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record BriefCompanyResponseDTO(
        Integer id,
        String name,
        String logo,
        String urlCom,
        String headquarters,
        List<Map<String, String>> activeFields,
        String employeeScale) {}
