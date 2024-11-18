package com.ducthong.TopCV.repository.address;

import com.ducthong.TopCV.domain.entity.address.PersonAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonAddressRepository extends JpaRepository<PersonAddress, Integer> {
}
