package com.ducthong.TopCV.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.CV;

@Repository
public interface CvRepository extends JpaRepository<CV, String> {
    @Query(value = "select * from cvs c where c.candidate_id = ?1 AND c.cv_type = 'UPLOAD'",
            countQuery = "select count(c.cv_id) from cvs c where c.candidate_id = ?1 AND c.cv_type = 'UPLOAD'",
            nativeQuery = true
    )
    Page<CV> findCvByAccountId(Integer accountId, Pageable pageable);
}
