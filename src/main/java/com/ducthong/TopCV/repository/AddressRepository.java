package com.ducthong.TopCV.repository;

import com.ducthong.TopCV.domain.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
