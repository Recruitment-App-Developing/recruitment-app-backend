package com.ducthong.TopCV.domain.dto.authentication;

import com.ducthong.TopCV.annotation.PasswordMatching;

@PasswordMatching(
        password = "newPassword",
        confirmPassword = "confirmNewPassword",
        message = "Mật khẩu và xác nhận mật khẩu cần trùng nhau")
public record ChangePasswordRequestDTO(
        String newPassword, String confirmNewPassword, String oldPassword) {}