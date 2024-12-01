package com.ducthong.TopCV.repository.dynamic_query;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;

import com.ducthong.TopCV.domain.dto.job.SearchJobRequestDTO;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.repository.objects.StatisticJobByIndustryObject;

public interface CustomJobRepository {
    List<Job> searchJob(SearchJobRequestDTO requestDTO);

    List<StatisticJobByIndustryObject> statisticGeneralJobByIndustry();

    List<AbstractMap.SimpleEntry<LocalDate, Integer>> statisticGeneralJobByDay();

    List<StatisticJobByIndustryObject> statisticCompanyJobByIndustry(Integer companyId);

    List<AbstractMap.SimpleEntry<ApplicationStatus, Integer>> statisticApplicationStatusByCompany(Integer companyId);

    List<AbstractMap.SimpleEntry<LocalDate, Integer>> statisticApplyCandidateByDay(Integer companyId);
}
