package com.ducthong.TopCV.domain.mapper.impl;

import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;
import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.mapper.ApplicationMapper;
import com.ducthong.TopCV.utility.TimeUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplicationMapperImpl implements ApplicationMapper {
    @Override
    public ApplicationResponseDTO toApplicationResponseDto(Application entity) {
        Company company = entity.getJob().getCompany();
        Job job = entity.getJob();

        return ApplicationResponseDTO.builder()
                .company(Map.of(
                        "id", company.getId().toString(),
                        "logo", company.getLogo().getImageUrl(),
                        "urlCom", company.getUrlCom(),
                        "name", company.getName()
                        ))
                .job(Map.of(
                        "id", job.getId().toString(),
                        "name", job.getName(),
                        "salary", job.getSalary()
                        ))
                .cvLink(entity.getCvLink())
                .applicationTime(TimeUtil.toStringDateTime(entity.getApplicationTime()))
                .applicationStatus(entity.getStatus().getTitle())
                .statusChangeTime(TimeUtil.toStringDateTime(entity.getStatusChangeTime()))
                .build();
    }
}
