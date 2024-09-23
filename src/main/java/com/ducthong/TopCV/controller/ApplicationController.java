package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.application.ApplicationRequestDTO;
import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.ApplicationService;
import com.ducthong.TopCV.utility.AuthUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Application Controller", description = "APIs related to Application operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class ApplicationController {
    private final ApplicationService applicationService;
    @PostMapping(Endpoint.V1.Application.APPLY)
    public ResponseEntity<Response<ApplicationResponseDTO>> addApplication(
            @RequestBody ApplicationRequestDTO requestDTO
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.successfulResponse("Apply successful",
                        applicationService.addApplication(AuthUtil.getRequestedUser().getId(), requestDTO)
            )
        );
    }
}
