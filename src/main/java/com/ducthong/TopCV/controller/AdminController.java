package com.ducthong.TopCV.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Admin Controller", description = "APIs releated to Admin operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
    // Service
    private final AccountService accountService;

    @PatchMapping(Endpoint.V1.Admin.Account.DELETE_TEMP)
    public ResponseEntity<Response<?>> deleteTempAccount(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.deleteTempAccount(id));
    }

    @PatchMapping(Endpoint.V1.Admin.Account.RETRIEVE)
    public ResponseEntity<Response<AccountResponseDTO>> retrieveAccount(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.retrieveAccount(id));
    }

    @DeleteMapping(Endpoint.V1.Admin.Account.DELETE_PERM)
    public ResponseEntity<Response<?>> deletePermAccount(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.deletePermAccount(id));
    }
}
