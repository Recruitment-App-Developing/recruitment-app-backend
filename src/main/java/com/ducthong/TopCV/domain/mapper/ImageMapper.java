package com.ducthong.TopCV.domain.mapper;

import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;
import com.ducthong.TopCV.domain.entity.Image;

public interface ImageMapper {
    ImageResponseDTO toImageResponseDto(Image entity);
}
