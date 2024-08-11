package com.ducthong.TopCV.domain.dto.employer;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.ducthong.TopCV.annotation.DobConstraint;
import com.ducthong.TopCV.domain.dto.address.AddPersonAddressRequestDTO;
import com.ducthong.TopCV.domain.enums.Gender;

public record AddEmployerRequestDTO(
        String username,
        @NotBlank(message = "Password cannot blank!") String password,
        String firstName,
        String lastName,
        Gender gender,
        @DobConstraint(min = 16) Date dateOfBirth,
        @Email(message = "Invalid email format") String email,
        MultipartFile avatar,
        String phoneNumber,
        AddPersonAddressRequestDTO address,
        Integer verifiedLevel) {}
