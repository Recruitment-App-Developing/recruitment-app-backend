package com.ducthong.TopCV.service;

import java.util.Map;

public interface StatisticService {
    Map<String, Object> statisticGeneralJobByIndustry();

    Map<String, Object> statisticGeneralJobByDay();

    Map<String, Object> statisticCompanyJobByIndustry(Integer accountId);

    Map<String, Object> statisticApplicationStatusByCompany(Integer accountId);

    Map<String, Object> statisticApplyCandidateByDay(Integer accountId);
}
