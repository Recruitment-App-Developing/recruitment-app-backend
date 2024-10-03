package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.domain.dto.company.CompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.company.DetailCompanyResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;
import org.springdoc.core.annotations.ParameterObject;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Company Controller", description = "APIs related to Company operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping(Endpoint.V1.Company.GET_LIST)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<CompanyResponseDTO>>> getListCompany(
            @ParameterObject MetaRequestDTO metaRequestDTO,
            @RequestParam(name = "company_name", required = false) String nameCom
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.getListCompany(metaRequestDTO, nameCom));
    }
    @GetMapping(Endpoint.V1.Company.GET_BRIEF_COMPANY)
    public ResponseEntity<Response<BriefCompanyResponseDTO>> getBriefCompany(
            @PathVariable(name = "companyId") Integer companyId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Get brief company successful", companyService.getBriefCompany(companyId)));
    }
    @GetMapping(Endpoint.V1.Company.GET_DETAIL)
    public ResponseEntity<Response<DetailCompanyResponseDTO>> getDetailCompany(
            @PathVariable(name = "companyId") Integer companyId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Get detail company successful", companyService.getDetailCompany(companyId)
                ));
    }
}
