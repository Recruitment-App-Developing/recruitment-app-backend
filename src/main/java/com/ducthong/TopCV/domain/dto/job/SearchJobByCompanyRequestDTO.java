package com.ducthong.TopCV.domain.dto.job;

import java.util.Objects;

import org.springframework.web.bind.annotation.RequestParam;

public record SearchJobByCompanyRequestDTO(
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "address", required = false) String address) {
    public String address() {
        return Objects.requireNonNullElse(this.address, "all");
    }
}
