package com.ducthong.TopCV.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationStatus {
    NEW("Mới"),
    VIEWED("Đã xem"),
    INTERVIEW_APPOINTMENT("Hẹn phỏng vấn"),
    CONTACT_ALLOW("Cho phép liên hệ"),
    FOLLOWING("Đang theo dõi"),
    SKIP("Bỏ qua");

    private final String title;
}
