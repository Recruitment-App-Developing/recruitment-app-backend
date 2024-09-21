package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.cv.CvRequestDTO;
import com.ducthong.TopCV.domain.dto.cv.CvResponseDTO;
import com.ducthong.TopCV.domain.entity.CV;

public interface CvMapper {
    CvResponseDTO toCvResponseDto(CV entity);

    CV cvRequestDtoToEntity(CvRequestDTO requestDTO);
}
