package com.ducthong.TopCV.domain.dto.employer;

import java.util.Map;

public record EmployerHomePageResponseDTO(
        Map<String, Object> statisticGeneralJobByIndustry,
        Map<String, Object> statisticGeneralJobByLastestWeek,
        Map<String, Object> statisticCompanyJobByIndustry,
        Map<String, Object> statisticCompanyApplicationStatus,
        Map<String, Object> statisticCandidate,
        Map<String, Integer> effectiveApply,
        Map<String, String> employerInfor) {}
