package com.ducthong.TopCV.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WorkMethod {
    FULLTIME("Full-time", "Toàn thời gian"),
    PARTTIME("Part-time", "Bán thời gian"),
    DIRECTLY("Làm việc trực tiếp", "Làm việc tại văn phòng hoặc cơ sở công ty, dưới sự giám sát trực tiếp."),
    REMOTE("Làm việc từ xa", "Có thể hoàn thành công việc từ bất kỳ đâu"),
    HYBRID("Làm việc kết hợp", "Làm việc từ xa và yêu cầu làm tại văn phòng khi cần thiết"),
    PROJECT_BASED(
            "Làm việc theo dự án", "Làm việc dựa trên dự án cụ thể, hoàn thiện theo kết quả và thời gian cam kết"),
    FLEXIBLE(
            "Làm việc linh hoạt",
            " Cho phép tự chọn thời gian và địa điểm làm việc phù hợp với lịch trình cá nhân và công việc"),
    FREELANCE("Làm việc độc lập", "Hoàn thành một nhiệm vụ với chất lượng và thời gian đã thoả thuận");

    private final String name;
    private final String detail;
}
