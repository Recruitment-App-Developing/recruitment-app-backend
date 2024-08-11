package com.ducthong.TopCV.domain.dto.authentication;

import lombok.Builder;

@Builder
public record IntrospectTokenResponseDTO(Boolean verifired, String subject) {}
