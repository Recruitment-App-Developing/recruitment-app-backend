package com.ducthong.TopCV.repository.cv_profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.CvProfile.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, String> {}
