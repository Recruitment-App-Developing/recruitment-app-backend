package com.ducthong.TopCV.extract_data.controller;

import com.ducthong.TopCV.annotation.RestApiV1;
import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.extract_data.dto.DetailCvInforDTO;
import com.ducthong.TopCV.extract_data.service.CvInforService;
import com.ducthong.TopCV.responses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping(Endpoint.V1.CvInfor.UPDATE)
    public ResponseEntity update(@RequestBody DetailCvInforDTO request) {
        return ResponseEntity.ok(cvInforService.update(request));
    }

    @GetMapping(Endpoint.V1.CvInfor.AUDIT_BY_ID)
    public ResponseEntity getAuditById(
            @PathVariable(name = "type") String type,
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.ok(cvInforService.getCvInforAuditById(id, type));
    }

    @GetMapping(Endpoint.V1.CvInfor.AUDIT_BY_CV_INFO_ID)
    public ResponseEntity getAuditByCvInfoId(
            @PathVariable(name = "type") String type,
            @PathVariable(name = "cvInfoId") String cvInfoId
    ) {
        return ResponseEntity.ok(cvInforService.getCvInforAuditByCvInfoId(cvInfoId, type));
    }
}
