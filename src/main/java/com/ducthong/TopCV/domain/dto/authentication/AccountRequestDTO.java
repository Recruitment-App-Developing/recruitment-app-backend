package com.ducthong.TopCV.domain.dto.authentication;

import com.ducthong.TopCV.annotation.DobConstraint;
import com.ducthong.TopCV.annotation.EnumValid;
import com.ducthong.TopCV.domain.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record AccountRequestDTO(
        String username,
        @NotBlank(message = "Password cannot blank!") String password,
        String firstName,
        String lastName,
        @EnumValid(name = "gender", enumClass = Gender.class)
        String gender,
        @DobConstraint(min = 16) Date dateOfBirth,
        @Email(message = "Invalid email format")
        String email,
        String avatar,
        String phoneNumber
) {
}
