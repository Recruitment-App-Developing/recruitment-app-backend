package com.ducthong.TopCV.domain.mapper.impl;

import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;
import com.ducthong.TopCV.domain.dto.job.DetailJobResponseDTO;
import com.ducthong.TopCV.domain.dto.job.JobRequestDTO;
import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.enums.ApplicationMethod;
import com.ducthong.TopCV.domain.enums.Gender;
import com.ducthong.TopCV.domain.enums.JobPosition;
import com.ducthong.TopCV.domain.enums.WorkMethod;
import com.ducthong.TopCV.domain.mapper.ImageMapper;
import com.ducthong.TopCV.domain.mapper.JobMapper;
import com.ducthong.TopCV.utility.AddressUtil;
import com.ducthong.TopCV.utility.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JobMapperImpl implements JobMapper {
    private final ImageMapper imageMapper;
    @Override
    public JobResponseDTO toJobResponseDto(Job entity) {
        Company company = entity.getCompany();
        
        StringBuilder address = new StringBuilder();
        entity.getAddresses().forEach(item -> address.append(item.getProvinceName()).append(", "));
        if (entity.getAddresses().size() > 0) address.setLength(address.length() - 2);
        
        return JobResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cities(address.toString())
                .comapny(
                    Map.of(
                            "id", company.getId().toString(),
                            "name", company.getName(),
                            "urlCom", company.getUrlCom(),
                            "logo", company.getLogo().getImageUrl()
                    )
                )
                .salary(entity.getSalary())
                .build();
    }
    @Override
    public DetailJobResponseDTO toDetailJobResponseDto(Job entity) {
        List<String> jobAddress = entity.getAddresses().stream().map(
                item -> item.toString()
        ).toList();
        List<ImageResponseDTO> imageList = entity.getImageList().stream().map(
                item -> imageMapper.toImageResponseDto(item)
        ).toList();
        // Industry
        Map<String, Object> industry = new HashMap<>();
        List<Map<String, String>> subItems = new ArrayList<>();

        entity.getIndustries().forEach(
            item -> {
                if (item.getIsMain()) industry.put("isMain",
                        Map.of("id", item.getId().toString(),
                                "name", item.getIndustry().getName()));
                else subItems.add(Map.of("id", item.getId().toString(),
                        "name", item.getIndustry().getName()));
            }
        );
        industry.put("subItems", subItems);

        return DetailJobResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(jobAddress)
                .jobPosition(entity.getJobPosition().name())
                .numberOfVacancy(entity.getNumberOfVacancy())
                .workMethod(entity.getWorkMethod().name())
                .sexRequired(entity.getSexRequired().name())
                .salary(entity.getSalary())
                .jobExp(entity.getJobExp())
                .postingTime(TimeUtil.toStringDateTime(entity.getPostingTime()))
                .applicationDueTime(TimeUtil.toStringDateTime(entity.getApplicationDueTime()))
                .numberOfApplicated(entity.getNumberOfApplicated())
                .isVerified(entity.getIsVerified())
                .jobBenefit(entity.getJobBenefit())
                .jobDescript(entity.getJobDescript())
                .jobRequirement(entity.getJobRequirement())
                .addApplicationInfor(entity.getAddApplicationInfor())
                .lastUpdated(TimeUtil.toStringDateTime(entity.getLastUpdated()))
                .numberOfLike(entity.getNumberOfLike())
                .numberOfView(entity.getNumberOfView())
                .applicationMethod(entity.getApplicationMethod().name())
                .imageList(imageList)
                .industry(industry)
                .build();
    }

    @Override
    public Job jobRequestDtoToJobEntity(JobRequestDTO requestDTO) {
        return Job.builder()
                .name(requestDTO.name())
                //.address(requestDTO.address())
                .jobPosition(JobPosition.valueOf(requestDTO.jobPosition()))
                .numberOfVacancy(requestDTO.numberOfVacancy())
                .workMethod(WorkMethod.valueOf(requestDTO.workMethod()))
                .sexRequired(Gender.valueOf(requestDTO.sexRequired()))
                .salary(requestDTO.salary())
                .jobExp(requestDTO.jobExp())
                .postingTime(TimeUtil.getDateTimeNow())
                .applicationDueTime(TimeUtil.convertToDateTime(requestDTO.applicationDueTime()))
                .numberOfApplicated(0)
                .isVerified(false)
                .jobBenefit(requestDTO.jobBenefit())
                .jobDescript(requestDTO.jobDescript())
                .jobRequirement(requestDTO.jobRequirement())
                .addApplicationInfor(requestDTO.addApplicationInfor())
                .lastUpdated(null)
                .isActive(requestDTO.isActive())
                .isTempDeleted(false)
                .tempDeletedTime(null)
                .numberOfLike(0)
                .numberOfView(0)
                .applicationMethod(ApplicationMethod.valueOf(requestDTO.applicationMethod()))
                .build();
    }
}
