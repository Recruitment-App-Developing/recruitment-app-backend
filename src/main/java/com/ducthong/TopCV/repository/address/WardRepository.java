package com.ducthong.TopCV.repository.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.address.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
    @Query("SELECT w FROM Ward w WHERE w.code = :wardCode")
    Optional<Ward> findById(@Param("wardCode") String wardCode);
}
