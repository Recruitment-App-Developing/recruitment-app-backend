package com.ducthong.TopCV.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.deleted = true")
    List<Account> getAllActiveAccount();
}
