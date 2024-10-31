package com.ducthong.TopCV.domain.dto.company;

import lombok.Builder;

@Builder
public record BriefCompanyResponseDTO(
        Integer id,
        String name,
        String logo,
        String urlCom,
        //String headquarters,
        String employeeScale) {}
