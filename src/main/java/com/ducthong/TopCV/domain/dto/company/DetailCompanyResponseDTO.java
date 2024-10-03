package com.ducthong.TopCV.domain.dto.company;

import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;
import lombok.Builder;

@Builder
public record DetailCompanyResponseDTO(
        Integer id,
        String name,
        ImageResponseDTO logo,
        String urlCom,
        Integer employeeScale,
        Integer numberOfFollow,
        String detailIntro
) {
}
