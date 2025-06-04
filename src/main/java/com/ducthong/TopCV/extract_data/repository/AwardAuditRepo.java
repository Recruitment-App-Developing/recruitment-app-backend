package com.ducthong.TopCV.extract_data.repository;

import com.ducthong.TopCV.extract_data.entity.AwardAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AwardAuditRepo extends JpaRepository<AwardAudit, String> {
    @Query(value = "select * from award_audit aa where aa.id in ?1 and aa.rev = ?2 and revtype = 2", nativeQuery = true)
    List<AwardAudit> getlist(List<String> id, Integer rev);
}
