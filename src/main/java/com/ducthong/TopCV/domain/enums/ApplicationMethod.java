package com.ducthong.TopCV.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationMethod {
    ONLINE("Ứng tuyển Online", "Nộp CV và bấm vào ứng tuyển ngay"),
    OFFLINE("Ứng tuyển Offline", "Tham gia buổi phỏng vấn tại công ty");

    private final String name;
    private final String detail;
}
