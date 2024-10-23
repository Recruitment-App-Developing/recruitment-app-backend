package com.ducthong.TopCV.domain.dto.job.job_address;

import lombok.Builder;

@Builder
public record JobAddressResponseDTO(
        Integer jobAddressId,
        String detail,
        String provinceCode,
        String provinceName,
        String districtCode,
        String districtName,
        String wardCode,
        String wardName
) {
}
