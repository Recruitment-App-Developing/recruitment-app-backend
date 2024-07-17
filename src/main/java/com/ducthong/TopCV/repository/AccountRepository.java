package com.ducthong.TopCV.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a " +
            "JOIN FETCH a.avatar " +
            "WHERE a.deleted = false")
    List<Account> getAllActiveAccount();
    @Query("SELECT a FROM Account a " +
            "JOIN FETCH a.avatar " +
            "WHERE a.deleted = true")
    List<Account> getAllDeletedAccount();
    @Query("SELECT a FROM Account a WHERE a.username = :username")
    Optional<Account> findByUsername(@Param("username") String username);
    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Optional<Account> findByEmail(@Param("email") String email);
}
