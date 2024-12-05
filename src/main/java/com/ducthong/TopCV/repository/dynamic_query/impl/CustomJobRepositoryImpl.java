package com.ducthong.TopCV.repository.dynamic_query.impl;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.dto.job.SearchJobByCompanyRequestDTO;
import com.ducthong.TopCV.domain.dto.job.SearchJobRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.entity.*;
import com.ducthong.TopCV.domain.entity.address.JobAddress;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.domain.enums.JobPosition;
import com.ducthong.TopCV.domain.enums.WorkMethod;
import com.ducthong.TopCV.domain.mapper.JobMapper;
import com.ducthong.TopCV.repository.dynamic_query.CustomJobRepository;
import com.ducthong.TopCV.repository.dynamic_query.PagedResponse;
import com.ducthong.TopCV.repository.objects.StatisticJobByIndustryObject;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomJobRepositoryImpl implements CustomJobRepository {
    private final EntityManager entityManager;
    private final JobMapper jobMapper;

    @Override
    public List<Job> searchJob(SearchJobRequestDTO requestDTO) {
        // todo
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Job> query = cb.createQuery(Job.class);
        Root<Job> root = query.from(Job.class);

        Join<Job, Company> job_company = root.join("company", JoinType.INNER);
        Join<Job, IndustryJob> job_IndustryJob = root.join("industries", JoinType.INNER);
        Join<IndustryJob, Industry> industryJob_Industry = job_IndustryJob.join("industry", JoinType.INNER);
        Join<Job, JobAddress> job_JobAddress = root.join("addresses", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        System.out.println(requestDTO.keyword());
        if (!Objects.equals(requestDTO.keyword(), null) && !Objects.equals(requestDTO.keyword(), "")) {
            String keywordParttern = "%" + requestDTO.keyword().toLowerCase() + "%";
            Predicate namePredicate = cb.like(cb.lower(root.get("name")), keywordParttern);
            Predicate nameCompanyPredicate = cb.like(cb.lower(job_company.get("name")), keywordParttern);
            predicates.add(cb.or(namePredicate, nameCompanyPredicate));
        }

        System.out.println(requestDTO.address().toString());
        if (!Objects.equals(requestDTO.address(), "") && !Objects.equals(requestDTO.address(), "all")) {
            predicates.add(cb.equal(job_JobAddress.get("provinceCode"), requestDTO.address()));
            System.out.println("Address");
        }

        System.out.println(requestDTO.experienceRequired().toString());
        if (!Objects.equals(requestDTO.experienceRequired(), "")
                && !Objects.equals(requestDTO.experienceRequired(), "all")) {
            predicates.add(cb.equal(root.get("workMethod"), WorkMethod.valueOf(requestDTO.workMethod())));
            System.out.println("Work method");
        }

        System.out.println(requestDTO.workMethod().toString());
        if (!Objects.equals(requestDTO.workMethod(), "") && !Objects.equals(requestDTO.workMethod(), "all")) {
            predicates.add(cb.equal(root.get("workMethod"), WorkMethod.valueOf(requestDTO.workMethod())));
            System.out.println("Work method");
        }

        System.out.println(requestDTO.jobPosition().toString());
        if (!Objects.equals(requestDTO.jobPosition(), "") && !Objects.equals(requestDTO.jobPosition(), "all")) {
            predicates.add(cb.equal(root.get("jobPosition"), JobPosition.valueOf(requestDTO.jobPosition())));
            System.out.println("Job Position");
        }

        System.out.println(requestDTO.workField());
        if (requestDTO.workField() != null
                && !Objects.equals(requestDTO.workField(), "")
                && !Objects.equals(requestDTO.workField(), "all")) {
            predicates.add(cb.equal(industryJob_Industry.get("id"), requestDTO.workField()));
            System.out.println("Work Field");
        }

        query.select(root)
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .groupBy(
                        root.get("id"),
                        root.get("name"),
                        job_JobAddress.get("provinceCode"),
                        industryJob_Industry.get("id"));

        List<Job> results = entityManager.createQuery(query).getResultList();
        return results;
    }

    @Override
    public PagedResponse<Job> searchJobByCompany(
            SearchJobByCompanyRequestDTO requestDTO, Integer companyId, MetaRequestDTO metaRequestDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Job> query = cb.createQuery(Job.class);
        Root<Job> root = query.from(Job.class);

        Join<Job, Company> job_company = root.join("company", JoinType.INNER);
        Join<Job, JobAddress> job_JobAddress = root.join("addresses", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        Predicate companyPredicate = cb.equal(job_company.get("id"), companyId);
        predicates.add(companyPredicate);

        if (!Objects.equals(requestDTO.keyword(), null) && !Objects.equals(requestDTO.keyword(), "")) {
            String keywordParttern = "%" + requestDTO.keyword().toLowerCase() + "%";
            Predicate namePredicate = cb.like(cb.lower(root.get("name")), keywordParttern);
            predicates.add(namePredicate);
        }

        if (!Objects.equals(requestDTO.address(), "") && !Objects.equals(requestDTO.address(), "all"))
            predicates.add(cb.equal(job_JobAddress.get("provinceCode"), requestDTO.address()));

        query.select(root)
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .orderBy(cb.desc(root.get("postingTime")))
                .distinct(true);
        TypedQuery<Job> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(metaRequestDTO.currentPage() * metaRequestDTO.pageSize());
        typedQuery.setMaxResults(metaRequestDTO.pageSize());

        List<Job> results = typedQuery.getResultList();

        // Total Items
        CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
        Root<Job> rootCount = queryCount.from(Job.class);

        Join<Job, Company> job_companyCount = rootCount.join("company", JoinType.INNER);
        Join<Job, JobAddress> job_JobAddressCount = rootCount.join("addresses", JoinType.INNER);

        List<Predicate> predicatesCount = new ArrayList<>();

        Predicate companyPredicateCount = cb.equal(job_companyCount.get("id"), companyId);
        predicatesCount.add(companyPredicateCount);

        if (!Objects.equals(requestDTO.keyword(), null) && !Objects.equals(requestDTO.keyword(), "")) {
            String keywordParttern = "%" + requestDTO.keyword().toLowerCase() + "%";
            Predicate namePredicate = cb.like(cb.lower(rootCount.get("name")), keywordParttern);
            predicatesCount.add(namePredicate);
        }

        if (!Objects.equals(requestDTO.address(), "") && !Objects.equals(requestDTO.address(), "all"))
            predicatesCount.add(cb.equal(job_JobAddressCount.get("provinceCode"), requestDTO.address()));

        queryCount.select(cb.countDistinct(rootCount)).where(cb.and(predicatesCount.toArray(new Predicate[0])));

        long totalItems = entityManager.createQuery(queryCount).getSingleResult();
        int totalPages = (int) Math.ceil((double) totalItems / metaRequestDTO.pageSize());

        return PagedResponse.<Job>builder()
                .pageNumber(metaRequestDTO.currentPage())
                .pageSize(metaRequestDTO.pageSize())
                .totalPages(totalPages)
                .totalElements(totalItems)
                .content(results)
                .build();
    }

    @Override
    public List<StatisticJobByIndustryObject> statisticGeneralJobByIndustry() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<StatisticJobByIndustryObject> query = cb.createQuery(StatisticJobByIndustryObject.class);
        Root<Industry> root = query.from(Industry.class);

        Join<Industry, IndustryJob> industry_IndutryJob = root.join("jobs", JoinType.INNER);
        Join<IndustryJob, Job> industryJob_Job = industry_IndutryJob.join("job", JoinType.INNER);

        Predicate isMain = cb.equal(industry_IndutryJob.get("isMain"), true);
        query.groupBy(root.get("id"), root.get("name"));

        query.select(cb.construct(
                        StatisticJobByIndustryObject.class,
                        root.get("id"),
                        root.get("name"),
                        cb.count(industryJob_Job.get("id"))))
                .where(isMain);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<AbstractMap.SimpleEntry<LocalDate, Integer>> statisticGeneralJobByDay() {
        int n = 30;

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Job> root = query.from(Job.class);
        Expression<LocalDate> jobDate = cb.function("DATE", LocalDate.class, root.get("postingTime"));
        Expression<Long> jobCount = cb.count(root.get("id"));

        query.multiselect(jobDate, jobCount).groupBy(jobDate).orderBy(cb.desc(jobDate));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(n);
        typedQuery.setFirstResult(0);

        List<Object[]> results = typedQuery.getResultList();

        // Create list day
        List<LocalDate> dateRange = new LinkedList<>();
        LocalDate today = LocalDate.now();
        for (long i = n - 1; i >= 0; i--) dateRange.add(today.minusDays(i));

        List<AbstractMap.SimpleEntry<LocalDate, Integer>> finalResults = new ArrayList<>();
        for (LocalDate date : dateRange) {
            boolean flag = false;
            for (Object[] item : results) {
                if (date.equals(item[0])) {
                    finalResults.add(new AbstractMap.SimpleEntry<>(date, ((Long) item[1]).intValue()));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                finalResults.add(new AbstractMap.SimpleEntry<>(date, 0));
            }
        }

        return finalResults;
    }

    @Override
    public List<StatisticJobByIndustryObject> statisticCompanyJobByIndustry(Integer companyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<StatisticJobByIndustryObject> query = cb.createQuery(StatisticJobByIndustryObject.class);
        Root<Industry> root = query.from(Industry.class);

        Join<Industry, IndustryJob> industry_IndutryJob = root.join("jobs", JoinType.INNER);
        Join<IndustryJob, Job> industryJob_Job = industry_IndutryJob.join("job", JoinType.INNER);
        Join<Job, Company> job_company = industryJob_Job.join("company", JoinType.INNER);

        Predicate isMain = cb.equal(industry_IndutryJob.get("isMain"), true);
        Predicate company = cb.equal(job_company.get("id"), companyId);

        query.groupBy(root.get("id"), root.get("name"));

        query.select(cb.construct(
                        StatisticJobByIndustryObject.class,
                        root.get("id"),
                        root.get("name"),
                        cb.count(industryJob_Job.get("id"))))
                .where(cb.and(isMain, company));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<AbstractMap.SimpleEntry<ApplicationStatus, Integer>> statisticApplicationStatusByCompany(
            Integer companyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Application> root = query.from(Application.class);

        Join<Application, Job> application_job = root.join("job", JoinType.INNER);
        Join<Job, Company> job_company = application_job.join("company", JoinType.INNER);

        Predicate company = cb.equal(job_company.get("id"), companyId);

        Expression<Long> statusCount = cb.count(application_job.get("id"));

        query.groupBy(root.get("status"));
        query.multiselect(root.get("status"), statusCount).where(company);

        List<Object[]> results = entityManager.createQuery(query).getResultList();

        List<AbstractMap.SimpleEntry<ApplicationStatus, Integer>> finalResults = new ArrayList<>();
        for (Object[] item : results)
            finalResults.add(new AbstractMap.SimpleEntry<>((ApplicationStatus) item[0], ((Long) item[1]).intValue()));

        return finalResults;
    }

    @Override
    public List<AbstractMap.SimpleEntry<LocalDate, Integer>> statisticApplyCandidateByDay(Integer companyId) {
        int n = 30;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Application> root = query.from(Application.class);

        Join<Application, Job> application_job = root.join("job", JoinType.INNER);
        Join<Job, Company> job_company = application_job.join("company", JoinType.INNER);

        Predicate company = cb.equal(job_company.get("id"), companyId);

        Expression<LocalDate> applicationDate = cb.function("DATE", LocalDate.class, root.get("applicationTime"));
        Expression<Long> applicationCount = cb.count(root.get("id"));

        query.multiselect(applicationDate, applicationCount)
                .groupBy(applicationDate)
                .where(company);

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(n);
        typedQuery.setFirstResult(0);

        List<Object[]> results = typedQuery.getResultList();

        List<LocalDate> dateRange = new LinkedList<>();
        LocalDate today = LocalDate.now();
        for (long i = n - 1; i >= 0; i--) dateRange.add(today.minusDays(i));

        List<AbstractMap.SimpleEntry<LocalDate, Integer>> finalResults = new ArrayList<>();

        for (LocalDate date : dateRange) {
            boolean flag = false;
            for (Object[] item : results) {
                if (date.equals(item[0])) {
                    finalResults.add(new AbstractMap.SimpleEntry<>(date, ((Long) item[1]).intValue()));
                    flag = true;
                    break;
                }
            }
            if (!flag) finalResults.add(new AbstractMap.SimpleEntry<>(date, 0));
        }

        return finalResults;
    }
}
