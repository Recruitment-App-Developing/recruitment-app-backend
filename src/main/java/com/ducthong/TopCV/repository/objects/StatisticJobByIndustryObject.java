package com.ducthong.TopCV.repository.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticJobByIndustryObject {
    private Integer industryId;
    private String name;
    private Long numberOfJob;

    public StatisticJobByIndustryObject(Integer industryId, String name, Long numberOfJob) {
        this.industryId = industryId;
        this.name = name;
        this.numberOfJob = numberOfJob;
    }
}
