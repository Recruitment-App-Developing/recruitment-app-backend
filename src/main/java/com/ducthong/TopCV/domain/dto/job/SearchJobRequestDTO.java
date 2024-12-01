package com.ducthong.TopCV.domain.dto.job;

import java.util.Objects;

import org.springframework.web.bind.annotation.RequestParam;

public record SearchJobRequestDTO(
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "address", required = false) String address,
        @RequestParam(name = "experienceRequired", required = false) String experienceRequired,
        @RequestParam(name = "workField", required = false) String workField,
        @RequestParam(name = "jobPosition", required = false) String jobPosition,
        @RequestParam(name = "workMethod", required = false) String workMethod) {
    public String address() {
        return Objects.requireNonNullElse(this.address, "all");
    }

    public String experienceRequired() {
        return Objects.requireNonNullElse(this.experienceRequired, "all");
    }

    public String workField() {
        return Objects.requireNonNullElse(this.workField, "all");
    }

    public String jobPosition() {
        return Objects.requireNonNullElse(this.jobPosition, "all");
    }

    public String workMethod() {
        return Objects.requireNonNullElse(this.workMethod, "all");
    }
}
