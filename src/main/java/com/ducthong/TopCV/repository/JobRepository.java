package com.ducthong.TopCV.repository;

import com.ducthong.TopCV.domain.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query(value = "SELECT j FROM Job j WHERE (:name IS NULL OR :name = '' OR LOWER(j.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Job> getListJob(Pageable pageable, @Param("name") String name);
}
