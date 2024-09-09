package com.ducthong.TopCV.domain.mapper;

import java.util.Date;

import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.ducthong.TopCV.domain.dto.image.ImageRequestDTO;
import com.ducthong.TopCV.domain.entity.Image;

public interface ImageMapper {
    ImageResponseDTO toImageResponseDto(Image entity);
}
