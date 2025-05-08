package com.ducthong.TopCV.domain.dto.company;

import java.util.List;

import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;

import lombok.Builder;

@Builder
public record DetailCompanyResponseDTO(
        Integer id,
        String name,
        ImageResponseDTO logo,
        String banner,
        String urlCom,
        String employeeScale,
        Integer numberOfFollow,
        String headQuaters,
        List<String> activeFields,
        List<String> subAddress,
        String detailIntro) {}
