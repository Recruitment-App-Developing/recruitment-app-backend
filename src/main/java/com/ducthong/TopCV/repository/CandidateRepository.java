package com.ducthong.TopCV.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.account.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    // CANDIDATE
    @Query(
            value = "SELECT c FROM Candidate c " + "JOIN FETCH c.avatar " + "JOIN FETCH c.address "
                    + "WHERE c.id = :id AND c.deleted = false")
    Optional<Candidate> findActiveAccountById(@Param(value = "id") Integer id);

    @Query(value = "SELECT c FROM Candidate c " + "JOIN FETCH c.avatar " + "JOIN FETCH c.address " + "WHERE c.id = :id")
    Optional<Candidate> findById(@Param(value = "id") Integer id);
}
