package com.ducthong.TopCV.domain.dto.image;

import lombok.Builder;

@Builder
public record ImageResponseDTO(
        Integer id,
        String imageUrl
) {
}
