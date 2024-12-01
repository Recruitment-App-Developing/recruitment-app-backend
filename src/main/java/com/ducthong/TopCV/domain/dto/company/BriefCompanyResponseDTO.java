package com.ducthong.TopCV.domain.dto.company;

import java.util.List;
import java.util.Map;

import lombok.Builder;

@Builder
public record BriefCompanyResponseDTO(
        Integer id,
        String name,
        String logo,
        String urlCom,
        String headquarters,
        List<Map<String, String>> activeFields,
        String employeeScale) {}
