package com.ducthong.TopCV.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    //    @Query(value = "SELECT ad.* FROM companies c " +
    //            "INNER JOIN company_addresses ca ON c.company_id = ca.company_id " +
    //            "INNER JOIN addresses ad ON ca.address_id = ad.address_id " +
    //            "WHERE ca.is_main = true AND c.company_id = :companyId LIMIT 1;", nativeQuery = true)
    //    @Query(value = "SELECT ad FROM Address ad " +
    //            "INNER JOIN CompanyAddress ca ON ca.id = ad.id " +
    //            "WHERE ca.isMain = true AND ca.id = :companyId", nativeQuery = false)
    //    Optional<Address> getHeadquartersAddress(@Param("companyId") Integer companyId);
}
