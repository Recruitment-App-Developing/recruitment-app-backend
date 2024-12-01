package com.ducthong.TopCV.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;

@Repository
public interface CvProfileRepository extends JpaRepository<CvProfile, String> {}
