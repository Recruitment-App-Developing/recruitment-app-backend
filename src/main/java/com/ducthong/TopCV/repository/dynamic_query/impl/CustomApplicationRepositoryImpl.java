package com.ducthong.TopCV.repository.dynamic_query.impl;

import com.ducthong.TopCV.domain.dto.candidate.SearchCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.dynamic_query.CustomApplicationRepository;
import com.ducthong.TopCV.repository.dynamic_query.PagedResponse;
import com.ducthong.TopCV.utility.TimeUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CustomApplicationRepositoryImpl implements CustomApplicationRepository {
    private final EntityManager entityManager;

    @Override
    public PagedResponse<Application> searchCandidateByJob(SearchCandidateRequestDTO requestDTO, Integer jobId, MetaRequestDTO metaRequestDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Application> query = cb.createQuery(Application.class);
        Root<Application> root = query.from(Application.class);

        Join<Application, Candidate> application_candidate = root.join("candidate", JoinType.INNER);
        Join<Application, Job> application_job = root.join("job", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        Predicate jobPredicate = cb.equal(application_job.get("id"), jobId);
        predicates.add(jobPredicate);

        if (requestDTO.username() != null && !requestDTO.username().equals("")){
            String usernameParttern = "%" + requestDTO.username().toLowerCase() + "%";
            Predicate usernamePredicate = cb.like(application_candidate.get("username"), usernameParttern);
            predicates.add(usernamePredicate);
        }

        if (requestDTO.status() != null && !requestDTO.status().equals("") && !Objects.equals(requestDTO.status(), "all")) {
            try {
                ApplicationStatus status = ApplicationStatus.valueOf(requestDTO.status());
                Predicate statusPredicate = cb.equal(root.get("status"), status);
                predicates.add(statusPredicate);
            } catch (IllegalArgumentException e) {
                throw new AppException("Trạng thái ứng tuyển không hợp lệ");
            }
        }

        if (requestDTO.startTime() != null && requestDTO.startTime() != "" && requestDTO.endTime() != null && requestDTO.endTime() != "") {
            try {
                LocalDate startTime = LocalDate.parse(requestDTO.startTime());
                LocalDate endTime = LocalDate.parse(requestDTO.endTime());
                Expression<LocalDate> applicationDate = cb.function("DATE", LocalDate.class, root.get("applicationTime"));
                Predicate applicationTimePre = cb.between(applicationDate, startTime, endTime);
                predicates.add(applicationTimePre);
            } catch (Exception e) {
                System.out.println("Thời gian không hợp lệ");
            }
        }

        query.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.desc(root.get("applicationTime")));

        TypedQuery<Application> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(metaRequestDTO.currentPage() * metaRequestDTO.pageSize());
        typedQuery.setMaxResults(metaRequestDTO.pageSize());

        List<Application> results = typedQuery.getResultList();
        for (Application item : results)
            System.out.println(item.getId() +" | "+item.getCandidate().getId() + " | "+item.getCandidate().getUsername());
        // totalItems
        CriteriaQuery<Long> query1 = cb.createQuery(Long.class);
        Root<Application> root1 = query1.from(Application.class);

        Join<Application, Candidate> application_candidate1 = root1.join("candidate", JoinType.INNER);
        Join<Application, Job> application_job1 = root1.join("job", JoinType.INNER);

        List<Predicate> predicates1 = new ArrayList<>();
        Predicate jobPredicate1 = cb.equal(application_job1.get("id"), jobId);
        predicates1.add(jobPredicate1);

        if (requestDTO.username() != null && !requestDTO.username().equals("")){
            String usernameParttern = "%" + requestDTO.username().toLowerCase() + "%";
            Predicate usernamePredicate1 = cb.like(application_candidate1.get("username"), usernameParttern);
            predicates1.add(usernamePredicate1);
        }

        if (requestDTO.status() != null && !requestDTO.status().equals("") && !Objects.equals(requestDTO.status(), "all")) {
            try {
                ApplicationStatus status = ApplicationStatus.valueOf(requestDTO.status());
                Predicate statusPredicate1 = cb.equal(root1.get("status"), status);
                predicates1.add(statusPredicate1);
            } catch (IllegalArgumentException e) {
                throw new AppException("Trạng thái ứng tuyển không hợp lệ");
            }
        }

        if (requestDTO.startTime() != null && requestDTO.startTime() != "" && requestDTO.endTime() != null && requestDTO.endTime() != "") {
            try {
                LocalDate startTime = LocalDate.parse(requestDTO.startTime());
                LocalDate endTime = LocalDate.parse(requestDTO.endTime());
                Expression<LocalDate> applicationDate1 = cb.function("DATE", LocalDate.class, root1.get("applicationTime"));
                Predicate applicationTimePre1 = cb.between(applicationDate1, startTime, endTime);
                predicates1.add(applicationTimePre1);
            } catch (Exception e) {
                System.out.println("Thời gian không hợp lệ");
            }
        }

        query1.select(cb.count(root1)).where(predicates1.toArray(new Predicate[0]));
        long totalItems = entityManager.createQuery(query1).getSingleResult();
        int totalPages = (int) Math.ceil((double) totalItems / metaRequestDTO.pageSize());

        return PagedResponse.<Application>builder()
                .pageNumber(metaRequestDTO.currentPage())
                .pageSize(metaRequestDTO.pageSize())
                .totalPages(totalPages)
                .totalElements(totalItems)
                .content(results)
                .build();
    }

    @Override
    public PagedResponse<Application> getListApplicationByAccountId(Integer accountId, ApplicationStatus applicationStatus, Integer pageSize, Integer pageNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Application> query = cb.createQuery(Application.class);
        Root<Application> root = query.from(Application.class);

        Join<Application, Candidate> application_candidate = root.join("candidate", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        Predicate accountIdPredicate = cb.equal(application_candidate.get("id"), accountId);
        predicates.add(accountIdPredicate);

        if (applicationStatus != null)
            predicates.add(cb.equal(root.get("status"), applicationStatus));

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])))
                .orderBy(cb.desc(root.get("applicationTime")));
        TypedQuery<Application> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * pageSize);
        typedQuery.setMaxResults(pageSize);

        List<Application> res = typedQuery.getResultList();
        // Total Item
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Application> rootCount = countQuery.from(Application.class);

        Join<Application, Candidate> application_candidateCount = rootCount.join("candidate", JoinType.INNER);

        List<Predicate> predicatesCount = new ArrayList<>();
        Predicate accountIdPredicateCount = cb.equal(application_candidateCount.get("id"), accountId);
        predicatesCount.add(accountIdPredicateCount);

        if (applicationStatus != null)
            predicatesCount.add(cb.equal(rootCount.get("status"), applicationStatus));

        countQuery.select(cb.count(rootCount)).where(cb.and(predicatesCount.toArray(new Predicate[0])));
        long totalItems = entityManager.createQuery(countQuery).getSingleResult();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        return PagedResponse.<Application>builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .totalElements(totalItems)
                .content(res)
                .build();
    }
}
