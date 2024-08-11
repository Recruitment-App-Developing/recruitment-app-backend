package com.ducthong.TopCV.controller;

import jakarta.validation.constraints.Email;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ducthong.TopCV.annotation.RestApiV1;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Test Controller", description = "APIs related to Test operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class TestController {
    @PostMapping("/test")
    public void test1(@RequestParam @Email(message = "Hello") String email) {
        System.out.println("Ok");
    }
}
