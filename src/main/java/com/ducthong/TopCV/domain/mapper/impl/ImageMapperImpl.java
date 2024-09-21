package com.ducthong.TopCV.domain.mapper.impl;

import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.mapper.ImageMapper;

@Component
public class ImageMapperImpl implements ImageMapper {
    @Override
    public ImageResponseDTO toImageResponseDto(Image entity) {
        return ImageResponseDTO.builder()
                .id(entity.getId())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
