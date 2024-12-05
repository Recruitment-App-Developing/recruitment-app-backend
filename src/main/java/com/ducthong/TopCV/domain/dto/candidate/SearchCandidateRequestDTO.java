package com.ducthong.TopCV.domain.dto.candidate;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

public record SearchCandidateRequestDTO(
        @RequestParam(name = "username", required = false) String username,
        @RequestParam(name = "status", required = false) String status,
        @RequestParam(name = "startTime", required = false) String startTime,
        @RequestParam(name = "endTime", required = false) String endTime
) {
    public String status() {
        return Objects.requireNonNullElse(this.status, "all");
    }
    public String startTime() {
        return Objects.requireNonNullElse(this.startTime, "");
    }
    public String endTime() {
        return Objects.requireNonNullElse(this.endTime, "");
    }
}
