package com.ducthong.TopCV.domain.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecruitmentEffectiveDTO {
    private Integer jobActive;
    private Integer jobInactive;
    private Integer cvSum;
    private Integer newCV;
}
