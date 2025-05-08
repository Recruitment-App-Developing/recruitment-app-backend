package com.ducthong.TopCV.domain.dto.application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequestDTO {
    private String applicationId;
    private String status;
}
