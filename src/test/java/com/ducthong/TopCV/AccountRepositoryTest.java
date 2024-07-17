package com.ducthong.TopCV;

import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRepositoryTest {
    private static AccountRepository accountRepository;

    public static void main(String[] args) {
        Account res = accountRepository.findByUsername("ducthong").get();
        System.out.println(res.toString());
    }
}
