package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.job.DetailJobResponseDTO;
import com.ducthong.TopCV.domain.dto.job.JobRequestDTO;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.JobService;
import com.ducthong.TopCV.utility.AuthUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Tag(name = "Job Controller", description = "APIs related to Job operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class JobController {
    private final JobService jobService;
    @PostMapping(Endpoint.V1.Job.ADD_ONE)
    public ResponseEntity<Response<DetailJobResponseDTO>> addJob(
            @RequestBody @Valid JobRequestDTO requestDTO
            ) throws IOException {
        Account account = AuthUtil.getRequestedUser();
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse(
                        "Add new job successfull" ,
                        jobService.addJob(requestDTO, account.getId())));
    }
}
