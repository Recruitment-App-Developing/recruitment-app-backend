package com.ducthong.TopCV.domain.dto.company;

import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record DetailCompanyResponseDTO(
        Integer id,
        String name,
        ImageResponseDTO logo,
        String urlCom,
        String employeeScale,
        Integer numberOfFollow,
        String headQuaters,
        List<String> activeFields,
        List<String> subAddress,
        String detailIntro
) {
}
