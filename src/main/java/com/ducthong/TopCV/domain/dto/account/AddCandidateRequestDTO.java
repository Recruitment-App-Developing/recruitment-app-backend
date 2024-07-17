package com.ducthong.TopCV.domain.dto.account;

import java.util.Date;

import com.ducthong.TopCV.domain.dto.address.AddPersonAddressRequestDTO;
import com.ducthong.TopCV.domain.entity.address.PersonAddress;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.ducthong.TopCV.annotation.DobConstraint;
import com.ducthong.TopCV.domain.enums.Gender;
import org.springframework.web.multipart.MultipartFile;

public record AddCandidateRequestDTO(
        String username,
        @NotBlank(message = "Password cannot blank!")
        String password,
        String firstName,
        String lastName,
        Gender gender,
        @DobConstraint(min = 16)
        Date dateOfBirth,
        @Email(message = "Invalid email format")
        String email,
        MultipartFile avatar,
        String phoneNumber,
        AddPersonAddressRequestDTO address,
        Boolean isFindJob) {}
