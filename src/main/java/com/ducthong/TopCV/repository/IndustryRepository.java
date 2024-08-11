package com.ducthong.TopCV.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.Industry;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Integer> {
    @Query(value = "SELECT i FROM Industry i " + "WHERE i.deleted = false")
    Page<Industry> getAllActiveIndustry(Pageable pageable);

    @Query(value = "SELECT i FROM Industry i ")
    Page<Industry> getAllIndustry(Pageable pageable);

    @Query(value = "SELECT i FROM Industry i " + "WHERE i.deleted = false AND i.id = :id ")
    Optional<Industry> findActiveIndustryById(@Param("id") Integer id);

    Optional<Industry> findIndustryByName(@Param("name") String name);

    @Query(
            value = "SELECT i FROM Industry i "
                    + "WHERE i.deleted = false AND CONCAT(i.name,' ',i.description) LIKE %:keyword% ")
    Page<Industry> searchActiveIndustry(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT i FROM Industry i " + "WHERE CONCAT(i.name,' ',i.description) LIKE %:keyword% ")
    Page<Industry> searchAllIndustry(@Param("keyword") String keyword, Pageable pageable);
}
