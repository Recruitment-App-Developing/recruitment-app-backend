package com.ducthong.TopCV.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.application.ApplicationRequestDTO;
import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.application.AppliedCandidateResponseDTO;
import com.ducthong.TopCV.domain.dto.application.StatisticApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.ApplicationService;
import com.ducthong.TopCV.utility.AuthUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Application Controller", description = "APIs related to Application operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping(Endpoint.V1.Application.APPLY)
    public ResponseEntity<Response<ApplicationResponseDTO>> addApplication(
            @RequestBody ApplicationRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.successfulResponse(
                        "Apply successful",
                        applicationService.addApplication(
                                AuthUtil.getRequestedUser().getId(), requestDTO)));
    }

    @GetMapping(Endpoint.V1.Application.STATISTIC_BY_COMPANY)
    public ResponseEntity<Response<StatisticApplicationResponseDTO>> statisticByCompany() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Get statistic by company successful",
                        applicationService.statisticCvByCompany(
                                AuthUtil.getRequestedUser().getId())));
    }

    @GetMapping(Endpoint.V1.Application.GET_APPLIED_CANDIDATE_BY_JOB)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<AppliedCandidateResponseDTO>>> getAppliedCandidateByJob(
            @PathVariable(name = "jobId") Integer jobId, @ParameterObject MetaRequestDTO metaRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(applicationService.getAppliedCandidateByJob(
                        jobId, AuthUtil.getRequestedUser().getId(), metaRequestDTO));
    }
}
