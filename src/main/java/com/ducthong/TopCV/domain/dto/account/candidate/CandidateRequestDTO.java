package com.ducthong.TopCV.domain.dto.account.candidate;

import com.ducthong.TopCV.annotation.PasswordMatching;

@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm password " + "don’t match. Please try again.")
public record CandidateRequestDTO(
        String username,
        String password,
        String confirmPassword,
        String email
) {
}
