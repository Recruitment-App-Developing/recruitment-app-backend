package com.ducthong.TopCV.domain.dto.company;

import lombok.Builder;

@Builder
public record CompanyResponseDTO(
        Integer id, String name, String logo, String banner, String urlCom, String detailIntro) {}
