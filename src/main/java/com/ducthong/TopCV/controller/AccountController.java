package com.ducthong.TopCV.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.domain.dto.account.*;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AccountService;
import com.ducthong.TopCV.utility.JwtTokenUtil;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Account Controller", description = "APIs related to Account operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class AccountController {
    MessageSourceUtil messageUtil;
    JwtTokenUtil jwtTokenUtil;
    AccountService accountService;

    //    @GetMapping(Endpoint.V1.Account.GET_ONE)
    //    public ResponseEntity<Response<CandidateResponseDTO>> getCandidateAccount(@PathVariable(name = "id") Integer
    // id) {
    //        return ResponseEntity.status(HttpStatus.OK).body(accountService.getCandidateAccount(id));
    //    }

    //    @PostMapping(Endpoint.V1.Account.ADD_ONE)
    //    public ResponseEntity<Response<CandidateResponseDTO>> addCandidateAccount(
    //            @ModelAttribute @Valid AddCandidateTest request) throws IOException {
    //        return ResponseEntity.status(HttpStatus.OK)
    //                .body(accountService.addCandidateAccount(request.content(), request.avatar()));
    //    }
    //    @PostMapping(Endpoint.V1.Account.UPDATE_CANDIDATE)
    //    public ResponseEntity<Response<CandidateResponseDTO>> updCandidateAccount(@RequestBody UpdCandidateRequestDTO
    // updCandidateRequestDTO){
    //        return ResponseEntity.status(HttpStatus.OK)
    //                .body(accountService.updCandidateAccount(updCandidateRequestDTO));
    //    }
    // CANDIDATE
    @GetMapping(Endpoint.V1.Candidate.Account.GET_DETAIL)
    public ResponseEntity<Response<CandidateResponseDTO>> getCandidateAccountForCandidate(
            HttpServletRequest request, @PathVariable(name = "id") Integer id) {
        Integer idToken = Integer.valueOf(jwtTokenUtil.getAccountId(request.getHeader(HttpHeaders.AUTHORIZATION)));
        if (id != idToken)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Response.failedResponse(
                            HttpStatus.UNAUTHORIZED.value(), messageUtil.getMessage(ErrorMessage.Account.GET_DETAIL)));
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getActiveCandidateAccount(id));
    }

    @PostMapping(Endpoint.V1.Account.ADD_ONE)
    public ResponseEntity<Response<CandidateResponseDTO>> addCandidateAccount(
            @ModelAttribute @Valid AddCandidateRequestDTO request) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.addCandidateAccount(request));
    }

    @PostMapping(Endpoint.V1.Candidate.Account.UPDATE)
    public ResponseEntity<Response<CandidateResponseDTO>> updCandidateAccount(
            HttpServletRequest request,
            @PathVariable(name = "id") Integer id,
            @RequestBody UpdCandidateRequestDTO updCandidateRequestDTO) {
        Integer idToken = Integer.valueOf(jwtTokenUtil.getAccountId(request.getHeader(HttpHeaders.AUTHORIZATION)));
        if (id != idToken)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Response.failedResponse(
                            HttpStatus.UNAUTHORIZED.value(), messageUtil.getMessage(ErrorMessage.Account.UPDATE)));
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.updCandidateAccount(id, updCandidateRequestDTO));
    }
    // ADMIN
    @Operation(summary = "Get list active account", description = "Returns list active account")
    @GetMapping(Endpoint.V1.Admin.Account.GET_LIST_ACTIVE)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<AccountResponseDTO>>> getAllActiveAccount(
            @ParameterObject MetaRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllActiveAccount(requestDTO));
    }

    @Operation(summary = "Get list deleted account", description = "Returns list deleted account")
    @GetMapping(Endpoint.V1.Admin.Account.GET_LIST_DELETED)
    public ResponseEntity<Response<List<AccountResponseDTO>>> getAllDeletedAccount() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllDeletedAccount());
    }
    //    @GetMapping(Endpoint.V1.Admin.Account.GET_DETAIL)
    //    public ResponseEntity<Response<CandidateResponseDTO>> getCandidateAccountForAdmin(@PathVariable(name = "id")
    // Integer id) {
    //        return ResponseEntity.status(HttpStatus.OK).body(accountService.getCandidateAccount(id));
    //    }
}
