package com.ducthong.TopCV.repository;

import com.ducthong.TopCV.domain.entity.IndustryJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryJobRepository extends JpaRepository<IndustryJob, Long> {
}
