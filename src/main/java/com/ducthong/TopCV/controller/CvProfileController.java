package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.domain.dto.application.AppliedCandidateResponseDTO;
import com.ducthong.TopCV.domain.dto.cv_profile.CvProfileResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CvProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Cv Profile Controller", description = "APIs related to Cv Profile operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class CvProfileController {
    private final CvProfileService cvProfileService;
    @GetMapping(Endpoint.V1.CvProfile.GET_ONE)
    public ResponseEntity<Response<CvProfileResponseDTO>> getCvProfile(
            @PathVariable(name = "cvProfileId") String cvProfileId){
        return ResponseEntity.status(HttpStatus.OK).body(Response.successfulResponse(
                "Get CV profile successfull",
                cvProfileService.getCvProfile(cvProfileId)));
    }
}
