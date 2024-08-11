package com.ducthong.TopCV.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.account.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {
    @Query(
            value = "SELECT e FROM Employer e " + "JOIN FETCH e.avatar " + "JOIN FETCH e.address "
                    + "WHERE e.id = :id AND e.deleted = false")
    Optional<Employer> findActiveAccountById(@Param(value = "id") Integer id);

    @Query(value = "SELECT e FROM Employer e " + "JOIN FETCH e.avatar " + "JOIN FETCH e.address " + "WHERE e.id = :id")
    Optional<Employer> findById(@Param(value = "id") Integer id);
}
