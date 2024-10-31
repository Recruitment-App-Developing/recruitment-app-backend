package com.ducthong.TopCV.domain.dto.job;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record DetailJobPageResponseDTO(
        Integer id,
        String name,
        BriefCompanyResponseDTO company,
        List<String> address,
        String jobPosition,
        Integer numberOfVacancy,
        String workMethod,
        String sexRequired,
        String salary,
        String jobExp,
        String postingTime,
        String applicationDueTime,
        Integer numberOfApplicated,
        Boolean isVerified,
        String jobBenefit,
        String jobDescript,
        String jobRequirement,
        String addApplicationInfor,
        String lastUpdated,
        Integer numberOfLike,
        Integer numberOfView,
        String applicationMethod,
        Boolean isApply,
        List<String> imageList,
        Map<String, String> mainIndustry,
        List<Map<String, String>> subIndustries
) {
}
