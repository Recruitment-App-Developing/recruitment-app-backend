package com.ducthong.TopCV.domain.dto.job;

import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;

import java.util.List;
import java.util.Map;

public record UpdJobResponseDTO(
    Integer id,
    String name,
    List<Map<String, String>> address,
    String jobPosition,
    Integer numberOfVacancy,
    String workMethod,
    String sexRequired,
    String salary,
    String jobExp,
    String applicationDueTime,
    Integer numberOfApplicated,
    String jobBenefit,
    String jobDescript,
    String jobRequirement,
    String addApplicationInfor,
    String applicationMethod,
    List<ImageResponseDTO> imageList,
    Integer mainIndustry,
    List<Integer> subIndustries) {}