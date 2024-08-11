package com.ducthong.TopCV.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.industry.IndustryRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.entity.Industry;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.IndustryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Industry Controller", description = "APIs related to Industry operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class IndustryController {
    // Service
    private final IndustryService industryService;

    @GetMapping(Endpoint.V1.Industry.GET_LIST_ACTIVE)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<Industry>>> getAllActiveIndustry(
            @ParameterObject MetaRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(industryService.getAllActiveIndustry(requestDTO));
    }

    @GetMapping(Endpoint.V1.Industry.GET_LIST_ALL)
    public ResponseEntity<MetaResponse<MetaResponseDTO, List<Industry>>> getAllIndustry(
            @ParameterObject MetaRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(industryService.getAllIndustry(requestDTO));
    }

    @GetMapping(Endpoint.V1.Industry.GET_DETAIL_ACTIVE)
    public ResponseEntity<Response<Industry>> getDetailActiveIndustry(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(industryService.getDetailActiveIndustry(id));
    }

    @GetMapping(Endpoint.V1.Industry.GET_DETAIL_ALL)
    public ResponseEntity<Response<Industry>> getDetailAllIndustry(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(industryService.getDetailAllIndustry(id));
    }

    @PostMapping(Endpoint.V1.Industry.ADD_ONE)
    public ResponseEntity<Response<Industry>> addIndustry(@RequestBody @Valid IndustryRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(industryService.addIndustry(requestDTO));
    }
}
