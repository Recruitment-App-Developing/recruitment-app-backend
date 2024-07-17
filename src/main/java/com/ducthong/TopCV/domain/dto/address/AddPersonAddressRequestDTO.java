package com.ducthong.TopCV.domain.dto.address;

public record AddPersonAddressRequestDTO(
        String detail,
        String provinceName,
        String provinceCode,
        String districtName,
        String districtCode,
        String wardName,
        String wardCode
) {
}
