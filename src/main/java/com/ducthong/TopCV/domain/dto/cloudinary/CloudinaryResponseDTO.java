package com.ducthong.TopCV.domain.dto.cloudinary;

import lombok.Builder;

@Builder
public record CloudinaryResponseDTO(String name, String url, String public_id) {}
