package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.authentication.IntrospectTokenRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.IntrospectTokenResponseDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AuthenticationService;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping(value = {Endpoint.V1.Authentication.LOGIN, Endpoint.V1.Admin.Auth.LOGIN})
    public ResponseEntity<Response<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(requestDTO));
    }
    @PostMapping(Endpoint.V1.Authentication.INTROSPECT)
    public ResponseEntity<Response<IntrospectTokenResponseDTO>> introspectToken(@RequestBody @Valid IntrospectTokenRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.introspectToken(requestDTO));
    }
}
