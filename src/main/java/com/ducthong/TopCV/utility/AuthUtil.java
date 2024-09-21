package com.ducthong.TopCV.utility;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ducthong.TopCV.domain.entity.account.Account;

public class AuthUtil {
    public static Account getRequestedUser() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
