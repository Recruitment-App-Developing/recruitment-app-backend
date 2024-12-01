package com.ducthong.TopCV.controller;

import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.cv.CvRequestDTO;
import com.ducthong.TopCV.domain.dto.cv.CvResponseDTO;
import com.ducthong.TopCV.domain.dto.cv.UpdCvRequestDTO;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CvService;
import com.ducthong.TopCV.utility.AuthUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Cv Controller", description = "APIs related to Cv operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class CvController {
    private final CvService cvService;

    @GetMapping(Endpoint.V1.Cv.GET_LIST_BY_ACCOUNT_ID)
    public ResponseEntity<Response<List<CvResponseDTO>>> getListByAccountId() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Get my CV successful",
                        cvService.getListCvByAccountId(
                                AuthUtil.getRequestedUser().getId())));
    }

    @PostMapping(Endpoint.V1.Cv.ADD_ONE)
    public ResponseEntity<Response<CvResponseDTO>> addOneCv(@RequestBody @Valid CvRequestDTO requestDTO)
            throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Thêm mới một CV thành công",
                        cvService.addCv(AuthUtil.getRequestedUser().getId(), requestDTO)));
    }

    @PostMapping(Endpoint.V1.Cv.UPDATE_CV)
    public ResponseEntity<Response<CvResponseDTO>> updateCv(@RequestBody UpdCvRequestDTO requestDTO)
            throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse(
                        "Update a cv successful",
                        cvService.updateCv(AuthUtil.getRequestedUser().getId(), requestDTO)));
    }

    @DeleteMapping(Endpoint.V1.Cv.DELETE)
    public ResponseEntity<Response<String>> deleteCv(@PathVariable(name = "cvId") String cvId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cvService.deleteCv(AuthUtil.getRequestedUser().getId(), cvId));
    }
}
