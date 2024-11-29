package com.ducthong.TopCV.domain.mapper.impl;

import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.dto.cv.CvRequestDTO;
import com.ducthong.TopCV.domain.dto.cv.CvResponseDTO;
import com.ducthong.TopCV.domain.entity.CV;
import com.ducthong.TopCV.domain.enums.CvType;
import com.ducthong.TopCV.domain.mapper.CvMapper;
import com.ducthong.TopCV.utility.TimeUtil;

@Component
public class CvMapperImpl implements CvMapper {
    @Override
    public CvResponseDTO toCvResponseDto(CV entity) {
        return CvResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cvLink(entity.getCvLink())
                .cvType(entity.getCvType().name())
                .whenCreated(TimeUtil.toStringDateTime(entity.getWhenCreated()))
                .lastUpdated(TimeUtil.toStringDateTime(entity.getLastUpdated()))
                .build();
    }

    @Override
    public CV cvRequestDtoToEntity(CvRequestDTO requestDTO) {
        return CV.builder()
                .name(requestDTO.name())
                .cvType(CvType.valueOf(requestDTO.cvType()))
                .whenCreated(TimeUtil.getDateTimeNow())
                .lastUpdated(null)
                .isPublic(false)
                .build();
    }
}
