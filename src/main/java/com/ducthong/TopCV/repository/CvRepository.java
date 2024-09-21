package com.ducthong.TopCV.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.CV;

@Repository
public interface CvRepository extends JpaRepository<CV, String> {}
