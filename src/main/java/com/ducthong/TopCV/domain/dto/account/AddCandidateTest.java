package com.ducthong.TopCV.domain.dto.account;

import jakarta.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

public record AddCandidateTest(MultipartFile avatar, @Valid AddCandidateRequestDTO content
        //        @Size(min = 5)
        //        String test
        ) {}
