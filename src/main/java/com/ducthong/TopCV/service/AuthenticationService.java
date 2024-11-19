package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.authentication.*;
import com.ducthong.TopCV.responses.Response;

public interface AuthenticationService {
    Response<LoginResponseDTO> login(LoginRequestDTO requestDTO);
    Response<IntrospectTokenResponseDTO> introspectToken(IntrospectTokenRequestDTO requestDTO);
    Response<String> changePassword(Integer accountId, ChangePasswordRequestDTO requestDTO);
}
