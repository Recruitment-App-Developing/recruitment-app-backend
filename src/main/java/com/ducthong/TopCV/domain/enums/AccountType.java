package com.ducthong.TopCV.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AccountType {
    CANDIDATE("Ứng viên"),
    EMPLOYER("Nhà tuyển dụng"),
    ADMIN("Quản trị viên");

    private final String title;
}
