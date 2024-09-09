package com.ducthong.TopCV.utility;

import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.AccountRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetRoleUtil {
    private static AccountRepository accountRepo;
    @Autowired
    private AccountRepository accountRepository;
    @PostConstruct
    private void init() {
        GetRoleUtil.accountRepo = this.accountRepository;
    }
    public static Candidate getCandidate(Integer accountId){
        try {
            return (Candidate) accountRepo.findById(accountId).get();
        } catch (Exception e) {
            throw new AppException("This account is not Candidate");
        }
    }
    public static Employer getEmployer(Integer accountId){
        try {
            return (Employer) accountRepo.findById(accountId).get();
        } catch (Exception e) {
            throw new AppException("This account is not Employer");
        }
    }
}
