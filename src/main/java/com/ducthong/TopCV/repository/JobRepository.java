package com.ducthong.TopCV.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.Job;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query(
            value =
                    "SELECT j FROM Job j WHERE (:name IS NULL OR :name = '' OR LOWER(j.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Job> getListJob(Pageable pageable, @Param("name") String name);

    @Query(value = "SELECT j FROM Job j WHERE j.company.id = :companyId")
    Page<Job> getListJobByCompany(Pageable pageable, @Param("companyId") Integer companyId);

    @Query(
            value = "SELECT j FROM Job j " + "JOIN JobAddress ja ON ja.job.id = j.id "
                    + "WHERE j.company.id = :companyId "
                    + "AND (:companyName IS NULL OR :companyName = '' OR j.name LIKE %:companyName%) "
                    + "AND (:address IS NULL OR :address = 'all' OR  ja.provinceCode = :address)")
    Page<Job> findListJobByCompany(
            Pageable pageable,
            @Param("companyId") Integer companyId,
            @Param("companyName") String companyName,
            @Param("address") String address);
    @Query(value = "select count(j.job_id) from jobs j where j.company_id = ?1 and j.is_active = ?2", nativeQuery = true)
    Integer countJobByIsActive(Integer comId, Boolean isActive);
    @Query(value = "select * from jobs j where j.company_id = ?1 and j.job_id in ?2", nativeQuery = true)
    List<Job> findJobByComIdAndListIds(Integer comId, List<Integer> jobIds);
    //    @Query(value = "SELECT new com.ducthong.TopCV.repository.objects.StatisticGeneralJobByIndustryObject( i.id,
    // i.name, COUNT(j.id)) " +
    //            "FROM Industry i JOIN i.jobs ij JOIN ij.job j " +
    //            "WHERE ij.isMain = true " +
    //            "GROUP BY i.id, i.name")
    //    List<StatisticJobByIndustryObject> statisticGeneralJobByIndustry();
}
