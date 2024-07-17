package com.ducthong.TopCV.domain.dto.authentication;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import lombok.Builder;

@Builder
public record LoginResponseDTO(
        Boolean authenticated,
        String token,
        AccountResponseDTO infor
) {
}
