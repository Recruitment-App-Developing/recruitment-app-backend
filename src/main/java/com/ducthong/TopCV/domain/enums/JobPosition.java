package com.ducthong.TopCV.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JobPosition {
    NHAN_VIEN("Nhân viên"),
    CHUYEN_VIEN("Chuyên viên"),
    QUAN_LY("Quản lý"),
    GIAM_DOC("Giám đốc"),
    TRUONG_PHONG("Trưởng phòng"),
    PHO_PHONG("Phó phòng"),
    TRUONG_NHOM("Trưởng nhóm"),
    GIAM_SAT("Giám sát"),
    THUC_TAP_SINH("Thực tập sinh"),
    FREELANCER("Freelancer");

    private final String name;
}
