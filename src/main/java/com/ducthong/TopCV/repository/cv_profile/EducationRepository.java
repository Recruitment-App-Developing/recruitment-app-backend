package com.ducthong.TopCV.repository.cv_profile;

import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, String> {
}
