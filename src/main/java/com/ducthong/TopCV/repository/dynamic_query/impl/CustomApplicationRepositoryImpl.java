package com.ducthong.TopCV.repository.dynamic_query.impl;

import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.repository.dynamic_query.CustomApplicationRepository;
import com.ducthong.TopCV.repository.dynamic_query.PagedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class CustomApplicationRepositoryImpl implements CustomApplicationRepository {
    private final EntityManager entityManager;
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
