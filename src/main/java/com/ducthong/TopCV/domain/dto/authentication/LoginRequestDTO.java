package com.ducthong.TopCV.domain.dto.authentication;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "{auth.error.login.empty-username}") String username, String password) {}
