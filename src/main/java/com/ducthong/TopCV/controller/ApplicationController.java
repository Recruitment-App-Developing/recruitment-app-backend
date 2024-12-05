package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.application.*;
import com.ducthong.TopCV.domain.dto.candidate.SearchCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.ApplicationService;
import com.ducthong.TopCV.utility.AuthUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping(Endpoint.V1.Application.STATISTIC_BY_COMPANY)
    public ResponseEntity<Response<StatisticApplicationResponseDTO>> statisticByCompany(){
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse("Get statistic by company successful",
                        applicationService.statisticCvByCompany(AuthUtil.getRequestedUser().getId()))
        );
    }
    @GetMapping(Endpoint.V1.Application.GET_APPLIED_CANDIDATE_BY_JOB)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<AppliedCandidateResponseDTO>>> getAppliedCandidateByJob (
            @PathVariable(name = "jobId") Integer jobId,
            @ParameterObject MetaRequestDTO metaRequestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.getAppliedCandidateByJob(jobId, AuthUtil.getRequestedUser().getId(), metaRequestDTO));
    }
    @GetMapping(Endpoint.V1.Application.HISTORY_APPLICAITON)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<ApplicationForCandidateResponseDTO>>> getApplicationHitory(
            @ParameterObject MetaRequestDTO metaRequestDTO,
            @RequestParam(name = "status", required = false) String status
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                applicationService.getHistoryApplication(AuthUtil.getRequestedUser().getId(), metaRequestDTO, status)
        );
    }
    @GetMapping(Endpoint.V1.Application.SEARCH_CANDIDATE_BY_JOB)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<AppliedCandidateResponseDTO>>> searchCandidateByJob(
            @PathVariable(name = "jobId") Integer jobId,
            @ParameterObject MetaRequestDTO metaRequestDTO,
            @ParameterObject SearchCandidateRequestDTO requestDTO
            ){
        return ResponseEntity.status(HttpStatus.OK).body(
                applicationService.searchAppliedCandidateByJob(requestDTO, AuthUtil.getRequestedUser().getId(), jobId, metaRequestDTO)
        );
    }
}
