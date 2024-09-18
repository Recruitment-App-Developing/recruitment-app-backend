package com.ducthong.TopCV.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.constant.messages.SuccessMessage;
import com.ducthong.TopCV.domain.dto.authentication.IntrospectTokenRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.IntrospectTokenResponseDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.mapper.AccountMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AuthenticationService;
import com.ducthong.TopCV.utility.JwtTokenUtil;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    MessageSourceUtil messageSourceUtil;
    AccountRepository accountRepository;
    JwtTokenUtil jwtTokenUtil;

    @Override
    public Response<LoginResponseDTO> login(LoginRequestDTO requestDTO) {
        Optional<Account> accountResult = accountRepository.findByUsername(requestDTO.username());
//        if (accountResult.get() instanceof Employer) System.out.println("Employer");
//        if (accountResult.get() instanceof Candidate) System.out.println("Candidate");

        if (accountResult.isEmpty()) throw new AppException(ErrorMessage.Auth.NOT_EXISTED_USERNAME);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Boolean authenticated = passwordEncoder.matches(
                requestDTO.password(), accountResult.get().getPassword());
        if (authenticated == false) throw new AppException(ErrorMessage.Auth.LOGIN_FAIL);

        // Generate Token
        String token = jwtTokenUtil.generateToken(accountResult.get());

        LoginResponseDTO responseDTO = LoginResponseDTO.builder()
                .authenticated(true)
                .token(token)
                .infor(AccountMapper.INSTANCE.toAccountResponseDto(accountResult.get()))
                .build();

        return Response.successfulResponse(
                messageSourceUtil.getMessage(SuccessMessage.Auth.LOGIN_SUCCESS), responseDTO);
    }

    @Override
    public Response<IntrospectTokenResponseDTO> introspectToken(IntrospectTokenRequestDTO requestDTO) {
        return Response.successfulResponse(
                messageSourceUtil.getMessage(SuccessMessage.Auth.INTROSPECT),
                IntrospectTokenResponseDTO.builder()
                        .verifired(jwtTokenUtil.validateToken(requestDTO.token()))
                        .subject(jwtTokenUtil.getSubject(requestDTO.token()))
                        .build());
    }
}
