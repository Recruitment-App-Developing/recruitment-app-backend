package com.ducthong.TopCV.repository;

import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvProfileRepository extends JpaRepository<CvProfile, String> {
}
