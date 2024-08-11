package com.ducthong.TopCV.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ducthong.TopCV.domain.entity.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a " + "JOIN FETCH a.avatar " + "WHERE a.deleted = false")
    Page<Account> getAllActiveAccount(Pageable pageable);

    @Query("SELECT a FROM Account a " + "JOIN FETCH a.avatar "
            + "WHERE CONCAT(a.id,' ',a.username,' ',a.firstName,' ',a.lastName,' ',a.email,' ',a.phoneNumber) LIKE %:keyword% "
            + "AND a.deleted = false")
    Page<Account> searchAllActiveAccount(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT a FROM Account a " + "JOIN FETCH a.avatar " + "WHERE a.deleted = true")
    List<Account> getAllDeletedAccount();

    @Query("SELECT a FROM Account a " + "JOIN FETCH a.avatar " + "WHERE a.id = :id")
    Optional<Account> findById(@Param("id") Integer id);

    @Query("SELECT a FROM Account a WHERE a.username = :username")
    Optional<Account> findByUsername(@Param("username") String username);

    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Optional<Account> findByEmail(@Param("email") String email);
}
