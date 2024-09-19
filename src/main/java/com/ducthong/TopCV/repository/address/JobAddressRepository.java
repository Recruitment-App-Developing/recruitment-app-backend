package com.ducthong.TopCV.repository.address;

import com.ducthong.TopCV.domain.entity.address.JobAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAddressRepository extends JpaRepository<JobAddress, Integer> {
}
