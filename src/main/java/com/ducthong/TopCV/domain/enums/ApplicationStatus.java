package com.ducthong.TopCV.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationStatus {
    NEW("Mới"),
    VIEWED("Đã xem"),
    INTERVIEW_APPOINTMENT("Hẹn phỏng vấn"),
    INTERVIEWED("Đã phỏng vấn"),
    OFFERED("Đã gửi offer"),
    HIRED("Đã nhận việc"),
    SKIP("Bỏ qua");

    private final String title;
}
