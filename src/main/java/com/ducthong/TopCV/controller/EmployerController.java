package com.ducthong.TopCV.controller;

import java.io.IOException;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.AddEmployerRequestDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.UpdEmployerRequestDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.EmployerService;
import com.ducthong.TopCV.utility.AuthUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class EmployerController {
    // Service
    EmployerService employerService;

    @GetMapping(Endpoint.V1.Employer.MY_ACCOUNT)
    public ResponseEntity<Response<EmployerResponseDTO>> getActiveEmployerAccount() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employerService.getActiveEmployerAccount(
                        AuthUtil.getRequestedUser().getId()));
    }

    @PostMapping(Endpoint.V1.Employer.REGISTER_EMPLOYER)
    public ResponseEntity<Response<LoginResponseDTO>> addEmployerAccount(
            @RequestBody @Valid AddEmployerRequestDTO requestDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Chào mừng " + requestDTO.username(), employerService.registerEmployerAccount(requestDTO)));
    }

    @PutMapping(Endpoint.V1.Employer.UPDATE_EMPLOYER)
    public ResponseEntity<Response<EmployerResponseDTO>> updEmployerAccount(
            @RequestBody @Valid UpdEmployerRequestDTO requestDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employerService.updEmployerAccount(
                        AuthUtil.getRequestedUser().getId(), requestDTO));
    }

    @PatchMapping(Endpoint.V1.Employer.REGISTER_COMPANY)
    public ResponseEntity<Response> registerCompany(@PathVariable(name = "companyId") Integer companyId) {
        employerService.registerCompany(AuthUtil.getRequestedUser().getId(), companyId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Response.successfulResponse("Đăng kí công ty thành công"));
    }
}
