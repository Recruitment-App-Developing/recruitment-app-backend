package com.ducthong.TopCV.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.address.JobAddress;

@Repository
public interface JobAddressRepository extends JpaRepository<JobAddress, Integer> {
    @Modifying
    @Query(value = "DELETE FROM JobAddress ja WHERE ja.id = :jobAddressId")
    void deleteById(@Param("jobAddressId") Integer jobAddressId);
}
