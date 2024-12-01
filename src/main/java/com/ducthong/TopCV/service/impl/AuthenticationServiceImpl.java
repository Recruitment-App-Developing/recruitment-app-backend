package com.ducthong.TopCV.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.constant.MailTemplate;
import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.constant.messages.SuccessMessage;
import com.ducthong.TopCV.domain.dto.authentication.*;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.mapper.Account2Mapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AuthenticationService;
import com.ducthong.TopCV.utility.JwtTokenUtil;
import com.ducthong.TopCV.utility.MailSenderUtil;
import com.ducthong.TopCV.utility.MessageSourceUtil;
import com.ducthong.TopCV.utility.TimeUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableTransactionManagement
public class AuthenticationServiceImpl implements AuthenticationService {
    MessageSourceUtil messageSourceUtil;
    AccountRepository accountRepo;
    JwtTokenUtil jwtTokenUtil;
    MailSenderUtil mailSenderUtil;
    Account2Mapper account2Mapper;

    @Override
    @Transactional
    public Response<LoginResponseDTO> login(LoginRequestDTO requestDTO) {
        Optional<Account> accountResult = accountRepo.findByUsername(requestDTO.username());

        if (accountResult.isEmpty()) throw new AppException(ErrorMessage.Auth.NOT_EXISTED_USERNAME);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Boolean authenticated = passwordEncoder.matches(
                requestDTO.password(), accountResult.get().getPassword());
        if (authenticated == false) throw new AppException(ErrorMessage.Auth.LOGIN_FAIL);

        return Response.successfulResponse(
                "Welcome " + accountResult.get().getUsername(), account2Mapper.toLoginResponseDto(accountResult.get()));

        //        // Generate Token
        //        String token = jwtTokenUtil.generateToken(accountResult.get());
        //
        //        LoginResponseDTO responseDTO = LoginResponseDTO.builder()
        //                .authenticated(true)
        //                .token(token)
        //                .infor(AccountMapper.INSTANCE.toAccountResponseDto(accountResult.get()))
        //                .build();
        //
        //        return Response.successfulResponse(
        //                messageSourceUtil.getMessage(SuccessMessage.Auth.LOGIN_SUCCESS), responseDTO);
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

    @Override
    @Transactional
    public Response<String> changePassword(Integer accountId, ChangePasswordRequestDTO requestDTO) {
        Optional<Account> findAccount = accountRepo.findById(accountId);
        if (findAccount.isEmpty()) throw new AppException("Tài khoản này không tồn tại");
        Account account = findAccount.get();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(requestDTO.oldPassword(), account.getPassword());
        if (!authenticated) throw new AppException("Mật khẩu hiện tại không hợp lệ");

        account.setPassword(passwordEncoder.encode(requestDTO.newPassword()));
        try {
            Account saveUser = accountRepo.save(account);

            // Send mail
            String toMail = account.getEmail();
            String subject = MailTemplate.CHANGE_PASSWORD.CHANGE_PASSWORD_SUBJECT;
            String template = MailTemplate.CHANGE_PASSWORD.CHANGE_PASSWORD_TEMPLATE;
            Map<String, Object> variable = Map.of(
                    "userName", account.getUsername(),
                    "newPassword", requestDTO.newPassword(),
                    "whenChange", TimeUtil.toStringFullDateTime(TimeUtil.getDateTimeNow()));
            mailSenderUtil.sendMailWithHTML(toMail, subject, template, variable);
            return Response.successfulResponse(
                    "Thay đổi mật khẩu thành công. Hãy kiểm tra Email để nhận mật khẩu mới.");
        } catch (Exception e) {
            throw new AppException("Thay đổi mật khẩu thất bại");
        }
    }
}
