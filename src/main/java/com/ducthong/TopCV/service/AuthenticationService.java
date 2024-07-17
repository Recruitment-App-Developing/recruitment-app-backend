package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.authentication.IntrospectTokenRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.IntrospectTokenResponseDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.responses.Response;

public interface AuthenticationService {
    Response<LoginResponseDTO> login(LoginRequestDTO requestDTO);

    Response<IntrospectTokenResponseDTO> introspectToken(IntrospectTokenRequestDTO requestDTO);
}
