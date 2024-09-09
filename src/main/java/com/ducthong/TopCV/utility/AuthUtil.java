package com.ducthong.TopCV.utility;

import com.ducthong.TopCV.domain.entity.account.Account;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static Account getRequestedUser() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}