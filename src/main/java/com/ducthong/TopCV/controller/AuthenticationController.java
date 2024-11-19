package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.domain.dto.authentication.*;
import com.ducthong.TopCV.utility.AuthUtil;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping(value = {Endpoint.V1.Authentication.LOGIN, Endpoint.V1.Admin.Auth.LOGIN})
    public ResponseEntity<Response<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(requestDTO));
    }
    @PostMapping(Endpoint.V1.Authentication.INTROSPECT)
    public ResponseEntity<Response<IntrospectTokenResponseDTO>> introspectToken(
            @RequestBody @Valid IntrospectTokenRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.introspectToken(requestDTO));
    }
    @PostMapping(Endpoint.V1.Authentication.CHANGE_PASSWORD)
    public ResponseEntity<Response<String>> changePassword(
            @RequestBody @Valid ChangePasswordRequestDTO requestDTO
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                authenticationService.changePassword(AuthUtil.getRequestedUser().getId(), requestDTO));
    }
}
