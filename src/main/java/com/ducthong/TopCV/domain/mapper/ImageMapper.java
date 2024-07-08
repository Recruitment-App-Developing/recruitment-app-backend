package com.ducthong.TopCV.domain.mapper;

import java.util.Date;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.ducthong.TopCV.domain.dto.image.ImageRequestDTO;
import com.ducthong.TopCV.domain.entity.Image;

@Mapper
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    // ImageRequestDTO to Image============================================
    Image toImageEntity(ImageRequestDTO imageRequestDTO);

    @AfterMapping
    default void editImageEntity(@MappingTarget Image imageEntity, ImageRequestDTO imageRequestDTO) {
        imageEntity.setWhenCreated(new Date());
    }
}
