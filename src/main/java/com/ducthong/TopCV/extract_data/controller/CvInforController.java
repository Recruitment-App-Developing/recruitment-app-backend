package com.ducthong.TopCV.extract_data.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.extract_data.service.CvInforService;
import com.ducthong.TopCV.responses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestApiV1
@RequiredArgsConstructor
public class CvInforController {
    private final CvInforService cvInforService;

    @GetMapping(Endpoint.V1.CvInfor.GET_LIST)
    public ResponseEntity getList() {
        return ResponseEntity.ok(Response.successfulResponse(
                "Lấy danh sách CV thành công",
                cvInforService.getList()
        ));
    }

    @GetMapping(Endpoint.V1.CvInfor.GET_DETAIL)
    public ResponseEntity getDetail(@PathVariable(name = "inforId") String inforId) {
        return ResponseEntity.ok(Response.successfulResponse(
                "Lấy chi tiết thông tin CV thành công",
                cvInforService.getDetail(inforId)
        ));
    }
}
