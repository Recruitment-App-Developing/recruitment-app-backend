package com.ducthong.TopCV.service.impl;

import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Service;

import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.repository.dynamic_query.CustomJobRepository;
import com.ducthong.TopCV.repository.objects.StatisticJobByIndustryObject;
import com.ducthong.TopCV.service.StatisticService;
import com.ducthong.TopCV.utility.GetRoleUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final CustomJobRepository customJobRepo;

    @Override
    public Map<String, Object> statisticGeneralJobByIndustry() {
        List<StatisticJobByIndustryObject> li = customJobRepo.statisticGeneralJobByIndustry();

        Map<String, Object> result = new HashMap<>();

        List<Long> data = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        li.forEach(item -> {
            data.add(item.getNumberOfJob());
            categories.add(item.getName());
        });

        result.put("data", data);
        result.put("categories", categories);

        return result;
    }

    @Override
    public Map<String, Object> statisticGeneralJobByDay() {
        List<AbstractMap.SimpleEntry<LocalDate, Integer>> li = customJobRepo.statisticGeneralJobByDay();

        Map<String, Object> results = new HashMap<>();

        List<String> categories = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        li.forEach(item -> {
            categories.add(item.getKey().toString());
            data.add(item.getValue());
        });

        results.put("categories", categories);
        results.put("data", data);

        return results;
    }

    @Override
    public Map<String, Object> statisticCompanyJobByIndustry(Integer accountId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        if (employer.getCompany() == null) return null;
        Company company = employer.getCompany();
        List<StatisticJobByIndustryObject> li = customJobRepo.statisticCompanyJobByIndustry(company.getId());

        Map<String, Object> result = new HashMap<>();

        List<Long> data = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        li.forEach(item -> {
            data.add(item.getNumberOfJob());
            categories.add(item.getName());
        });

        result.put("data", data);
        result.put("categories", categories);

        return result;
    }

    @Override
    public Map<String, Object> statisticApplicationStatusByCompany(Integer accountId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        if (employer.getCompany() == null) return null;
        Company company = employer.getCompany();

        List<AbstractMap.SimpleEntry<ApplicationStatus, Integer>> li =
                customJobRepo.statisticApplicationStatusByCompany(company.getId());

        Map<String, Object> results = new HashMap<>();

        List<Integer> data = new ArrayList<>();
        List<Integer> realData = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        if (!li.isEmpty()) {
            int count = 0;
            for (AbstractMap.SimpleEntry<ApplicationStatus, Integer> item : li) count += item.getValue();

            for (AbstractMap.SimpleEntry<ApplicationStatus, Integer> item : li) {
                realData.add(item.getValue());
                data.add(Math.round(((float) (item.getValue()) / count) * 100));
                categories.add(item.getKey().getTitle());
            }
        }

        results.put("realData", realData);
        results.put("data", data);
        results.put("categories", categories);

        return results;
    }

    @Override
    public Map<String, Object> statisticApplyCandidateByDay(Integer accountId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        if (employer.getCompany() == null) return null;
        Company company = employer.getCompany();

        List<AbstractMap.SimpleEntry<LocalDate, Integer>> li =
                customJobRepo.statisticApplyCandidateByDay(company.getId());

        Map<String, Object> results = new HashMap<>();

        List<String> categories = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        li.forEach(item -> {
            categories.add(item.getKey().toString());
            data.add(item.getValue());
        });

        results.put("categories", categories);
        results.put("data", data);

        return results;
    }
}
