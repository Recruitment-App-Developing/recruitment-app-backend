package com.ducthong.TopCV.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AddressService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Address Controller", description = "APIs related to Address operations")
@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressController {
    AddressService addressService;

    @GetMapping(Endpoint.V1.Address.GET_LIST_PROVINCE)
    public ResponseEntity<Response<List<Map<String, String>>>> getListProvince() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse("", addressService.getListProvince()));
    }

    @GetMapping(Endpoint.V1.Address.GET_LIST_DISTRICT_BY_PROVINCE)
    public ResponseEntity<Response<List<Map<String, String>>>> getListDistrictByProvince(
            @PathVariable(name = "provinceId") String provinceId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse("", addressService.getListDistrictByProvince(provinceId)));
    }

    @GetMapping(Endpoint.V1.Address.GET_LIST_WARD_BY_DISTRICT)
    public ResponseEntity<Response<List<Map<String, String>>>> getListWardByDistrict(
            @PathVariable(name = "districtId") String districtId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.successfulResponse("", addressService.getListWardByDistrict(districtId)));
    }
}
