package com.ducthong.TopCV.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CvType {
    UPLOAD("Cv upload"),
    ONLINE("Cv online");

    private final String title;
}
