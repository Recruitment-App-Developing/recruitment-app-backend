package com.ducthong.TopCV.extract_data.repository;

import com.ducthong.TopCV.extract_data.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, String> {
}
