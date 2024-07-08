package com.ducthong.TopCV.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/api/v1/accounts")
    public ResponseEntity<Response<List<AccountResponseDTO>>> getAllActiveAccount() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllActiveAccount());
    }

    @GetMapping("/api/v1/accounts/{id}")
    public ResponseEntity<Response<CandidateResponseDTO>> getCandidateAccount(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getCandidateAccount(id));
    }
    //    @GetMapping("/api/v1/accounts")
    //    public ResponseEntity<String> test() {
    //        return ResponseEntity.ok().body("Hello");
    //    }
}
