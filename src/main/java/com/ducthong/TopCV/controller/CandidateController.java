package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CandidateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Candidate Controller", description = "APIs releated to Candidate operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class CandidateController {
    private final CandidateService candidateService;
    @PostMapping(Endpoint.V1.Candidate.REGISTER_CANDIDATE)
    public ResponseEntity<Response<LoginResponseDTO>> registerCandidate(
            @RequestBody CandidateRequestDTO requestDTO
            ){
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse(
                        "Welcome "+requestDTO.username(),
                        candidateService.registerCandidate(requestDTO)
                )
        );
    }
}
