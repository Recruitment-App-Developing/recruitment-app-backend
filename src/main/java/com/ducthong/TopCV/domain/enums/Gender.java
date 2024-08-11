package com.ducthong.TopCV.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {
    FEMALE("Nữ"),
    MALE("Nam"),
    NON("Không xác định");

    private final String name;
}
