package com.ducthong.TopCV.extract_data.repository;

import com.ducthong.TopCV.extract_data.entity.CvInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvInforRepository extends JpaRepository<CvInfor, String> {
}
