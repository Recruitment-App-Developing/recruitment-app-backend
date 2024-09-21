package com.ducthong.TopCV.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.company.BriefCompanyResponseDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CompanyService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Company Controller", description = "APIs related to Company operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping(Endpoint.V1.Company.GET_BRIEF_COMPANY)
    public ResponseEntity<Response<BriefCompanyResponseDTO>> getBriefCompany(
            @PathVariable(name = "companyId") Integer companyId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Get brief company successful", companyService.getBriefCompany(companyId)));
    }
}
