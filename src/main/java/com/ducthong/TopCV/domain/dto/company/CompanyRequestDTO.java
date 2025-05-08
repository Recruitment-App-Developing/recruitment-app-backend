package com.ducthong.TopCV.domain.dto.company;

import java.util.List;

import lombok.Builder;

@Builder
public record CompanyRequestDTO(
        String name,
        String logo,
        String banner,
        String urlCom,
        String email,
        String phoneNumber,
        String employeeScale,
        String taxCode,
        List<Integer> activeFields,
        String briefIntro,
        String detailIntro,
        String headQuaters,
        List<String> subAddress) {}
