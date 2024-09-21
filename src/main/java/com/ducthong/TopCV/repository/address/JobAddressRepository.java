package com.ducthong.TopCV.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.address.JobAddress;

@Repository
public interface JobAddressRepository extends JpaRepository<JobAddress, Integer> {}
