package com.ducthong.TopCV.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.domain.dto.employer.AddEmployerRequestDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.UpdEmployerRequestDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.EmployerService;
import com.ducthong.TopCV.utility.JwtTokenUtil;
import com.ducthong.TopCV.utility.MessageSourceUtil;

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
    // Ultility
    JwtTokenUtil jwtTokenUtil;
    MessageSourceUtil messageUtil;

    @GetMapping(Endpoint.V1.Employer.Account.GET_DETAIL)
    public ResponseEntity<Response<EmployerResponseDTO>> getActiveEmployerAccount(
            HttpServletRequest request, @PathVariable(name = "id") Integer id) {
        Integer idToken = Integer.valueOf(jwtTokenUtil.getAccountId(request.getHeader(HttpHeaders.AUTHORIZATION)));
        if (id != idToken)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Response.failedResponse(
                            HttpStatus.UNAUTHORIZED.value(), messageUtil.getMessage(ErrorMessage.Account.GET_DETAIL)));
        return ResponseEntity.status(HttpStatus.OK).body(employerService.getActiveEmployerAccount(id));
    }

    @PostMapping(Endpoint.V1.Employer.Account.ADD_ONE)
    public ResponseEntity<Response<EmployerResponseDTO>> addEmployerAccount(
            @ModelAttribute @Valid AddEmployerRequestDTO requestDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(employerService.addEmployerAccount(requestDTO));
    }

    @PutMapping(Endpoint.V1.Employer.Account.UPDATE)
    public ResponseEntity<Response<EmployerResponseDTO>> updEmployerAccount(
            HttpServletRequest request,
            @PathVariable(name = "id") Integer id,
            @RequestBody @Valid UpdEmployerRequestDTO requestDTO) {
        Integer idToken = Integer.valueOf(jwtTokenUtil.getAccountId(request.getHeader(HttpHeaders.AUTHORIZATION)));
        if (id != idToken)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Response.failedResponse(
                            HttpStatus.UNAUTHORIZED.value(), messageUtil.getMessage(ErrorMessage.Account.UPDATE)));
        return ResponseEntity.status(HttpStatus.OK).body(employerService.updEmployerAccount(id, requestDTO));
    }
}
