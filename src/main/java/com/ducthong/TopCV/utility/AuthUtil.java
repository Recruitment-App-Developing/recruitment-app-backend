package com.ducthong.TopCV.utility;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ducthong.TopCV.domain.entity.account.Account;

public class AuthUtil {
    public static Account getRequestedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) return null;
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
