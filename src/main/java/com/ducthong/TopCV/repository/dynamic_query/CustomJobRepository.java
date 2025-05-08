package com.ducthong.TopCV.repository.dynamic_query;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;

import com.ducthong.TopCV.domain.dto.job.SearchJobByCompanyRequestDTO;
import com.ducthong.TopCV.domain.dto.job.SearchJobRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.repository.objects.StatisticJobByIndustryObject;

public interface CustomJobRepository {
    PagedResponse searchJob(SearchJobRequestDTO requestDTO, MetaRequestDTO metaRequestDTO);

    PagedResponse<Job> searchJobByCompany(
            SearchJobByCompanyRequestDTO requestDTO, Integer companyId, MetaRequestDTO metaRequestDTO);

    List<StatisticJobByIndustryObject> statisticGeneralJobByIndustry();

    List<AbstractMap.SimpleEntry<LocalDate, Integer>> statisticGeneralJobByDay();

    List<StatisticJobByIndustryObject> statisticCompanyJobByIndustry(Integer companyId);

    List<AbstractMap.SimpleEntry<ApplicationStatus, Integer>> statisticApplicationStatusByCompany(Integer companyId);

    List<AbstractMap.SimpleEntry<LocalDate, Integer>> statisticApplyCandidateByDay(Integer companyId);
}
