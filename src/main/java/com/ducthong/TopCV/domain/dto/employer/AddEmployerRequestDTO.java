package com.ducthong.TopCV.domain.dto.employer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.ducthong.TopCV.annotation.PasswordMatching;
import com.ducthong.TopCV.domain.enums.Gender;

@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm password " + "don’t match. Please try again.")
public record AddEmployerRequestDTO(
        String username,
        @NotBlank(message = "Password cannot blank!") String password,
        String confirmPassword,
        Gender gender,
        // @DobConstraint(min = 16) Date dateOfBirth,
        @Email(message = "Invalid email format") String email,
        // MultipartFile avatar,
        String phoneNumber
        // AddPersonAddressRequestDTO address,
        // Integer verifiedLevel
        ) {}
