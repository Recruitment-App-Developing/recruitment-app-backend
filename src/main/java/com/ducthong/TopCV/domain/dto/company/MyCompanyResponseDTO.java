package com.ducthong.TopCV.domain.dto.company;

import lombok.Builder;

import java.util.List;

@Builder
public record MyCompanyResponseDTO(
        Integer id,
        String name,
        String logo,
        String phoneNumber,
        String taxCode,
        String urlCom,
        String employeeScale,
        String headQuaters,
        List<String> activeFields,
        List<String> subAddress,
        String detailIntro
) {
}
