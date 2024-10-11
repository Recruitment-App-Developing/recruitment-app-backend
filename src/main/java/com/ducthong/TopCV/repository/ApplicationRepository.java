package com.ducthong.TopCV.repository;

import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Application a WHERE a.job.id = :jobId AND a.candidate.id = :accountId")
    Boolean checkAccountAppliedJob(@Param("jobId") Integer jobId, @Param("accountId") Integer accountId);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.company.id = :companyId AND ( :status IS NULL OR a.status = :status)")
    Integer statisticByStatus(@Param("companyId") Integer companyId , @Param("status") ApplicationStatus status);
}
