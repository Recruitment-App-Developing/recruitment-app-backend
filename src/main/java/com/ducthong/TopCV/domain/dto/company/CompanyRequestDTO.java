package com.ducthong.TopCV.domain.dto.company;

import lombok.Builder;

import java.util.List;
@Builder
public record CompanyRequestDTO(
     String name,
     String logo,
     String urlCom,
     String email,
     String phoneNumber,
     String employeeScale,
     String taxCode,
     List<Integer> activeFields,
     String briefIntro,
     String detailIntro,
     String headQuaters,
     List<String> subAddress
) {
}
