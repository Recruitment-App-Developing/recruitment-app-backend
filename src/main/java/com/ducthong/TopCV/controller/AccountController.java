package com.ducthong.TopCV.controller;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.domain.dto.account.*;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.utility.JwtTokenUtil;
import com.ducthong.TopCV.utility.MessageSourceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AccountService;
import com.google.gson.Gson;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    MessageSourceUtil messageUtil;
    JwtTokenUtil jwtTokenUtil;
    AccountService accountService;

//    @GetMapping(Endpoint.V1.Account.GET_ONE)
//    public ResponseEntity<Response<CandidateResponseDTO>> getCandidateAccount(@PathVariable(name = "id") Integer id) {
//        return ResponseEntity.status(HttpStatus.OK).body(accountService.getCandidateAccount(id));
//    }

//    @PostMapping(Endpoint.V1.Account.ADD_ONE)
//    public ResponseEntity<Response<CandidateResponseDTO>> addCandidateAccount(
//            @ModelAttribute @Valid AddCandidateTest request) throws IOException {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(accountService.addCandidateAccount(request.content(), request.avatar()));
//    }
//    @PostMapping(Endpoint.V1.Account.UPDATE_CANDIDATE)
//    public ResponseEntity<Response<CandidateResponseDTO>> updCandidateAccount(@RequestBody UpdCandidateRequestDTO updCandidateRequestDTO){
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(accountService.updCandidateAccount(updCandidateRequestDTO));
//    }
    // CANDIDATE
    @GetMapping(Endpoint.V1.Candidate.Account.GET_DETAIL)
    public ResponseEntity<Response<CandidateResponseDTO>> getCandidateAccountForCandidate(
            HttpServletRequest request,
            @PathVariable(name = "id") Integer id) {
        Integer idToken = Integer.valueOf(jwtTokenUtil.getAccountId(request.getHeader(HttpHeaders.AUTHORIZATION)));
        if (id != idToken)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.failedResponse(HttpStatus.UNAUTHORIZED.value(), messageUtil.getMessage(ErrorMessage.Account.GET_DETAIL)));
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getNoDeletedCandidateAccount(id));
    }
    @PostMapping(Endpoint.V1.Account.ADD_ONE)
    public ResponseEntity<Response<CandidateResponseDTO>> addCandidateAccount(
            @ModelAttribute @Valid AddCandidateRequestDTO request) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.addCandidateAccount(request));
    }
    @PostMapping(Endpoint.V1.Candidate.Account.UPDATE)
    public ResponseEntity<Response<CandidateResponseDTO>> updCandidateAccount(
            HttpServletRequest request,
            @PathVariable(name = "id") Integer id,
            @RequestBody UpdCandidateRequestDTO updCandidateRequestDTO){
        Integer idToken = Integer.valueOf(jwtTokenUtil.getAccountId(request.getHeader(HttpHeaders.AUTHORIZATION)));
        if (id != idToken)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Response.failedResponse(HttpStatus.UNAUTHORIZED.value(), messageUtil.getMessage(ErrorMessage.Account.UPDATE)));
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.updCandidateAccount(id, updCandidateRequestDTO));
    }
    // ADMIN
    @GetMapping(Endpoint.V1.Admin.Account.GET_LIST_ACTIVE)
    public ResponseEntity<Response<List<AccountResponseDTO>>> getAllActiveAccount(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllActiveAccount());
    }
    @GetMapping(Endpoint.V1.Admin.Account.GET_LIST_DELETED)
    public ResponseEntity<Response<List<AccountResponseDTO>>> getAllDeletedAccount() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllDeletedAccount());
    }
//    @GetMapping(Endpoint.V1.Admin.Account.GET_DETAIL)
//    public ResponseEntity<Response<CandidateResponseDTO>> getCandidateAccountForAdmin(@PathVariable(name = "id") Integer id) {
//        return ResponseEntity.status(HttpStatus.OK).body(accountService.getCandidateAccount(id));
//    }
}
