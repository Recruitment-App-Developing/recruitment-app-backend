package com.ducthong.TopCV.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JobPosition {
    NHAN_VIEN("Nhân viên"),
    CONG_TAC_VIEN("Cộng tác viên"),
    TRUONG_PHONG("Trưởng phòng"),
    QUAN_LY("Quản lý");

    private final String name;
}
