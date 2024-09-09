package com.ducthong.TopCV.domain.dto.job;

import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record DetailJobResponseDTO(
    Integer id,
    String name,
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
    List<ImageResponseDTO> imageList,
    Integer industryId
) {
}
