package com.ducthong.TopCV.repository;

import com.ducthong.TopCV.domain.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
}
