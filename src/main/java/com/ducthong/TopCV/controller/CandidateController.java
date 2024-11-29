package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.dto.candidate.DetailCandidateResponseDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CandidateService;
import com.ducthong.TopCV.utility.AuthUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Tag(name = "Candidate Controller", description = "APIs releated to Candidate operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class CandidateController {
    private final CandidateService candidateService;
    @GetMapping(Endpoint.V1.Candidate.GET_DETAIL)
    public ResponseEntity<Response<DetailCandidateResponseDTO>> getDetailCandidate() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse("",
                        candidateService.getDetailCandidate(AuthUtil.getRequestedUser().getId()))
        );
    }
    @PostMapping(Endpoint.V1.Candidate.REGISTER_CANDIDATE)
    public ResponseEntity<Response<LoginResponseDTO>> registerCandidate(
            @RequestBody @Valid CandidateRequestDTO requestDTO
            ){
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse(
                        "Welcome "+requestDTO.username(),
                        candidateService.registerCandidate(requestDTO)
                )
        );
    }
    @PutMapping(Endpoint.V1.Candidate.UPDATE)
    public ResponseEntity<Response<DetailCandidateResponseDTO>> updateCandidate(
            @RequestBody @Valid UpdCandidateRequestDTO requestDTO
            ) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse(
                        "Cập nhật thông tin tài khoản thành công",
                        candidateService.updateCandidate(AuthUtil.getRequestedUser().getId(), requestDTO)
                )
        );
    }
}
