package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.StatisticService;
import com.ducthong.TopCV.utility.AuthUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Tag(name = "Statistic Controller", description = "APIs related to Statistic operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class StatisticController {
    private final StatisticService statisticService;
    @GetMapping(Endpoint.V1.Statistic.STATISTIC_GENERAL_JOB_BY_INDUSTRY)
    public ResponseEntity<Response<Map<String, Object>>> statisticGeneralJobByIndustry() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse("",
                        statisticService.statisticGeneralJobByIndustry())
        );
    }
    @GetMapping(Endpoint.V1.Statistic.STATISTIC_GENERAL_JOB_BY_DAY)
    public ResponseEntity<Response<Map<String, Object>>> statisticGeneralJobByDay() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse("",
                        statisticService.statisticGeneralJobByDay())
        );
    }
    @GetMapping(Endpoint.V1.Statistic.STATISTIC_COMPANY_JOB_BY_INDUSTRY)
    public ResponseEntity<Response<Map<String, Object>>> statisticCompanyJobByIndustry() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse("",
                        statisticService.statisticCompanyJobByIndustry(AuthUtil.getRequestedUser().getId()))
        );
    }
    @GetMapping(Endpoint.V1.Statistic.STATISTIC_APPLICATION_STATUS_BY_COMPANY)
    public ResponseEntity<Response<Map<String, Object>>> statisticApplicationStatusByCompany() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse("",
                        statisticService.statisticApplicationStatusByCompany(AuthUtil.getRequestedUser().getId()))
        );
    }
    @GetMapping(Endpoint.V1.Statistic.STATISTIC_APPLY_CANDIDATE_BY_DAY)
    public ResponseEntity<Response<Map<String, Object>>> statisticApplyCandidateByDay() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Response.successfulResponse("",
                        statisticService.statisticApplyCandidateByDay(AuthUtil.getRequestedUser().getId()))
        );
    }
}
