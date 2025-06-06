package com.ducthong.TopCV.controller;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.dto.job.*;
import com.ducthong.TopCV.domain.dto.job.job_address.JobAddressRequestDTO;
import com.ducthong.TopCV.domain.dto.job.job_address.JobAddressResponseDTO;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.JobService;
import com.ducthong.TopCV.utility.AuthUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Job Controller", description = "APIs related to Job operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class JobController {
    private final JobService jobService;

    @GetMapping(Endpoint.V1.Job.GET_LIST_JOB)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<JobResponseDTO>>> getListJob(
            @ParameterObject MetaRequestDTO metaRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getListJob(metaRequestDTO));
    }
    @GetMapping(Endpoint.V1.Job.SEARCH_JOB)
    public ResponseEntity<Response<List<RelatedJobResponseDTO>>> searchJob(
            @ParameterObject SearchJobRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK).body(Response.successfulResponse(
                "",
                jobService.searchJob(requestDTO)));
    }
    @GetMapping(Endpoint.V1.Job.GET_LIST_BY_COMPANY)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<EmployerJobResponseDTO>>> getListJobByCompany(
            @ParameterObject MetaRequestDTO metaRequestDTO,
            @ParameterObject SearchJobByCompanyRequestDTO requestDTO
    ) {
        Integer accountId = AuthUtil.getRequestedUser().getId();
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getListJobByCompany(requestDTO, accountId, metaRequestDTO));
    }

    @GetMapping(Endpoint.V1.Job.FIND_LIST_BY_COMPANY)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<RelatedJobResponseDTO>>> findListJobByCompany(
            @ParameterObject MetaRequestDTO metaRequestDTO,
            @PathVariable(name = "companyId") Integer companyId,
            @RequestParam(name = "job_name", required = false) String jobName,
            @RequestParam(name = "address", required = false) String address
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findListJobByCompany(metaRequestDTO, companyId, jobName, address));
    }

    @GetMapping(Endpoint.V1.Job.GET_LIST_JOB_SPEC)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<JobResponseDTO>>> getListJobSpecification(
            @ParameterObject MetaRequestDTO metaRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getListJobSpecification(metaRequestDTO));
    }

    @GetMapping(Endpoint.V1.Job.GET_DETAIL)
    public ResponseEntity<Response<DetailJobResponseDTO>> getDetailJob(@PathVariable(name = "jobId") Integer jobId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse("Get a detail job successful", jobService.getDetailJob(jobId)));
    }
    @GetMapping(Endpoint.V1.Job.GET_DETAIL_JOB_PAGE)
    public ResponseEntity<Response<DetailJobPageResponseDTO>> getDetailJobPage(@PathVariable(name = "jobId") Integer jobId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse("Get a detail job successful", jobService.getDetailJobPage(jobId)));
    }
    @PostMapping(Endpoint.V1.Job.ADD_ONE)
    public ResponseEntity<Response<DetailJobResponseDTO>> addJob(@RequestBody @Valid JobRequestDTO requestDTO)
            throws IOException {
        Account account = AuthUtil.getRequestedUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Add new job successfull", jobService.addJob(requestDTO, account.getId())));
    }
    @PostMapping(Endpoint.V1.Job.UPDATE_ONE)
    public ResponseEntity<Response<DetailJobResponseDTO>> updateJob(
            @PathVariable(name = "jobId") Integer jobId,
            @RequestBody UpdJobRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Update a job successfull",
                        jobService.updateJob(requestDTO, AuthUtil.getRequestedUser().getId(), jobId)
                ));
    }
    @GetMapping(Endpoint.V1.Job.GET_LIST_JOB_ADDRESS)
    public ResponseEntity<Response<List<JobAddressResponseDTO>>> getListJobAddressByJob(
            @PathVariable(name = "jobId") Integer jobId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(Response.successfulResponse(
                "Get list job address successful",
                jobService.getListJobAddressByJob(AuthUtil.getRequestedUser().getId(), jobId)
        ));
    }
    @PostMapping(Endpoint.V1.Job.UPDATE_JOB_ADDRESS)
    public ResponseEntity<Response<List<JobAddressResponseDTO>>> updateJobAddress(
            @PathVariable(name = "jobId") Integer jobId,
            @RequestBody JobAddressRequestDTO requestDTO
            ){
        return ResponseEntity.status(HttpStatus.OK).body(Response.successfulResponse(
                "Update job address successfull",
                jobService.updateJobAddress(requestDTO, AuthUtil.getRequestedUser().getId(), jobId)
        ));
    }
    @DeleteMapping(Endpoint.V1.Job.DELETE_JOB_ADDRESS)
    public ResponseEntity<Response<List<JobAddressResponseDTO>>> deleteJobAddress(
            @PathVariable(name = "jobId") Integer jobId,
            @RequestParam(name = "job_address_id", required = true) Integer jobAddressId
            ){
        return ResponseEntity.status(HttpStatus.OK).body(Response.successfulResponse(
                "Delete job address successful",
                jobService.deleteJobAddressId(AuthUtil.getRequestedUser().getId(), jobId, jobAddressId)
        ));
    }
}
